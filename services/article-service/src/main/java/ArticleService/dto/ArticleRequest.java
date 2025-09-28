package ArticleService.dto;

import ArticleService.util.DateOrDateTimeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Optional;

@Data
@Getter
@Setter
public class ArticleRequest {
    private String title;
    private String author;
    @JsonDeserialize(using = DateOrDateTimeDeserializer.class)
    private Optional<LocalDateTime> publishedAt;
    private String content;
}