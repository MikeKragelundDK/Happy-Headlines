package ArticleService.service;

import ArticleService.dao.ArticleRepository;
import ArticleService.entities.Article;
import org.springframework.stereotype.Service;

import java.util.*;

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
    public Article updateArticle(Article article) {
        return articleRepos.save(article);
    }

    @Override
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
}
