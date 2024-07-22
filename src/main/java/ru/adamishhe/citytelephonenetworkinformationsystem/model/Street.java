package ru.adamishhe.citytelephonenetworkinformationsystem.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "streets")
@Data
public class Street {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "street_id")
    private Long id;

    @Column(name = "street_name", nullable = false)
    private String name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "district_id", nullable = false)
    private District district;

}
