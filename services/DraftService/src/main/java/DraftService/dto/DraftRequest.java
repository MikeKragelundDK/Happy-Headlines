package DraftService.dto;

import DraftService.util.DateOrDateTimeDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;


import java.util.Date;

@Data
@Getter
@Setter
public class DraftRequest {
    private String title;
    private String author;
    private String content;
    private Date lastEditedAt;
}
