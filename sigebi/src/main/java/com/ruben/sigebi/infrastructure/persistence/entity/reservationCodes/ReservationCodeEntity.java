package com.ruben.sigebi.infrastructure.persistence.entity.reservationCodes;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "reservationCodes")
public class ReservationCodeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "code")
    private String code;

    public ReservationCodeEntity(UUID id, String code) {
        this.id = id;
        this.code = code;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
