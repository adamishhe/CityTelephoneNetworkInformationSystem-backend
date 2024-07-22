package ru.adamishhe.citytelephonenetworkinformationsystem.controllers;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.adamishhe.citytelephonenetworkinformationsystem.model.District;
import ru.adamishhe.citytelephonenetworkinformationsystem.model.Street;
import ru.adamishhe.citytelephonenetworkinformationsystem.service.DistrictService;
import ru.adamishhe.citytelephonenetworkinformationsystem.service.StreetService;

@RestController
@RequestMapping("/api/streets")
@CrossOrigin(origins = "http://localhost:3000")
public class StreetController {


    private final StreetService streetService;


    private final DistrictService districtService;

    public StreetController(StreetService streetService, DistrictService districtService) {
        this.streetService = streetService;
        this.districtService = districtService;
    }

    @GetMapping
    public Page<Street> getAllStreets(Pageable pageable) {
        return streetService.getAllStreets(pageable);
    }

    @PostMapping
    public Street createStreet(@RequestBody Street street) {
        District district = districtService.getDistrictById(street.getDistrict().getId());
        street.setDistrict(district);
        return streetService.createStreet(street);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Street> updateStreet(@PathVariable Long id, @RequestBody Street streetDetails) {
        if (streetDetails.getDistrict() != null && streetDetails.getDistrict().getId() != null) {
            District district = districtService.getDistrictById(streetDetails.getDistrict().getId());
            streetDetails.setDistrict(district);
        }
        Street updatedStreet = streetService.updateStreet(id, streetDetails);
        return ResponseEntity.ok(updatedStreet);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStreet(@PathVariable Long id) {
        streetService.deleteStreet(id);
        return ResponseEntity.ok().build();
    }
}
