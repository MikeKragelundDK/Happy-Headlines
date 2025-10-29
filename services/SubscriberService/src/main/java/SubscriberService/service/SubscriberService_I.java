package SubscriberService.service;

import SubscriberService.entites.Subscriber;

import java.util.List;

public interface SubscriberService_I {
    List<Subscriber> getSubscribers();
    Subscriber addSubscriber(String email);
    boolean deleteSubscriber(long id);
}
