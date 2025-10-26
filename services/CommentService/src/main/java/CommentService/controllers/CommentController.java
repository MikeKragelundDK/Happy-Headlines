package CommentService.controllers;

import CommentService.cache.CommentCache;
import CommentService.entities.Comment;
import CommentService.dto.CommentRequest;
import CommentService.service.CommentService_I;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService_I commentService;
    private final CommentCache commentCache;

    public CommentController(CommentService_I commentService, CommentCache commentCache) {
        this.commentService = commentService;
        this.commentCache = commentCache;
    }

    @GetMapping("/{articleID}")
    public ResponseEntity<List<Comment>> getComments(@PathVariable long articleID) {
        List<Comment> fetched = commentService.getCommentsForArticle(articleID);
        return ResponseEntity.status(HttpStatus.OK).body(fetched);
    }

    @PostMapping()
    public ResponseEntity<Comment> addComment(@RequestBody CommentRequest comment) {
        // Her antager jeg at web serveren har håndteret logikken ved at tjekke for article id?
        Comment c = new Comment(comment.getPostedAt(), comment.getContent(), comment.getArticleId());
        Comment saved = commentService.addComment(c);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
    @GetMapping("/comment-ids")
    public ResponseEntity<List<Long>> getCommentids(){
        List<Long> fetched = commentService.findCommentIds();
        return ResponseEntity.status(HttpStatus.OK).body(fetched);
    }

    // I wanna do deletemapping (delete request) - however spring doesn't support that.. So we do post.
    @PostMapping("/bulk-delete")
    public ResponseEntity<String> deleteCommentsById(@RequestBody List<Long> articleIds){
        commentService.deleteAllByArticleIdAsync(articleIds);
        return ResponseEntity.accepted().build();
        // I might wanna create a jobId and expose something like /api/comments/jobs/{jobId}
        // idk?
        // TODO
    }


    // fake dashboard, aka i hate frontend..
    @GetMapping("/cache/stats")
    public ResponseEntity<Map<String, Object>> getCacheStats(){
        return ResponseEntity.status(HttpStatus.OK).body(commentCache.stats());
    }
}
