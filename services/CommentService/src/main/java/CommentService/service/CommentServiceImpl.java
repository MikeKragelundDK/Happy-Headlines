package CommentService.service;

import CommentService.clients.ProfanityClient;
import CommentService.dao.CommentRepository;
import CommentService.entities.Comment;
import CommentService.entities.Profanity;
import CommentService.exceptions.ProfanityServiceUnavailableException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
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
    @Transactional
    public Comment addComment(Comment comment) {
        // Implement circuit breaker here.
        try{
        Boolean isProfane = profane.filter(comment.getContent());
        if(isProfane) {
            comment.setProfane(Profanity.PROFANE);
        } else {
            comment.setProfane(Profanity.SAFE);
        }}
        catch (ProfanityServiceUnavailableException e) {
            log.warn("Profanity service failed, marking as UNSURE");
            comment.setProfane(Profanity.UNSURE);

            // mark for retry and attempt again in 10 minutes.
            comment.setProfanityAttempts(1);
            comment.setProfanityNextAttemptAt(Instant.now().plus(Duration.ofMinutes(10)));
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
    public void deleteCommentForArticle(long articleId, long id) {
        commentRepository.delete(commentRepository.findCommentByArticleIdAndId(articleId, id));

    }

    @Override
    public List<Comment> findTop200ByProfaneAndProfanityNextAttemptAtBeforeOrderByIdAsc(Profanity profane, Instant cutoff) {
        return commentRepository.findTop200ByProfaneAndProfanityNextAttemptAtBeforeOrderByIdAsc(profane,cutoff);
    }

    @Override
    public void saveAll(List<Comment> comments) {
        commentRepository.saveAll(comments);
    }

    @Override
    public List<Long> findCommentIds() {
        return commentRepository.findCommentIds();
    }

    @Override
    @Transactional
    public long deleteAllByArticleId(Collection<Long> articleId) {
        return commentRepository.deleteByArticleIdIn(articleId);
    }
}
