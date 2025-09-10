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
        this.http = builder.baseUrl("http://localhost:8282").build();
    }

    public ProfanityResponse filter(String content){
        return http.post()
                .uri("/api/profanity")
                .contentType(MediaType.APPLICATION_JSON)
                .body(new ProfanityRequest(content))
                .retrieve()
                .body(ProfanityResponse.class);
    }
}
