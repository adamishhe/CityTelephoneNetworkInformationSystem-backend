package ru.adamishhe.citytelephonenetworkinformationsystem.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.adamishhe.citytelephonenetworkinformationsystem.model.City;
import ru.adamishhe.citytelephonenetworkinformationsystem.model.District;
import ru.adamishhe.citytelephonenetworkinformationsystem.service.CityService;
import ru.adamishhe.citytelephonenetworkinformationsystem.service.DistrictService;

@RestController
@RequestMapping("/api/districts")
public class DistrictController {


    private final DistrictService districtService;

    private final CityService cityService;

    public DistrictController(DistrictService districtService, CityService cityService) {
        this.districtService = districtService;
        this.cityService = cityService;
    }

    @GetMapping
    public Page<District> getAllDistricts(Pageable pageable) {
        return districtService.getAllDistricts(pageable);
    }

    @PostMapping
    public District createDistrict(@RequestBody District district) {
        City city = cityService.getCityById(district.getCity().getId());
        district.setCity(city);
        return districtService.createDistrict(district);
    }

    @PutMapping("/{id}")
    public ResponseEntity<District> updateDistrict(@PathVariable Long id, @RequestBody District districtDetails) {
        if (districtDetails.getCity() != null && districtDetails.getCity().getId() != null) {
            City city = cityService.getCityById(districtDetails.getCity().getId());
            districtDetails.setCity(city);
        }
        District updatedDistrict = districtService.updateDistrict(id, districtDetails);
        return ResponseEntity.ok(updatedDistrict);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDistrict(@PathVariable Long id) {
        districtService.deleteDistrict(id);
        return ResponseEntity.ok().build();
    }
}
