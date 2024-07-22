package ru.adamishhe.citytelephonenetworkinformationsystem.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import ru.adamishhe.citytelephonenetworkinformationsystem.model.ATS;
import ru.adamishhe.citytelephonenetworkinformationsystem.model.Address;
import ru.adamishhe.citytelephonenetworkinformationsystem.model.Phone;
import ru.adamishhe.citytelephonenetworkinformationsystem.repository.PhoneRepository;

import java.util.List;
import java.util.Optional;

@Service
public class PhoneService {

    private final PhoneRepository phoneRepository;
    private final ATSService atsService;
    private final AddressService addressService;

    public PhoneService(PhoneRepository phoneRepository, ATSService atsService, AddressService addressService) {
        this.phoneRepository = phoneRepository;
        this.atsService = atsService;
        this.addressService = addressService;
    }

    public Page<Phone> getAllPhones(int page, int size) {
        return phoneRepository.findAll(PageRequest.of(page, size));
    }

    public Optional<Phone> getPhoneById(Long id) {
        return phoneRepository.findById(id);
    }

    public Phone createPhone(Phone phone) {
        ATS ats = atsService.getATSById(phone.getAts().getId())
                .orElseThrow(() -> new RuntimeException("ATS not found"));
        Address address = addressService.getAddressById(phone.getAddress().getId())
                .orElseThrow(() -> new RuntimeException("Address not found"));
        phone.setAts(ats);
        phone.setAddress(address);
        return phoneRepository.save(phone);
    }

    public Phone updatePhone(Long id, Phone phoneDetails) {
        Phone phone = phoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Phone not found"));

        if (phoneDetails.getAts() != null) {
            ATS ats = atsService.getATSById(phoneDetails.getAts().getId())
                    .orElseThrow(() -> new RuntimeException("ATS not found"));
            phone.setAts(ats);
        }

        if (phoneDetails.getAddress() != null) {
            Address address = addressService.getAddressById(phoneDetails.getAddress().getId())
                    .orElseThrow(() -> new RuntimeException("Address not found"));
            phone.setAddress(address);
        }

        if (phoneDetails.getType() != null) {
            phone.setType(phoneDetails.getType());
        }

        if (phoneDetails.getPhoneNumber() != null) {
            phone.setPhoneNumber(phoneDetails.getPhoneNumber());
        }

        return phoneRepository.save(phone);
    }

    public List<Object[]> getFreePhonesByAtsCityDistrict(String serialNo, String cityName, String districtName) {
        return phoneRepository.findFreePhonesByAtsCityDistrict(serialNo, cityName, districtName);
    }

    public List<Object[]> getPayphonesByAtsCityDistrict(String serialNo, String cityName, String districtName) {
        return phoneRepository.findPayphonesByAtsCityDistrict(serialNo, cityName, districtName);
    }

    public List<Object[]> getIntercityCallPhones() {
        return phoneRepository.findIntercityCallPhones();
    }

    public List<Object[]> getPhonesByAddress() {
        return phoneRepository.findPhonesByAddress();
    }

    public List<Object[]> getSubscriberByPhoneNo(String phoneNo) {
        return phoneRepository.findSubscriberByPhoneNo(phoneNo);
    }

    public List<Object[]> getPairedPhonesWithFreePhones() {
        return phoneRepository.findPairedPhonesWithFreePhones();
    }

    public List<Object[]> getDebtorsByAtsSerialAndDistrict(String serialNo, String districtName) {
        return phoneRepository.findDebtorsByAtsSerialAndDistrict(serialNo, districtName);
    }

    public void deletePhone(Long id) {
        Phone phone = phoneRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Phone not found"));
        phoneRepository.delete(phone);
    }
}