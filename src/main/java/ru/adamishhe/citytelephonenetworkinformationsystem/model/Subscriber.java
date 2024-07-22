package ru.adamishhe.citytelephonenetworkinformationsystem.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "subscribers")
@Data
public class Subscriber {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subscriber_id")
    private Long id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "surname")
    private String surname;

    @Column(name = "gender", length = 1)
    private Character gender;

    @Column(name = "age")
    private Short age;

    @Column(name = "benefit", precision = 3, nullable = false)
    private Double benefit;

}
