package ru.adamishhe.citytelephonenetworkinformationsystem.model;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;

/**
 * Составной первичный ключ для сущности CallLog
 */
@Embeddable
@Data
public class CallLogId implements Serializable {

    @Column(name = "phone_id", nullable = false)
    private Long initiator;

    @Column(name = "call_time", nullable = false)
    private ZonedDateTime callTime;

    public CallLogId(Long initiator, ZonedDateTime callTime) {
        this.initiator = initiator;
        this.callTime = callTime;
    }

    public CallLogId() {
    }
}
