package ArticleService.dao;

import ArticleService.entities.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findAll();

    Article getArticlesById(long id);

    // for init
    boolean existsByTitleAndAuthor(String title, String author);

    @Query("select a.id from Article a where a.id in :ids")
    List<Long> findExistingIds(@Param("ids")Collection<Long> ids);

    List<Article> findTop5ByOrderByPublishedAtDesc();

}
