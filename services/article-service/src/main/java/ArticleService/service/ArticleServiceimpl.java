package ArticleService.service;

import ArticleService.dao.ArticleRepository;
import ArticleService.entities.Article;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ArticleServiceimpl implements ArticleService_I {
    private final ArticleRepository articleRepos;

    public ArticleServiceimpl(ArticleRepository articleRepos) {
        this.articleRepos = articleRepos;
    }

    @Override
    @Cacheable(cacheNames = "articlesById", key ="#id", unless = "#result == null")
    public Article getArticle(long id) {
        return articleRepos.getArticlesById(id);
    }

    @Override
    public List<Article> getArticles() {
        return articleRepos.findAll();
    }


    @Override
    // I gotta recache latestArticles, since it updates once every hour, if we put a new one it will be stale..
    // A new article most likely won't be top 5, so i don't need to refresh that.
    @CacheEvict(cacheNames =  "latestArticles", allEntries = true)
    @CachePut(cacheNames = "articlesById", key ="#article.id")
    public Article addArticle(Article article) {
        return articleRepos.save(article);
    }

    @Override
    // Here i refresh top5, since the updated article might be in there.
    @CacheEvict(cacheNames =  "top5Articles", allEntries = true)
    @CachePut(cacheNames = "articlesById", key ="#article.id")
    public Article updateArticle(Article article) {
        return articleRepos.save(article);
    }

    @Override
    // Here i nuke the entire list..
    @Caching(evict = {
            @CacheEvict(cacheNames =  "top5Articles", allEntries = true),
            @CacheEvict(cacheNames =  "articlesById", key = "#id"),
            @CacheEvict(cacheNames =  "latestArticles", allEntries = true)
    })
    public void deleteArticle(long id) {
        articleRepos.delete(getArticle(id));
    }

    @Override
    public Map<Long, Boolean> existsMap(List<Long> ids) {
        List<Long> existingIds = articleRepos.findExistingIds(ids);
        Map<Long, Boolean> result = new HashMap<>();
        for(Long id : ids){
           result.put(id, existingIds.contains(id));
        }
        return result;
    }

    @Override
    @Cacheable(cacheNames = "top5Articles", key ="'top5'")
    public List<Article> findTop5ByOrderByPublishedAtDesc() {
        return articleRepos.findTop5ByOrderByPublishedAtDesc();
    }

    @Override
    @Cacheable(cacheNames = "latestArticles", key ="#days")
    public List<Article> getLatestSinceDays(int days) {
        LocalDateTime cutoff = LocalDateTime.now().minusDays(days);
        return articleRepos.findAllByPublishedAtAfterOrderByPublishedAtDesc(cutoff);
    }
}
