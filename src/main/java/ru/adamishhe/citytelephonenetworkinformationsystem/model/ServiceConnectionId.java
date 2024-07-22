package ru.adamishhe.citytelephonenetworkinformationsystem.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

/**
 * Составной первичный ключ для сущности ServiceConnection
 */
@Embeddable
@Data
public class ServiceConnectionId implements Serializable {

    @Column(name = "service_id")
    private Long serviceId;

    @Column(name = "subscription_id")
    private Long subscriptionId;

    public ServiceConnectionId(Long serviceId, Long subscriptionId) {
        this.serviceId = serviceId;
        this.subscriptionId = subscriptionId;
    }

    public ServiceConnectionId() {

    }
}
