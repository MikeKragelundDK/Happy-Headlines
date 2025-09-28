package NewsletterService.dto;

import NewsletterService.util.DateOrDateTimeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Optional;

@Data
@Getter
@Setter
public class Article {
    private String title;
    private String author;
    private String content;
    @JsonDeserialize(using = DateOrDateTimeDeserializer.class)
    private LocalDateTime publishedAt;
}
