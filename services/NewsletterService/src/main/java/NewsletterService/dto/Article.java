package NewsletterService.dto;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import java.util.Date;

@Data
@Getter
@Setter
public class Article {
    private String title;
    private String author;
    private String content;
    private Date publishedAt;
}
