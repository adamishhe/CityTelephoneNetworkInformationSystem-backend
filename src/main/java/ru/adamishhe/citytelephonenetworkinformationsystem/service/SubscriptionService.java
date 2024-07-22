package ru.adamishhe.citytelephonenetworkinformationsystem.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.adamishhe.citytelephonenetworkinformationsystem.model.Phone;
import ru.adamishhe.citytelephonenetworkinformationsystem.model.Subscriber;
import ru.adamishhe.citytelephonenetworkinformationsystem.model.Subscription;
import ru.adamishhe.citytelephonenetworkinformationsystem.repository.SubscriptionRepository;

import java.util.Optional;

@Service
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final PhoneService phoneService;
    private final SubscriberService subscriberService;

    public SubscriptionService(SubscriptionRepository subscriptionRepository, PhoneService phoneService, SubscriberService subscriberService) {
        this.subscriptionRepository = subscriptionRepository;
        this.phoneService = phoneService;
        this.subscriberService = subscriberService;
    }

    public Page<Subscription> getAllSubscriptions(int page, int size) {
        return subscriptionRepository.findAll(PageRequest.of(page, size));
    }

    public Optional<Subscription> getSubscriptionById(Long id) {
        return subscriptionRepository.findById(id);
    }

    public Subscription createSubscription(Subscription subscription) {
        Phone phone = phoneService.getPhoneById(subscription.getPhone().getId())
                .orElseThrow(() -> new RuntimeException("Phone not found"));
        Subscriber subscriber = subscriberService.getSubscriberById(subscription.getSubscriber().getId())
                .orElseThrow(() -> new RuntimeException("Subscriber not found"));
        subscription.setPhone(phone);
        subscription.setSubscriber(subscriber);
        return subscriptionRepository.save(subscription);
    }

    public Subscription updateSubscription(Long id, Subscription subscriptionDetails) {
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));

        if (subscriptionDetails.getPhone() != null) {
            Phone phone = phoneService.getPhoneById(subscriptionDetails.getPhone().getId())
                    .orElseThrow(() -> new RuntimeException("Phone not found"));
            subscription.setPhone(phone);
        }

        if (subscriptionDetails.getSubscriber() != null) {
            Subscriber subscriber = subscriberService.getSubscriberById(subscriptionDetails.getSubscriber().getId())
                    .orElseThrow(() -> new RuntimeException("Subscriber not found"));
            subscription.setSubscriber(subscriber);
        }

        if (subscriptionDetails.getApartment() != null) {
            subscription.setApartment(subscriptionDetails.getApartment());
        }

        return subscriptionRepository.save(subscription);
    }

    public void deleteSubscription(Long id) {
        Subscription subscription = subscriptionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subscription not found"));
        subscriptionRepository.delete(subscription);
    }
}
