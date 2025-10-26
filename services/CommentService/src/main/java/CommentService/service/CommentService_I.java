package CommentService.service;

import CommentService.entities.Comment;
import CommentService.entities.Profanity;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface CommentService_I {
    // web post comment + request comment
    Comment addComment(Comment comment);
    List<Comment> getCommentsForArticle(long articleId);
    Comment getComment(long id);
    Comment updateCommentForArticle(Comment comment);
    // somehow we gotta cascade this call!
    void deleteCommentForArticle(long articleId, long id);

    List<Comment> findTop200ByProfaneAndProfanityNextAttemptAtBeforeOrderByIdAsc(
            Profanity profane, Instant cutoff);

    void saveAll(List<Comment> comments);

    List<Long> findCommentIds();

    CompletableFuture<Long> deleteAllByArticleIdAsync(Collection<Long> articleId);
}
