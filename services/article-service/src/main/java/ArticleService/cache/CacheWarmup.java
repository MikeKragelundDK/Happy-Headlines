package ArticleService.cache;

import ArticleService.dao.ArticleRepository;
import ArticleService.entities.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
@EnableScheduling
@RequiredArgsConstructor
public class CacheWarmup {
    private final CacheManager cacheManager;
    // Here we can call directly into the repo. - unsure if it's ugly..
    private final ArticleRepository repo;

    // Get from the latest 14 days.
    @Value("${article-service.cache.warmup.days:14}")
    private int warmDays;

    @Scheduled(initialDelay = 5_000, fixedDelayString = "${article-service.cache.warmup.interval:PT5M}")
    public void warmupCache(){
        LocalDateTime cutoff = LocalDateTime.now().minusDays(warmDays);
        List<Article> recent = repo.findAllByPublishedAtAfterOrderByPublishedAtDesc(cutoff);

        Cache byId = cacheManager.getCache("articlesById");
        Cache latest = cacheManager.getCache("articlesLatest");
        Cache top5 = cacheManager.getCache("top5Articles");

        if(latest != null) latest.put(warmDays, recent);
        if(top5 != null){
            List<Article> top5List = recent.stream().limit(5).toList();
            top5.put("top5", top5List);
        }
        if(byId != null){
            for(Article article : recent){
                byId.put(article.getId(), article);
            }
        }
    }
}
