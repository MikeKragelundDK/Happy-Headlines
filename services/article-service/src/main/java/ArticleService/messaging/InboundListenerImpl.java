package ArticleService.messaging;

import ArticleService.dto.ArticleRequest;
import ArticleService.entities.Article;
import ArticleService.service.ArticleService_I;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class InboundListenerImpl implements InboundListener_I{
    // This is a bit ugly.. Might wanna rework in the future
    private static final String QUEUE = "inbound_queue";
    private final ArticleService_I service;

    public InboundListenerImpl(ArticleService_I service) {
        this.service = service;
    }
    @RabbitListener(queues = QUEUE)
    public void onMessage(ArticleRequest request){
        Article article = new Article(
                request.getTitle(),
                request.getAuthor(),
                request.getContent()
        );
        service.addArticle(article);
    }
}
