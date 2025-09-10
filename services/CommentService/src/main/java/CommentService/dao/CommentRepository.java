package CommentService.dao;

import CommentService.entities.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findCommentsByArticleId(long articleId);

    Comment findCommentByArticleId(long articleId);

    Comment getCommentById(long id);
}
