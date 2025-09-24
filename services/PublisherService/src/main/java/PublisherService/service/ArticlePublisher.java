package PublisherService.service;

import PublisherService.dto.ArticleRecievedDto;
import PublisherService.messaging.MessageClient_I;
import PublisherService.messaging.RabbitMessageClientImpl;
import PublisherService.util.PublishOutcome;
import org.springframework.stereotype.Service;

@Service
public class ArticlePublisher {

    private final MessageClient_I client;

    public ArticlePublisher(RabbitMessageClientImpl client) {
        this.client = client;
    }

    public PublishOutcome publish(ArticleRecievedDto message) {

        return client.publish(message);

    }
}
