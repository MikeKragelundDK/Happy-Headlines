package SubscriberService.dto;

import SubscriberService.entites.Subscriber;

import java.util.List;

public record SubscribeResponse(List<Subscriber> subscribers) {
}
