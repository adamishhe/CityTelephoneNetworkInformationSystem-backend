package ru.adamishhe.citytelephonenetworkinformationsystem.controllers;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.adamishhe.citytelephonenetworkinformationsystem.model.Phone;
import ru.adamishhe.citytelephonenetworkinformationsystem.service.PhoneService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/phones")
public class PhoneController {

    private final PhoneService phoneService;

    public PhoneController(PhoneService phoneService) {
        this.phoneService = phoneService;
    }

    @GetMapping
    public Page<Phone> getAllPhones(@RequestParam(defaultValue = "0") int page,
                                    @RequestParam(defaultValue = "10") int size) {
        return phoneService.getAllPhones(page, size);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Phone> getPhoneById(@PathVariable Long id) {
        Optional<Phone> phone = phoneService.getPhoneById(id);
        return phone.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/free-phones")
    public List<Object[]> getFreePhonesByAtsCityDistrict(
            @RequestParam String serialNo,
            @RequestParam String cityName,
            @RequestParam String districtName
    ) {
        return phoneService.getFreePhonesByAtsCityDistrict(serialNo, cityName, districtName);
    }

    @GetMapping("/payphones")
    public List<Object[]> getPayphonesByAtsCityDistrict(
            @RequestParam String serialNo,
            @RequestParam String cityName,
            @RequestParam String districtName
    ) {
        return phoneService.getPayphonesByAtsCityDistrict(serialNo, cityName, districtName);
    }

    @GetMapping("/intercity-call-phones")
    public List<Object[]> getIntercityCallPhones() {
        return phoneService.getIntercityCallPhones();
    }

    @GetMapping("/phones-by-address")
    public List<Object[]> getPhonesByAddress() {
        return phoneService.getPhonesByAddress();
    }

    @GetMapping("/subscriber-by-phone")
    public List<Object[]> getSubscriberByPhoneNo(@RequestParam String phoneNo) {
        return phoneService.getSubscriberByPhoneNo(phoneNo);
    }

    @GetMapping("/paired-phones-with-free-phones")
    public List<Object[]> getPairedPhonesWithFreePhones() {
        return phoneService.getPairedPhonesWithFreePhones();
    }

    @GetMapping("/debtors")
    public List<Object[]> getDebtorsByAtsSerialAndDistrict(@RequestParam String serialNo, @RequestParam String districtName) {
        return phoneService.getDebtorsByAtsSerialAndDistrict(serialNo, districtName);
    }

    @PostMapping
    public Phone createPhone(@RequestBody Phone phone) {
        return phoneService.createPhone(phone);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Phone> updatePhone(@PathVariable Long id, @RequestBody Phone phoneDetails) {
        Phone updatedPhone = phoneService.updatePhone(id, phoneDetails);
        return ResponseEntity.ok(updatedPhone);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePhone(@PathVariable Long id) {
        phoneService.deletePhone(id);
        return ResponseEntity.noContent().build();
    }
}