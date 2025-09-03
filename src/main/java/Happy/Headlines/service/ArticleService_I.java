package Happy.Headlines.service;

import Happy.Headlines.entities.Article;

import java.util.List;

public interface ArticleService_I {
    public Article getArticle(long id);
    public List<Article> getArticles();
    public Article addArticle(Article article);
    public Article updateArticle(long id,Article article);
    public  void deleteArticle(long id);
}
