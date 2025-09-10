package CommentService.service;

import CommentService.clients.ProfanityClient;
import CommentService.dao.CommentRepository;
import CommentService.dto.ProfanityResponse;
import CommentService.entities.Comment;
import CommentService.entities.Profanity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
@Slf4j
public class CommentServiceImpl implements CommentService_I {
    private final CommentRepository commentRepository;
    private final ProfanityClient profane;

    public CommentServiceImpl(CommentRepository commentRepository,  ProfanityClient profane) {
        this.commentRepository = commentRepository;
        this.profane = profane;
    }

    @Override
    public Comment addComment(Comment comment) {
        // Implement circuit breaker here.
        try{
        Boolean isProfane = profane.filter(comment.getContent());
        if(isProfane) {
            comment.setProfane(Profanity.PROFANE);
        } else {
            comment.setProfane(Profanity.SAFE);
        }}
        catch (Exception e) {
            log.warn("Profanity service failed, marking as UNSURE", e);
            comment.setProfane(Profanity.UNSURE);
        }

        return commentRepository.save(comment);
    }

    @Override
    public List<Comment> getCommentsForArticle(long articleId) {
        return commentRepository.findCommentsByArticleId(articleId);
    }

    @Override
    public Comment getComment(long id) {
        return commentRepository.getCommentById(id);
    }

    @Override
    public Comment updateCommentForArticle(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public void deleteCommentForArticle(long articleId) {
        commentRepository.delete(commentRepository.findCommentByArticleId(articleId));

    }
}
