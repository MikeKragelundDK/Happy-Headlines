package ArticleService.controllers;

import ArticleService.entities.Article;
import ArticleService.entities.ArticleRequest;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Article> getArticle(@PathVariable long id) {
        Article fetched = articleService.getArticle(id);
        return ResponseEntity.status(HttpStatus.OK).body(fetched);
    }

    @GetMapping
    public ResponseEntity<List<Article>> getArticles() {
        List<Article> fetched = articleService.getArticles();
        return ResponseEntity.status(HttpStatus.OK).body(fetched);
    }

    @PostMapping
    public ResponseEntity<Article> saveArticle(@RequestBody ArticleRequest article) {
        Article a = new Article(article.getTitle(),article.getAuthor(),article.getPublishedAt());
        Article saved = articleService.addArticle(a);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Article> deleteArticle(@PathVariable long id) {
        Article article = articleService.getArticle(id);
        if(article == null) {
            throw new RuntimeException("Article not found: " + id);
        }
        articleService.deleteArticle(id);

        return ResponseEntity.status(HttpStatus.OK).body(article);
    }

    @PutMapping("/{id}")
    public  ResponseEntity<Article> updateArticle(@PathVariable long id, @RequestBody ArticleRequest article) {
        if(articleService.getArticle(id) == null) {
            throw new RuntimeException("Article not found: " + id);
        }
        Article a = new Article(id,article.getTitle(),article.getAuthor(),article.getPublishedAt());
        Article saved = articleService.updateArticle(a);
        return  ResponseEntity.status(HttpStatus.OK).body(saved);
    }
}
