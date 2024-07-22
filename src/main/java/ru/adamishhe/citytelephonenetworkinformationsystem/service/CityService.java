package ru.adamishhe.citytelephonenetworkinformationsystem.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.adamishhe.citytelephonenetworkinformationsystem.model.City;
import ru.adamishhe.citytelephonenetworkinformationsystem.repository.CityRepository;

import java.util.List;
import java.util.Optional;

@Service
public class CityService {

    private CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public Page<City> getAllCities(Pageable pageable) {
        return cityRepository.findAll(pageable);
    }

    public City createCity(City city) {
        return cityRepository.save(city);
    }

    public City updateCity(Long id, City cityDetails) {
        Optional<City> optionalCity = cityRepository.findById(id);
        if (optionalCity.isPresent()) {
            City city = optionalCity.get();
            city.setName(cityDetails.getName());
            return cityRepository.save(city);
        } else {
            throw new RuntimeException("City not found with id " + id);
        }
    }

    public void deleteCity(Long id) {
        cityRepository.deleteById(id);
    }

    public City getCityById(Long id) {
        Optional<City> optionalCity = cityRepository.findById(id);
        if (optionalCity.isPresent()) {
            return optionalCity.get();
        } else {
            throw new RuntimeException("City not found with id " + id);
        }
    }

    public List<Object[]> getCityWithMostIntercityCalls() {
        return cityRepository.findCityWithMostIntercityCalls();
    }
}