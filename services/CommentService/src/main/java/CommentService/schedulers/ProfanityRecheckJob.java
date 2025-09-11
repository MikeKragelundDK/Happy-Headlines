package CommentService.schedulers;

import CommentService.clients.ProfanityClient;
import CommentService.entities.Profanity;
import CommentService.exceptions.ProfanityServiceUnavailableException;
import CommentService.service.CommentService_I;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;

@Service
@RequiredArgsConstructor
public class ProfanityRecheckJob {
    private final CommentService_I commentService;
    private final ProfanityClient profanityClient;

    @Scheduled(fixedDelayString = "${profanity.recheck-interval:PT10M}")
    @Transactional
    public void recheck() {
        var batch = commentService.findTop200ByProfaneAndProfanityNextAttemptAtBeforeOrderByIdAsc(Profanity.UNSURE, Instant.now());

        for(var  comment : batch) {
            try{
                // success, we set the profanity status and null the attempts.
                boolean profane = profanityClient.filter(comment.getContent());
                comment.setProfane(profane ? Profanity.PROFANE : Profanity.SAFE);
                comment.setProfanityNextAttemptAt(null);
            } catch (ProfanityServiceUnavailableException e){
                int attempts = comment.getProfanityAttempts() +1;
                comment.setProfanityAttempts(attempts);
                // exponential backoff.
                long minutes = Math.min(60, 5L * attempts);
                comment.setProfanityNextAttemptAt(Instant.now().plus(Duration.ofMinutes(minutes)));
            }
        }
        commentService.saveAll(batch);
    }
}
