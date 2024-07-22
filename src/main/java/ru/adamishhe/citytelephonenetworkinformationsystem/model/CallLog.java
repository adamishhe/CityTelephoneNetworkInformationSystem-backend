package ru.adamishhe.citytelephonenetworkinformationsystem.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

@Entity
@Data
@Table(name = "call_log")
public class CallLog implements Serializable {



    @EmbeddedId
    private CallLogId id;

    @Column(name = "recipient_no", nullable = false)
    private String recipientNo;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "recipient_ats_address", nullable = false)
    private Address recipientAtsAddress;

    @Column(name = "duration", nullable = false, columnDefinition = "int default 1")
    private Integer duration;

}

