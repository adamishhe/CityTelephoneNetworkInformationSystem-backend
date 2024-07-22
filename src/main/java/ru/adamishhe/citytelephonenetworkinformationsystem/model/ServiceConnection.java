package ru.adamishhe.citytelephonenetworkinformationsystem.model;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.sql.Date;
import java.time.LocalDate;

@Entity
@Table(name = "service_connection")
@Data
public class ServiceConnection {

    @EmbeddedId
    private ServiceConnectionId id;

    @Column(name = "payment_date", nullable = false)
    private LocalDate paymentDate;


}


