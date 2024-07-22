package ru.adamishhe.citytelephonenetworkinformationsystem.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.adamishhe.citytelephonenetworkinformationsystem.model.City;
import ru.adamishhe.citytelephonenetworkinformationsystem.service.CityService;

import java.util.List;

@RestController
@RequestMapping("/api/cities")
public class CityController {

    @Autowired
    private CityService cityService;

    @GetMapping
    public Page<City> getAllCities(Pageable pageable) {
        return cityService.getAllCities(pageable);
    }

    @PostMapping
    public City createCity(@RequestBody City city) {
        return cityService.createCity(city);
    }

    @PutMapping("/{id}")
    public ResponseEntity<City> updateCity(@PathVariable Long id, @RequestBody City cityDetails) {
        City updatedCity = cityService.updateCity(id, cityDetails);
        return ResponseEntity.ok(updatedCity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCity(@PathVariable Long id) {
        cityService.deleteCity(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/most-intercity-calls")
    public List<Object[]> getCityWithMostIntercityCalls() {
        return cityService.getCityWithMostIntercityCalls();
    }
}

