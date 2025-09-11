package CommentService.dao;

import CommentService.entities.Comment;
import CommentService.entities.Profanity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findCommentsByArticleId(long articleId);

    Comment findCommentByArticleIdAndId(long articleId, long id);

    Comment getCommentById(long id);

    List<Comment> findTop200ByProfaneAndProfanityNextAttemptAtBeforeOrderByIdAsc(
            Profanity profane, Instant cutoff);
}
