package CommentService.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "comments", schema = "commentdatabase", indexes = {
        @Index(
                // indexing on the articleid, making lookups a bit faster for the site.
                // tradeoff here, is that the scheduler is gonna have heavier calls.
                // I could also index it on profanity status - making the scheduler faster
                // However this would make the web site slower.
                // I priotize response time (availablity over consistency).
                name = "idx_comments_article_posted_id",
                columnList ="articleid, posted_at, id")
})
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id",unique = true)
    private long id;

    //foreign key? - wtf idk what to do here..
    @Column(name= "articleid", nullable = false)
    private long articleId;

    @Column(name="content", nullable = false)
    private String content;

    @Column(name="posted_at", nullable = false)
    private LocalDateTime postedAt;

    @Enumerated(EnumType.STRING)
    @Column(name ="profane")
    private Profanity profane;

    @Column(name="profanity_Attempts", nullable = false)
    private int profanityAttempts =0;

    @Column(name ="profanity_next_attempt")
    private Instant profanityNextAttemptAt = null;

    protected Comment() {

    }

    public Comment(LocalDateTime postedAt, String content, long articleId) {
        this.articleId = articleId;
        this.content = content;
        this.postedAt = postedAt;
        this.profane = Profanity.UNSURE;
        this.profanityAttempts = 0;
        this.profanityNextAttemptAt = null;
    }

    public Comment(long id, LocalDateTime postedAt, String content, long articleId) {
        this.id = id;
        this.postedAt = postedAt;
        this.content = content;
        this.articleId = articleId;
        // Default is unsure
        this.profane = Profanity.UNSURE;
        this.profanityAttempts = 0;
        this.profanityNextAttemptAt =null;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "articleId=" + articleId +
                ", content='" + content + '\'' +
                ", postedAt=" + postedAt +
                '}';
    }
}
