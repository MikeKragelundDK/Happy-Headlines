package CommentService.cache;

import CommentService.dao.CommentRepository;
import CommentService.entities.Comment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Counter;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Component
public class CommentCache {

    private static final String LRU_KEY = "commentCache:lru";
    private static final String KEY_PREFIX = "commentCache:article:";
    private static final int MAX_ARTICLE_BUCKETS = 30;

    private final RedisTemplate<String, Object> redis;
    private final CommentRepository commentRepository;

    private final Counter hitCounter;
    private final Counter missCounter;
    private final Counter errorCounter;

    public CommentCache(RedisTemplate<String, Object> redis,
                        CommentRepository commentRepository,
                        MeterRegistry meterRegistry) {
        this.redis = redis;
        this.commentRepository = commentRepository;
        this.hitCounter = meterRegistry.counter("cache.comment.hits");
        this.missCounter = meterRegistry.counter("cache.comment.misses");
        this.errorCounter = meterRegistry.counter("cache.comment.errors");
    }
    /*
    Kommentar til alt det her try/catch tosser:
    Jeg kunne også have lavet hver metode med en "getCommentFromDb" osv..
    Og så ha høvlet en CircuitBreaker derind..
    Jeg indså det først bagefter, og nu har jeg lavet en million try/catches.. Så.. yeah..
    */

    @SuppressWarnings("unchecked")
    public List<Comment> getCommentsForArticle(long articleId){
        String key = key(articleId);
        try {
            Object cached = redis.opsForValue().get(key);
            if (cached != null) {
                touch(articleId);
                hitCounter.increment();
                return (List<Comment>) cached;
            }
        } catch (RuntimeException e){
            errorCounter.increment();
            log.warn("CommentCache: Redis GET failed (articleID={} - falling back to DB: {}", articleId, e.toString());
        }
        List<Comment> fetched = commentRepository.findCommentsByArticleId(articleId);

        try {
            redis.opsForValue().set(key, fetched);
            touch(articleId);
            enforceCapacity();
            missCounter.increment();
        } catch (RuntimeException e){
            errorCounter.increment();
            log.warn("CommentCache: Redis SET failed (articleID={} - falling back to DB: {}", articleId, e.toString());
        }
        return fetched;
    }

    public void invalidateArticle(long articleId){
        try{
            redis.delete(key(articleId));
            redis.opsForZSet().remove(LRU_KEY, articleId);
        } catch (RuntimeException e){
            errorCounter.increment();
            log.warn("CommentCache: Redis invalidate failed (articleID={} - falling back to DB: {}", articleId, e.toString());
        }
    }

    public void invalidateArticles(Collection<Long> articleIds){
        if(articleIds == null || articleIds.isEmpty()) return;
        try{
            List<String> keys = articleIds.stream().map(this::key).toList();
            redis.delete(keys);
            redis.opsForZSet().remove(LRU_KEY, articleIds.stream().map(String::valueOf).toArray());
        } catch (RuntimeException e){
            errorCounter.increment();
            log.warn("CommentCache: Redis bulk invalidate failed (articleIDs={} - falling back to DB: {}", articleIds, e.toString());
        }
    }

    public Map<String,Object> stats(){
        long size = 0L;
        try{
            Long s = redis.opsForZSet().size(LRU_KEY);
            size = (s == null) ? 0 : s;
        } catch (RuntimeException e){
            errorCounter.increment();
            log.warn("CommentCache: Redis size failed (size={})", e.toString());
        }
        double hits = hitCounter.count();
        double misses = missCounter.count();
        double ratio = (hits+misses) == 0 ? 0.0 : hits / (hits + misses);

        Map<String, Object> m = new LinkedHashMap<>();
        m.put("size", size);
        m.put("hits", (long) hits);
        m.put("misses", (long) misses);
        m.put("hitRatio", ratio);
        return m;
    }

    private void touch(long articleId){
        try {
            redis.opsForZSet().add(LRU_KEY, articleId, (double) Instant.now().toEpochMilli());
        } catch (RuntimeException e){
            errorCounter.increment();
            log.warn("CommentCache: Redis touch failed (articleID={} - falling back to DB: {}", articleId, e.toString());
        }
    }

    private String key (long articleId){
        return KEY_PREFIX + articleId;
    }

    private void enforceCapacity(){
        try{
        Long size = redis.opsForZSet().size(LRU_KEY);
        if(size != null && size> MAX_ARTICLE_BUCKETS) {
            long toEvict = size - MAX_ARTICLE_BUCKETS;
            // ældste først.
            Set<Object> lruIds = redis.opsForZSet().range(LRU_KEY, 0, toEvict - 1);
            if (lruIds != null && !lruIds.isEmpty()) {
                List<String> keys =
                        lruIds.stream()
                                .map(id -> key(Long.parseLong(id.toString())))
                                .collect(Collectors.toList());
                redis.delete(keys);
                redis.opsForZSet().remove(LRU_KEY, lruIds.toArray());
            }
        }
        } catch (RuntimeException e){
            errorCounter.increment();
            log.warn("CommentCache: Redis capacity enforcement failed (size={})", e.toString());
        }
    }
}
