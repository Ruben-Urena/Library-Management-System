package com.ruben.sigebi.infrastructure.persistence.entity.user.embed;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class PasswordEmbeddable {
    @Column(name = "password_hash")
    private String value;

    public PasswordEmbeddable() {
    }

    public PasswordEmbeddable(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}