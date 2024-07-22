package ru.adamishhe.citytelephonenetworkinformationsystem.service;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.adamishhe.citytelephonenetworkinformationsystem.model.ServiceS;
import ru.adamishhe.citytelephonenetworkinformationsystem.repository.ServiceRepository;

import java.util.Optional;

@Service
public class ServiceService {


    private final ServiceRepository serviceRepository;

    public ServiceService(ServiceRepository serviceSRepository) {
        this.serviceRepository = serviceSRepository;
    }

    public Page<ServiceS> getAllServices(Pageable pageable) {
        return serviceRepository.findAll(pageable);
    }

    public ServiceS createService(ServiceS service) {
        return serviceRepository.save(service);
    }

    public ServiceS updateService(Long id, ServiceS serviceDetails) {
        Optional<ServiceS> optionalService = serviceRepository.findById(id);
        if (optionalService.isPresent()) {
            ServiceS service = optionalService.get();
            if (serviceDetails.getName() != null && !serviceDetails.getName().isEmpty()) {
                service.setName(serviceDetails.getName());
            }
            if (serviceDetails.getCost() != null) {
                service.setCost(serviceDetails.getCost());
            }
            return serviceRepository.save(service);
        } else {
            throw new RuntimeException("Service not found with id " + id);
        }
    }

    public void deleteService(Long id) {
        serviceRepository.deleteById(id);
    }
}
