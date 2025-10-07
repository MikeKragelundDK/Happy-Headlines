package CommentService.service;

import CommentService.cache.CommentCache;
import CommentService.clients.ProfanityClient;
import CommentService.dao.CommentRepository;
import CommentService.entities.Comment;
import CommentService.entities.Profanity;
import CommentService.exceptions.ProfanityServiceUnavailableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.List;
@Service
@Slf4j
public class CommentServiceImpl implements CommentService_I {
    private final CommentRepository commentRepository;
    private final ProfanityClient profane;
    private final CommentCache commentCache;

    public CommentServiceImpl(CommentRepository commentRepository,  ProfanityClient profane, CommentCache commentCache) {
        this.commentRepository = commentRepository;
        this.profane = profane;
        this.commentCache = commentCache;
    }

    @Override
    @Transactional
    public Comment addComment(Comment comment) {
        // Implement circuit breaker here.
        try{
        Boolean isProfane = profane.filter(comment.getContent());
        if(isProfane) {
            comment.setProfane(Profanity.PROFANE);
        } else {
            comment.setProfane(Profanity.SAFE);
        }}
        catch (ProfanityServiceUnavailableException e) {
            log.warn("Profanity service failed, marking as UNSURE");
            comment.setProfane(Profanity.UNSURE);

            // mark for retry and attempt again in 10 minutes.
            comment.setProfanityAttempts(1);
            comment.setProfanityNextAttemptAt(Instant.now().plus(Duration.ofMinutes(10)));
        }
        Comment saved = commentRepository.save(comment);
        commentCache.invalidateArticle(saved.getArticleId());
        return saved;
    }

    @Override
    public List<Comment> getCommentsForArticle(long articleId) {
        return commentCache.getCommentsForArticle(articleId);
    }

    @Override
    public Comment getComment(long id) {
        return commentRepository.getCommentById(id);
    }

    @Override
    public Comment updateCommentForArticle(Comment comment) {
        Comment updated = commentRepository.save(comment);
        commentCache.invalidateArticle(updated.getArticleId());
        return updated;
    }

    @Override
    public void deleteCommentForArticle(long articleId, long id) {
        commentRepository.delete(commentRepository.findCommentByArticleIdAndId(articleId, id));
        commentCache.invalidateArticle(articleId);

    }

    @Override
    public List<Comment> findTop200ByProfaneAndProfanityNextAttemptAtBeforeOrderByIdAsc(Profanity profane, Instant cutoff) {
        return commentRepository.findTop200ByProfaneAndProfanityNextAttemptAtBeforeOrderByIdAsc(profane,cutoff);
    }

    @Override
    @Transactional
    public void saveAll(List<Comment> comments) {
        commentRepository.saveAll(comments);
        // lidt wonky bulk invalidation
        comments.stream()
                .map(Comment::getArticleId)
                .distinct()
                .forEach(commentCache::invalidateArticle);
    }

    @Override
    public List<Long> findCommentIds() {
        return commentRepository.findCommentIds();
    }

    @Override
    @Transactional
    public long deleteAllByArticleId(Collection<Long> articleId) {
        long deleted = commentRepository.deleteByArticleIdIn(articleId);
        commentCache.invalidateArticles(articleId);
        return deleted;
    }
}
