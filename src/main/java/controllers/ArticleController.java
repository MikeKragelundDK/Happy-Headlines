package controllers;

import entities.Article;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import service.ArticleService_I;

@Controller

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


}
