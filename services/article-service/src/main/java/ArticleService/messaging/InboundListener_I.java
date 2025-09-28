package ArticleService.messaging;

import ArticleService.dto.ArticleRequest;

public interface InboundListener_I {
    void onMessage(ArticleRequest request);
}

