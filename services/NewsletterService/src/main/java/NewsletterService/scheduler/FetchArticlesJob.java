package NewsletterService.scheduler;

import NewsletterService.clients.ArticleClient;
import NewsletterService.dto.Article;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FetchArticlesJob {
    private final ArticleClient articleClient;


    // for production once every day.
//    @Scheduled(fixedDelayString = "${comment.cleanup-interval:PT24H}")

    // for testing, ever 5 minutes.
    @Scheduled(fixedDelayString = "${comment.cleanup-interval:PT5M}")
    public void getTop5Articles(){
        /**
         *  Right now i just fetch top 5 articles
         *  In a later iteration we need to use these, to send out to subscribers.
         *  Implementing that part might be smarter when we know how we want subscriber service to look like
         *  Consider Making a "Urgent/important/newsletter boolean in articles - and fetch them with top 5.. idk?"
         *  TODO
         */
        List<Article> top5Articles =  articleClient.getTop5Articles();
        log.info("Fetched top 5 articles" + top5Articles );
    }
}
