package ru.adamishhe.citytelephonenetworkinformationsystem.controllers;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.web.bind.annotation.*;
import ru.adamishhe.citytelephonenetworkinformationsystem.model.ServiceConnection;
import ru.adamishhe.citytelephonenetworkinformationsystem.service.ServiceConnectionService;

@RestController
@RequestMapping("/api/service-connections")
public class ServiceConnectionController {

    private final ServiceConnectionService serviceConnectionService;

    public ServiceConnectionController(ServiceConnectionService serviceConnectionService) {
        this.serviceConnectionService = serviceConnectionService;
    }

    @GetMapping
    public Page<ServiceConnection> getAllServiceConnections(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size) {
        return serviceConnectionService.getAllServiceConnections(PageRequest.of(page, size));
    }

    @PostMapping
    public ServiceConnection createServiceConnection(@RequestBody ServiceConnection serviceConnection) {
        return serviceConnectionService.saveServiceConnection(serviceConnection);
    }

    @PutMapping("/{serviceId}/{subscriptionId}")
    public ServiceConnection updateServiceConnection(@PathVariable Long serviceId, @PathVariable Long subscriptionId, @RequestBody ServiceConnection serviceConnection) {
        return serviceConnectionService.updateServiceConnection(serviceId, subscriptionId, serviceConnection);
    }

    @DeleteMapping("/{serviceId}/{subscriptionId}")
    public void deleteServiceConnection(@PathVariable Long serviceId, @PathVariable Long subscriptionId) {
        serviceConnectionService.deleteServiceConnection(serviceId, subscriptionId);
    }
}
