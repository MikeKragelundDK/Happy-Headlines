package ArticleService.service;

import ArticleService.entities.Article;

import java.util.List;

public interface ArticleService_I {
    public Article getArticle(long id);
    public List<Article> getArticles();
    public Article addArticle(Article article);
    public Article updateArticle(Article article);
    public  void deleteArticle(long id);
}
