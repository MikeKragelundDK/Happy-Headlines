package SubscriberService.controller;

import SubscriberService.dto.SubscribeRequest;
import SubscriberService.dto.SubscribeResponse;
import SubscriberService.entites.Subscriber;
import SubscriberService.service.SubscriberService_I;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
// Since the endpoints are so simple, we just do a generic /api/subscriber mapping for all endpooints.
@RequestMapping("/api/subscriber")
@RequiredArgsConstructor
public class SubscriberController {

    private final SubscriberService_I subscriberService;

    @PostMapping
    public ResponseEntity<Subscriber> subscribe(@RequestBody SubscribeRequest request) {
        Subscriber addedSubscriber = subscriberService.addSubscriber(request.email());
        return ResponseEntity.status(HttpStatus.CREATED).body(addedSubscriber);
    }

    @DeleteMapping
    public ResponseEntity<String> unsubscribe(@RequestParam long id) {
        boolean deleted = subscriberService.deleteSubscriber(id);
        if(deleted){
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @GetMapping
    public ResponseEntity<SubscribeResponse> getSubscribers() {
        List<Subscriber> subscribers = subscriberService.getSubscribers();
        if(subscribers.isEmpty()){
            ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
        }
        SubscribeResponse response = new SubscribeResponse(subscribers);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
