package PublisherService.messaging;

import PublisherService.dto.ArticleRecievedDto;
import PublisherService.util.PublishOutcome;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
public class RabbitMessageClientImpl implements  MessageClient_I {
    private final RabbitTemplate  rabbitTemplate;


    RabbitMessageClientImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    @Override
    public PublishOutcome publish(ArticleRecievedDto message) {
        // Monitoring/failure isolation
        try {
            rabbitTemplate.convertAndSend(message);
            return new PublishOutcome(true, "Queued");
        }
        catch (Exception e) {
            return new PublishOutcome(false,"Queue unavailable: " + e.getMessage());
        }

    }

}
