package CommentService.clients;

import CommentService.dto.ProfanityRequest;
import CommentService.dto.ProfanityResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

@Component
public class ProfanityClient {
    private final RestClient http;

    public ProfanityClient(RestClient.Builder builder) {
        this.http = builder.baseUrl("http://profanity-service:8282")
                .build();
    }

    public Boolean filter(String content){
        return Boolean.TRUE.equals(http.post()
                                                  .uri("/api/profanity/check")
                                                  .contentType(MediaType.TEXT_PLAIN)
                                                  .body(content)
                                                  .retrieve()
                                                  .body(Boolean.class));
    }
}
