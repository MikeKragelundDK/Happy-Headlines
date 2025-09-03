package service;

import dao.ArticleRepository;
import entities.Article;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ArticleServiceimpl implements ArticleService_I {
    private final ArticleRepository articleRepos;

    public ArticleServiceimpl(ArticleRepository articleRepos) {
        this.articleRepos = articleRepos;
    }

    @Override
    public Article getArticle(long id) {
        return articleRepos.getArticlesById(id);
    }

    @Override
    public List<Article> getArticles() {
        return articleRepos.findAll();
    }

    @Override
    public Article addArticle(Article article) {
        return articleRepos.save(article);
    }

    @Override
    public Article updateArticle(long id,Article article) {
        return articleRepos.save(getArticle(id));
    }

    @Override
    public void deleteArticle(long id) {
        articleRepos.delete(getArticle(id));
    }
}
