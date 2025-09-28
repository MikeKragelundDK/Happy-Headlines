package ArticleService.controllers;

import ArticleService.entities.Article;
import ArticleService.dto.ArticleRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ArticleService.service.ArticleService_I;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

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

    /*
    I need something here that can link an article to an author.
    Right now every article gets a new authorId
    A fix could be to add a author entity to the db, compare with that or init a new author.. idk
    TODO
     */
    @PostMapping
    public ResponseEntity<Article> postArticle(@RequestBody ArticleRequest article) {
        Article a = new Article(article.getTitle(),article.getAuthor(), article.getContent());
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
        Article a = new Article(id,article.getTitle(),article.getAuthor(),article.getPublishedAt().orElse(LocalDateTime.now()), article.getContent());
        Article saved = articleService.updateArticle(a);
        return  ResponseEntity.status(HttpStatus.OK).body(saved);
    }

    @PostMapping("/exists")
    public ResponseEntity<Map<Long, Boolean>> exists(@RequestBody List<Long> ids) {
        Map<Long, Boolean> result = articleService.existsMap(ids);
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/top5")
    public ResponseEntity<List<Article>> getTop5Articles(){
        List<Article> fetched = articleService.findTop5ByOrderByPublishedAtDesc();
        return ResponseEntity.status(HttpStatus.OK).body(fetched);
    }
}