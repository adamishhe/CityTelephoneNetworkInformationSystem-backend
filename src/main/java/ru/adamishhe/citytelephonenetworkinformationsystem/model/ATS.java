package ru.adamishhe.citytelephonenetworkinformationsystem.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ats")
@Data
public class ATS {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ats_id")
    private Long id;

    @Column(name = "serial_no", unique = true, nullable = false, length = 11)
    private String serialNo;

    @Column(name = "first_phone_no", nullable = false)
    private String firstPhoneNo;

    @Column(name = "last_phone_no", nullable = false)
    private String lastPhoneNo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "org_id", nullable = false)
    private Organization organization;

    @Enumerated(EnumType.STRING)
    private Type type;

}
