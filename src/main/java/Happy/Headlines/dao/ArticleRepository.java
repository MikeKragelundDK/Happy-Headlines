package Happy.Headlines.dao;

import Happy.Headlines.entities.Article;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface ArticleRepository extends JpaRepository<Article, Long> {

    List<Article> findAll();

    Article getArticlesById(long id);
}