package Happy.Headlines.controllers;

import Happy.Headlines.entities.Article;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import Happy.Headlines.service.ArticleService_I;

import java.util.List;

@RestController
@RequestMapping("/api/articles")
public class ArticleController {
    private final ArticleService_I articleService;

    public ArticleController(ArticleService_I articleService) {
        this.articleService = articleService;
    }

    @GetMapping("/{id}")
    public Article getArticle(@PathVariable long id) {
        return articleService.getArticle(id);
    }
    @GetMapping
    public List<Article> getArticles() {
        return articleService.getArticles();
    }
}
