package ru.adamishhe.citytelephonenetworkinformationsystem.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.adamishhe.citytelephonenetworkinformationsystem.model.City;
import ru.adamishhe.citytelephonenetworkinformationsystem.model.District;
import ru.adamishhe.citytelephonenetworkinformationsystem.repository.DistrictRepository;

import java.util.Optional;

@Service
public class DistrictService {


    private final DistrictRepository districtRepository;

    private final CityService cityService;

    public DistrictService(DistrictRepository districtRepository, CityService cityService) {
        this.districtRepository = districtRepository;
        this.cityService = cityService;
    }

    public Page<District> getAllDistricts(Pageable pageable) {
        return districtRepository.findAll(pageable);
    }

    public District createDistrict(District district) {
        return districtRepository.save(district);
    }

    public District getDistrictById(Long id) {
        return districtRepository.findById(id).orElseThrow(() -> new RuntimeException("District not found with id " + id));
    }

    public District updateDistrict(Long id, District districtDetails) {
        Optional<District> optionalDistrict = districtRepository.findById(id);
        if (optionalDistrict.isPresent()) {
            District district = optionalDistrict.get();
            if (districtDetails.getName() != null && !districtDetails.getName().isEmpty()) {
                district.setName(districtDetails.getName());
            }
            if (districtDetails.getCity() != null && districtDetails.getCity().getId() != null) {
                City city = cityService.getCityById(districtDetails.getCity().getId());
                district.setCity(city);
            }
            return districtRepository.save(district);
        } else {
            throw new RuntimeException("District not found with id " + id);
        }
    }

    public void deleteDistrict(Long id) {
        districtRepository.deleteById(id);
    }
}