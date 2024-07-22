package ru.adamishhe.citytelephonenetworkinformationsystem.model;


import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "services")
@Data
public class ServiceS {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "service_id")
    private Long id;

    @Column(name = "service_name", nullable = false, unique = true)
    private String name;

    @Column(name = "service_cost", nullable = false)
    private Double cost;

}
