package CommentCleaner.scheduler;

import CommentCleaner.clients.CommentCleanupClient;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CommentDeleteJob {
    private final CommentCleanupClient commentCleanupClient;

    // for production once every day.
//    @Scheduled(fixedDelayString = "${comment.cleanup-interval:PT24H}")

    // for testing, ever 5 minutes.
    @Scheduled(fixedDelayString = "${comment.cleanup-interval:PT5M}")
    public void deleteComments() {
        List<Long> commentIds = commentCleanupClient.fetchCommentArticleIDs();
        if(commentIds.isEmpty()){
            return;
        }
        HashMap<Long, Boolean> matchArticles = commentCleanupClient.fetchMapOfMatchArticles(commentIds);
        // no need to make the next call, if nothing needs to be deleted..
        if(!matchArticles.containsValue(false)){
            return;
        }

        // get all ids (keys) where the value is false
        List<Long> tobeDeleted=
                matchArticles.entrySet().stream()
                        .filter(e -> Boolean.FALSE.equals(e.getValue()))
                        .map(Map.Entry::getKey)
                        .collect(java.util.stream.Collectors.toList());

        commentCleanupClient.deleteCommentsByArticleIds(tobeDeleted);
    }

}
