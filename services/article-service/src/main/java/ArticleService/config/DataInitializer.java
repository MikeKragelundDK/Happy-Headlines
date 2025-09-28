package ArticleService.config;

import ArticleService.dao.ArticleRepository;
import ArticleService.entities.Article;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
public class DataInitializer {
    @Bean
    ApplicationRunner init(ArticleRepository repo) {
        return args -> {
            List<Article> seed = List.of(
                    new Article("Dummy data 1", "System", "test123"),
                    new Article("Dummy data 2", "System","test123"),
                    new Article("Dummy data 3", "System","test123")
            );

            for(Article article : seed){
                if(!repo.existsByTitleAndAuthor(article.getTitle(),article.getAuthor())){
                    repo.save(article);
                }
            }
        };
    }
}
