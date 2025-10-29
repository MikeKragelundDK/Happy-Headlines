package SubscriberService.service;

import SubscriberService.dao.SubscriberRepository;
import SubscriberService.entites.Subscriber;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@RequiredArgsConstructor
@Service
public class SubscriberServiceImpl implements SubscriberService_I{

    private final SubscriberRepository subscriberRepository;

    @Override
    public List<Subscriber> getSubscribers() {
        return subscriberRepository.findAll();
    }

    @Override
    // We only need the mail here, JPA does the rest.
    public Subscriber addSubscriber(String email) {
        Subscriber subscriber = new Subscriber(email);
        return subscriberRepository.save(subscriber);
    }

    @Override
    // We gotta make this atomic, for race conditions.
    @Transactional
    public boolean deleteSubscriber(long id) {
        boolean found = subscriberRepository.existsById(id);
        if(found){
            subscriberRepository.deleteById(id);
        }
        return found;
    }
}
