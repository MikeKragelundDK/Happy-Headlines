package CommentService.dto;

import CommentService.util.DateOrDateTimeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Getter
@Setter
public class CommentRequest {
    private String content;
    private long articleId;
    @JsonDeserialize(using = DateOrDateTimeDeserializer.class)
    private LocalDateTime postedAt;
}
