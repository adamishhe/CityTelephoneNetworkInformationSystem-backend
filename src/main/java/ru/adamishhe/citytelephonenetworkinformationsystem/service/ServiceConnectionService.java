package ru.adamishhe.citytelephonenetworkinformationsystem.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.adamishhe.citytelephonenetworkinformationsystem.model.ServiceConnection;
import ru.adamishhe.citytelephonenetworkinformationsystem.model.ServiceConnectionId;
import ru.adamishhe.citytelephonenetworkinformationsystem.repository.ServiceConnectionRepository;

import java.util.Optional;

@Service
public class ServiceConnectionService {

    private final ServiceConnectionRepository serviceConnectionRepository;

    public ServiceConnectionService(ServiceConnectionRepository serviceConnectionRepository) {
        this.serviceConnectionRepository = serviceConnectionRepository;
    }

    public Page<ServiceConnection> getAllServiceConnections(Pageable pageable) {
        return serviceConnectionRepository.findAll(pageable);
    }

    public ServiceConnection saveServiceConnection(ServiceConnection serviceConnection) {
        return serviceConnectionRepository.save(serviceConnection);
    }

    public ServiceConnection updateServiceConnection(Long serviceId, Long subscriptionId, ServiceConnection serviceConnection) {
        ServiceConnectionId id = new ServiceConnectionId(serviceId, subscriptionId);
        Optional<ServiceConnection> existingConnection = serviceConnectionRepository.findById(id);

        if (existingConnection.isPresent()) {
            ServiceConnection updatedConnection = existingConnection.get();
            if (serviceConnection.getPaymentDate() != null) {
                updatedConnection.setPaymentDate(serviceConnection.getPaymentDate());
            }
            return serviceConnectionRepository.save(updatedConnection);
        } else {
            throw new RuntimeException("ServiceConnection not found");
        }
    }

    public void deleteServiceConnection(Long serviceId, Long subscriptionId) {
        ServiceConnectionId id = new ServiceConnectionId(serviceId, subscriptionId);
        serviceConnectionRepository.deleteById(id);
    }
}