package ArticleService.dao;

import ArticleService.entities.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findAll();

    Article getArticlesById(long id);

    // for init
    boolean existsByTitleAndAuthorAndPublishedAt(String title, String author, LocalDateTime publishedAt);
}