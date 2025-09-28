package NewsletterService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class NewsletterServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(NewsletterServiceApplication.class, args);
	}

}
