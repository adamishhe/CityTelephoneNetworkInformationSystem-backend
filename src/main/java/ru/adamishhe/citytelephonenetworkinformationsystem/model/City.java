package ru.adamishhe.citytelephonenetworkinformationsystem.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "cities")
@Data
public class City {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "city_id")
    private Long id;

    @Column(name = "city_name", unique = true, nullable = false)
    private String name;

}
