package ru.adamishhe.citytelephonenetworkinformationsystem.controllers;


import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.adamishhe.citytelephonenetworkinformationsystem.model.ServiceS;
import ru.adamishhe.citytelephonenetworkinformationsystem.service.ServiceService;

@RestController
@RequestMapping("/api/services")

public class ServiceController {


    private final ServiceService serviceService;

    public ServiceController(ServiceService serviceService) {
        this.serviceService = serviceService;
    }

    @GetMapping
    public Page<ServiceS> getAllServices(Pageable pageable) {
        return serviceService.getAllServices(pageable);
    }

    @PostMapping
    public ServiceS createService(@RequestBody ServiceS service) {
        return serviceService.createService(service);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceS> updateService(@PathVariable Long id, @RequestBody ServiceS serviceDetails) {
        ServiceS updatedService = serviceService.updateService(id, serviceDetails);
        return ResponseEntity.ok(updatedService);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteService(@PathVariable Long id) {
        serviceService.deleteService(id);
        return ResponseEntity.ok().build();
    }
}
