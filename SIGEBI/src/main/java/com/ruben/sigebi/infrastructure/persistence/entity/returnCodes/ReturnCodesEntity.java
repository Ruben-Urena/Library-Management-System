package com.ruben.sigebi.infrastructure.persistence.entity.returnCodes;

import jakarta.persistence.*;

import java.util.UUID;

@Entity
@Table(name = "returnCodes")
public class ReturnCodesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "code")
    private String code;

    public ReturnCodesEntity(UUID id, String code) {
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
