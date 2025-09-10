package CommentService.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "comments", schema = "commentdatabase")
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

    @Column(name ="profane")
    private Profanity profane;

    protected Comment() {
    }

    public Comment(LocalDateTime postedAt, String content, long articleId) {
        this.articleId = articleId;
        this.content = content;
        this.postedAt = postedAt;
        this.profane = Profanity.UNSURE;
    }

    public Comment(long id, LocalDateTime postedAt, String content, long articleId) {
        this.id = id;
        this.postedAt = postedAt;
        this.content = content;
        this.articleId = articleId;
        // Default is unsure
        this.profane = Profanity.UNSURE;
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
