package CommentCleaner.clients;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.HashMap;
import java.util.List;

@Component
@Slf4j
public class CommentCleanupClient {
    private final RestClient httpComment;
    private final RestClient httpArticle;

    public CommentCleanupClient(RestClient.Builder builder) {
        this.httpComment = builder.baseUrl("http://comment-service:8181").build();
        this.httpArticle = builder.baseUrl("http://article-service:8080").build();
    }

    // Fetching article ids of all comments.
    public List<Long> fetchCommentArticleIDs(){
        return httpComment.get()
                .uri("/api/comments/comment-ids")
                .retrieve()
                .body(new ParameterizedTypeReference<List<Long>>(){});
    }

    // vi tager id'er af comments og smider dem herind.
    public HashMap<Long,Boolean> fetchMapOfMatchArticles(List<Long> commentIds){
        return httpArticle.post().uri("/api/articles/exists").body(commentIds).retrieve().body(new ParameterizedTypeReference<HashMap<Long, Boolean>>() {
        });
    }

    public void deleteCommentsByArticleIds(List<Long> articleIds){
         String msg = httpComment.post().uri("/api/comments/bulk-delete").contentType(MediaType.APPLICATION_JSON)
                .body(articleIds).retrieve().body(new ParameterizedTypeReference<String>() {});
         log.info("Deleted comments for articles: {}",msg);
    }
}
