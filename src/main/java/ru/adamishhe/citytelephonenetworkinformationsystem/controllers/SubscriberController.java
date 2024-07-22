package ru.adamishhe.citytelephonenetworkinformationsystem.controllers;

import org.springframework.web.bind.annotation.*;
import ru.adamishhe.citytelephonenetworkinformationsystem.model.Subscriber;
import ru.adamishhe.citytelephonenetworkinformationsystem.service.SubscriberService;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.List;

@RestController
@RequestMapping("/api/subscribers")
public class SubscriberController {

    private final SubscriberService subscriberService;

    public SubscriberController(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    @GetMapping
    public Page<Subscriber> getAllSubscribers(Pageable pageable) {
        return subscriberService.getAllSubscribers(pageable);
    }

    @GetMapping("/search")
    public List<Object[]> getSubscribersByAtsAndBenefitAndAgeRange(
            @RequestParam String serialNo,
            @RequestParam double benefit,
            @RequestParam int ageStart,
            @RequestParam int ageEnd
    ) {
        return subscriberService.getSubscribersByAtsAndBenefitAndAgeRange(serialNo, benefit, ageStart, ageEnd);
    }

    @GetMapping("/debt")
    public List<Object[]> getSubscribersWithDebt(
            @RequestParam String serialNo,
            @RequestParam String cityName,
            @RequestParam String districtName
    ) {
        return subscriberService.getSubscribersWithDebt(serialNo, cityName, districtName);
    }

    @GetMapping("/percentages-by-ats-type")
    public List<Object[]> getSubscriberPercentagesByAtsType() {
        return subscriberService.getSubscriberPercentagesByAtsType();
    }

    @GetMapping("/percentages-by-ats-serial")
    public List<Object[]> getSubscriberPercentagesByAtsSerial(@RequestParam String serialNo) {
        return subscriberService.getSubscriberPercentagesByAtsSerial(serialNo);
    }

    @GetMapping("/percentages-by-city-district")
    public List<Object[]> getSubscriberPercentagesByCityDistrict(@RequestParam String cityName, @RequestParam String districtName) {
        return subscriberService.getSubscriberPercentagesByCityDistrict(cityName, districtName);
    }

    @GetMapping("/by-ats-type")
    public List<Object[]> getSubscribersByAtsType() {
        return subscriberService.getSubscribersByAtsType();
    }

    @GetMapping("/by-ats-serial")
    public List<Object[]> getSubscribersByAtsSerial(@RequestParam String serialNo) {
        return subscriberService.getSubscribersByAtsSerial(serialNo);
    }

    @GetMapping("/by-city-district")
    public List<Object[]> getSubscribersByCityAndDistrict(@RequestParam String cityName, @RequestParam String districtName) {
        return subscriberService.getSubscribersByCityAndDistrict(cityName, districtName);
    }

    @GetMapping("/by-benefit-parallel")
    public List<Object[]> getBenefitParallelSubscribers() {
        return subscriberService.getBenefitParallelSubscribers();
    }

    @PostMapping
    public Subscriber createSubscriber(@RequestBody Subscriber subscriber) {
        return subscriberService.createSubscriber(subscriber);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Subscriber> updateSubscriber(@PathVariable Long id, @RequestBody Subscriber subscriberDetails) {
        Subscriber updatedSubscriber = subscriberService.updateSubscriber(id, subscriberDetails);
        return ResponseEntity.ok(updatedSubscriber);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSubscriber(@PathVariable Long id) {
        subscriberService.deleteSubscriber(id);
        return ResponseEntity.ok().build();
    }
}

