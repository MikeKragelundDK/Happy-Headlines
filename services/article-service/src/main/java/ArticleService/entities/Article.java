package ArticleService.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;


@Setter
@Getter
@Entity
@Table(name="articles")
public class Article implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", unique = true)
    private long id;
    @Column(name="title")
    private String title;
    @Column(name="author")
    private String author;
    @Column(name="published_at")
    private Date publishedAt;
    @Column(name="content")
    private String content;

    public Article(String title, String author, String content) {
        this.title = title;
        this.author = author;
        this.publishedAt = Date.from(LocalDateTime.now().atZone(java.time.ZoneId.systemDefault()).toInstant());
        this.content = content;
    }
    public Article(long id,String title, String author,Date publishedAt, String content) {
        this.title = title;
        this.author = author;
        this.publishedAt = publishedAt;;
        this.id = id;
        this.content = content;
    }

    protected Article() {
    }


    @Override
    public String toString() {
        return "article{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", published_at=" + publishedAt +
                '}';
    }
}
