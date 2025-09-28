package ArticleService.service;

import ArticleService.entities.Article;

import java.util.List;
import java.util.Map;

public interface ArticleService_I {
     Article getArticle(long id);
     List<Article> getArticles();
     Article addArticle(Article article);
     Article updateArticle(Article article);
     void deleteArticle(long id);
     Map<Long, Boolean> existsMap(List<Long> ids);
     List<Article> findTop5ByOrderByPublishedAtDesc();
}
