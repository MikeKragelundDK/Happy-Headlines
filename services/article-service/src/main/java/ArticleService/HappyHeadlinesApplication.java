package ArticleService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
// For cache scheduling
@EnableScheduling
public class HappyHeadlinesApplication {

	public static void main(String[] args) {
		SpringApplication.run(HappyHeadlinesApplication.class, args);
	}

}
