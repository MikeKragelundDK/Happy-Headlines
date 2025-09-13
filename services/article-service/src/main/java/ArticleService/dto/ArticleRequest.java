package ArticleService.dto;

import ArticleService.util.DateOrDateTimeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
@Data
@Getter
@Setter
public class ArticleRequest {
    private String title;
    private String author;
    @JsonDeserialize(using = DateOrDateTimeDeserializer.class)
    private LocalDateTime publishedAt;
}