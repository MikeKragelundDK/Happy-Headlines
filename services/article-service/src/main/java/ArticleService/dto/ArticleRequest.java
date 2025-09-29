package ArticleService.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;


@Data
@Getter
@Setter
public class ArticleRequest {
    private String title;
    private String author;
    private Date publishedAt;
    private String content;
}