package PublisherService.controllers;

import PublisherService.dto.ArticleRecievedDto;
import PublisherService.service.ArticlePublisher;
import PublisherService.util.PublishOutcome;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/publish")
public class PublisherController {

    private final ArticlePublisher articlePublisher;
    public PublisherController(ArticlePublisher articlePublisher) {
        this.articlePublisher = articlePublisher;
    }

    @PostMapping
    public ResponseEntity<ArticleRecievedDto> publishToMQ(@RequestBody ArticleRecievedDto articleRecievedDto){
        PublishOutcome outcome = articlePublisher.publish(articleRecievedDto);
        if(outcome.success()){
            return ResponseEntity.status(HttpStatus.CREATED).body(articleRecievedDto);
        } else {
            return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(articleRecievedDto);
        }
    }
}
