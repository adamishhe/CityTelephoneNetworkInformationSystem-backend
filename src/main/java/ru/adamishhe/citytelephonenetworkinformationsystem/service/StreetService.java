package ru.adamishhe.citytelephonenetworkinformationsystem.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.adamishhe.citytelephonenetworkinformationsystem.model.District;
import ru.adamishhe.citytelephonenetworkinformationsystem.model.Street;
import ru.adamishhe.citytelephonenetworkinformationsystem.repository.StreetRepository;

import java.util.Optional;

@Service
public class StreetService {


    private final StreetRepository streetRepository;

    private final DistrictService districtService;

    public StreetService(StreetRepository streetRepository, DistrictService districtService) {
        this.streetRepository = streetRepository;
        this.districtService = districtService;
    }

    public Page<Street> getAllStreets(Pageable pageable) {
        return streetRepository.findAll(pageable);
    }

    public Street createStreet(Street street) {
        return streetRepository.save(street);
    }

    public Street updateStreet(Long id, Street streetDetails) {
        Optional<Street> optionalStreet = streetRepository.findById(id);
        if (optionalStreet.isPresent()) {
            Street street = optionalStreet.get();
            if (streetDetails.getName() != null && !streetDetails.getName().isEmpty()) {
                street.setName(streetDetails.getName());
            }
            if (streetDetails.getDistrict() != null && streetDetails.getDistrict().getId() != null) {
                District district = districtService.getDistrictById(streetDetails.getDistrict().getId());
                street.setDistrict(district);
            }
            return streetRepository.save(street);
        } else {
            throw new RuntimeException("Street not found with id " + id);
        }
    }

    public void deleteStreet(Long id) {
        streetRepository.deleteById(id);
    }

    public Street getStreetById(Long id) {
        return streetRepository.findById(id).orElseThrow(() -> new RuntimeException("Street not found with id " + id));
    }
}

