package ArticleService.controllers;

import ArticleService.entities.Article;
import org.springframework.web.bind.annotation.*;
import ArticleService.service.ArticleService_I;

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
    @PostMapping("/")
    public void saveArticle(@RequestBody Article article) {
        articleService.addArticle(article);
    }
    @DeleteMapping("/{id}")
    public String deleteArticle(@PathVariable long id) {
        Article article = articleService.getArticle(id);
        if(article == null) {
            throw new RuntimeException("Article not found: " + id);
        }
        articleService.deleteArticle(id);

        return "Deleted article: " + id;
    }


    @PutMapping("/{id}")
    public  Article updateArticle(@PathVariable long id, @RequestBody Article article) {
        if(articleService.getArticle(id) == null) {
            throw new RuntimeException("Article not found: " + id);
        }
        return articleService.updateArticle(article);
    }
}
