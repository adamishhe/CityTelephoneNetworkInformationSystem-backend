package ru.adamishhe.citytelephonenetworkinformationsystem.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "phones")
@Data
public class Phone {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "phone_id")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "ats_id", nullable = false)
    private ATS ats;

    @Column(name = "phone_type", nullable = false)
    @Enumerated(EnumType.STRING)
    private PhoneType type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @Column(name = "phone_no", nullable = false)
    private String phoneNumber;

}
