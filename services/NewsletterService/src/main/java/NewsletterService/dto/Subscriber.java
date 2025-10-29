package NewsletterService.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Data
@Setter
@Getter
public class Subscriber {
    private long id;
    private LocalDateTime timestamp;
    private String email;
}
