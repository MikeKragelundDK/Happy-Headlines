package Happy.Headlines.entities;

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
    @Column(name="id")
    private long id;
    @Column(name="title")
    private String title;
    @Column(name="author")
    private String author;
    @Column(name="published_at")
    private LocalDateTime published_at;

    public Article(String title, String author, LocalDateTime published_at) {
        this.title = title;
        this.author = author;
        this.published_at = published_at;
    }

    protected Article() {
    }


    @Override
    public String toString() {
        return "article{" +
                "title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", published_at=" + published_at +
                '}';
    }
}
