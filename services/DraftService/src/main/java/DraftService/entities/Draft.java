package DraftService.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name="drafts", schema = "draftdatabase")
public class Draft {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true)
    private long id;
    @Column(name="title", nullable = true)
    private String title;
    @Column(name="author", nullable = false)
    private String author;
    @Column(name="content", nullable = true)
    private String content;
    @Column(name="last_edited_at", nullable = false)
    private LocalDateTime lastEditedAt;

    protected Draft() {}

    public Draft(String title, String author, String content, LocalDateTime lastEditedAt) {
        this.title = title;
        this.author = author;
        this.content = content;
        this.lastEditedAt = lastEditedAt;
    }

    @Override
    public String toString() {
        return "Draft{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                ", lastEditedAt=" + lastEditedAt +
                '}';
    }
}
