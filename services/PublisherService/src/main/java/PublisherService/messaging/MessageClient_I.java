package PublisherService.messaging;

import PublisherService.dto.ArticleRecievedDto;
import PublisherService.util.PublishOutcome;

public interface MessageClient_I {
    PublishOutcome publish(ArticleRecievedDto message);
}
