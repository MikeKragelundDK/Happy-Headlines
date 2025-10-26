package CommentService.clients;

import CommentService.dto.ProfanityRequest;
import CommentService.dto.ProfanityResponse;
import CommentService.exceptions.ProfanityServiceUnavailableException;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
@Slf4j
@Component
public class ProfanityClient {
    private final RestClient http;

    public ProfanityClient(RestClient.Builder builder) {
        this.http = builder.baseUrl("http://profanity-service:8282")
                .build();
    }

    @CircuitBreaker(name ="profanityService", fallbackMethod = "profanityUnavailable")
    @Retry(name="profanityService")
    public Boolean filter(String content){
        return Boolean.TRUE.equals(http.post()
                                                  .uri("/api/profanity/check")
                                                  .contentType(MediaType.TEXT_PLAIN)
                                                  .body(content)
                                                  .retrieve()
                                                  .body(Boolean.class));
    }

    public Boolean profanityUnavailable(String content,Throwable t){
        log.warn("Circuit breaker triggered");
        throw new ProfanityServiceUnavailableException("Profanity service unavailable", t);
    }
}
