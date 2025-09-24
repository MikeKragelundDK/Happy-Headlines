package PublisherService.dto;

import PublisherService.util.DateOrDateTimeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Optional;

@Data
@Setter
@Getter
public class ArticleRecievedDto {
    //Basically the same object that i use for ArticleService Dto
    private String title;
    private String author;
    @JsonDeserialize(using = DateOrDateTimeDeserializer.class)
    private LocalDateTime publishedAt;
    private String content;
}
