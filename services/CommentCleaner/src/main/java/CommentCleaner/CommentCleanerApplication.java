package CommentCleaner;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CommentCleanerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CommentCleanerApplication.class, args);
	}

}
