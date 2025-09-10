package ArticleService.service;

import ArticleService.entities.Article;

import java.util.List;

public interface ArticleService_I {
     Article getArticle(long id);
     List<Article> getArticles();
     Article addArticle(Article article);
     Article updateArticle(Article article);
      void deleteArticle(long id);
}
