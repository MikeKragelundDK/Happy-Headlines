package NewsletterService.scheduler;

import NewsletterService.clients.SubscriberClient;
import NewsletterService.dto.Subscriber;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class FetchSubscribersJob {
    private final SubscriberClient subscriberClient;

    // This just runs every 5 mins for tests.
    // In a production setting you would run this every time a monthly/weekly, newsletter would be sent out.
    // Now we just do a scheduled job, to demonstrate that it works.
    @Scheduled(fixedDelayString = "PT5M")
    public void getSubscribers(){
        List<Subscriber> subscribers = subscriberClient.getAllSubscribers();
        log.info("Fetched subscribers" + subscribers);
    }
}