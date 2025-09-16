package ArticleService.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Setter
@Getter
@Entity
@Table(name="articles")
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id", unique = true)
    private long id;
    @Column(name="title")
    private String title;
    @Column(name="author")
    private String author;
    @Column(name="published_at")
    private LocalDateTime publishedAt;
    @Column(name="content")
    private String content;

    public Article(String title, String author, LocalDateTime published_at, String content) {
        this.title = title;
        this.author = author;
        this.publishedAt = published_at;
        this.content = content;
    }
    public Article(long id,String title, String author, LocalDateTime published_at, String content) {
        this.title = title;
        this.author = author;
        this.publishedAt = published_at;
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
