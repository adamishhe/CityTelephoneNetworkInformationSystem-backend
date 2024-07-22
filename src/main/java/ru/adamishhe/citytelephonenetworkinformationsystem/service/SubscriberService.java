package ru.adamishhe.citytelephonenetworkinformationsystem.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import ru.adamishhe.citytelephonenetworkinformationsystem.model.Subscriber;
import ru.adamishhe.citytelephonenetworkinformationsystem.repository.SubscriberRepository;

@Service
public class SubscriberService {


    private final SubscriberRepository subscriberRepository;

    public SubscriberService(SubscriberRepository subscriberRepository) {
        this.subscriberRepository = subscriberRepository;
    }

    public Page<Subscriber> getAllSubscribers(Pageable pageable) {
        return subscriberRepository.findAll(pageable);
    }

    public Subscriber createSubscriber(Subscriber subscriber) {
        return subscriberRepository.save(subscriber);
    }

    public Optional<Subscriber> getSubscriberById(Long id) {
        return subscriberRepository.findById(id);
    }

    public List<Object[]> getSubscribersByAtsAndBenefitAndAgeRange(String serialNo, double benefit, int ageStart, int ageEnd) {
        return subscriberRepository.findSubscribersByAtsAndBenefitAndAgeRange(serialNo, benefit, ageStart, ageEnd);
    }

    public List<Object[]> getSubscribersWithDebt(String serialNo, String cityName, String districtName) {
        return subscriberRepository.findSubscribersWithDebt(serialNo, cityName, districtName);
    }

    public List<Object[]> getSubscriberPercentagesByAtsType() {
        return subscriberRepository.findSubscriberPercentagesByAtsType();
    }

    public List<Object[]> getSubscriberPercentagesByAtsSerial(String serialNo) {
        return subscriberRepository.findSubscriberPercentagesByAtsSerial(serialNo);
    }

    public List<Object[]> getSubscriberPercentagesByCityDistrict(String cityName, String districtName) {
        return subscriberRepository.findSubscriberPercentagesByCityDistrict(cityName, districtName);
    }

    public List<Object[]> getSubscribersByAtsType() {
        return subscriberRepository.findSubscribersByAtsType();
    }

    public List<Object[]> getSubscribersByAtsSerial(String serialNo) {
        return subscriberRepository.findSubscribersByAtsSerial(serialNo);
    }

    public List<Object[]> getSubscribersByCityAndDistrict(String cityName, String districtName) {
        return subscriberRepository.findSubscribersByCityAndDistrict(cityName, districtName);
    }

    public List<Object[]> getBenefitParallelSubscribers() {
        return subscriberRepository.findBenefitParallelSubscribers();
    }

    public Subscriber updateSubscriber(Long id, Subscriber subscriberDetails) {
        Optional<Subscriber> optionalSubscriber = subscriberRepository.findById(id);
        if (optionalSubscriber.isPresent()) {
            Subscriber subscriber = optionalSubscriber.get();
            if (subscriberDetails.getFirstName() != null && !subscriberDetails.getFirstName().isEmpty()) {
                subscriber.setFirstName(subscriberDetails.getFirstName());
            }
            if (subscriberDetails.getLastName() != null && !subscriberDetails.getLastName().isEmpty()) {
                subscriber.setLastName(subscriberDetails.getLastName());
            }
            if (subscriberDetails.getSurname() != null) {
                subscriber.setSurname(subscriberDetails.getSurname());
            }
            if (subscriberDetails.getGender() != null) {
                subscriber.setGender(subscriberDetails.getGender());
            }
            if (subscriberDetails.getAge() != null) {
                subscriber.setAge(subscriberDetails.getAge());
            }
            if (subscriberDetails.getBenefit() != null) {
                subscriber.setBenefit(subscriberDetails.getBenefit());
            }
            return subscriberRepository.save(subscriber);
        } else {
            throw new RuntimeException("Subscriber not found with id " + id);
        }
    }

    public void deleteSubscriber(Long id) {
        subscriberRepository.deleteById(id);
    }
}
