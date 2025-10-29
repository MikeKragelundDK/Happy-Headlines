package NewsletterService.clients;

import NewsletterService.dto.Subscriber;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.List;

@Component
@Slf4j
public class SubscriberClient {
    private final RestClient http;

    public SubscriberClient(RestClient.Builder builder) {
        this.http = builder.baseUrl("http://subscriber-service:8080").build();
    }

    public List<Subscriber> getAllSubscribers() {
        return http.get()
                .uri("/api/subscriber")
                .retrieve()
                .body(new ParameterizedTypeReference<List<Subscriber>>(){});
    }
}
