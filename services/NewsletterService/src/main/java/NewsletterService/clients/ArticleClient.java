package NewsletterService.clients;

import NewsletterService.dto.Article;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;


@Slf4j
@Component
public class ArticleClient {
    private final RestClient http;

    public ArticleClient(RestClient.Builder builder) {
      this.http = builder.baseUrl("http://article-service:8080").build();
    }

    // idk example, get top 5 articles i guess?
    public List<Article> getTop5Articles() {
        return http.get()
                .uri("/api/articles/top5")
                .retrieve()
                .body(new ParameterizedTypeReference<List<Article>>() {
        });
    }

}
