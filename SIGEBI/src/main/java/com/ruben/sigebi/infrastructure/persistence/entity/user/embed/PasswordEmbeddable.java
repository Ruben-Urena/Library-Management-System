package com.ruben.sigebi.infrastructure.persistence.entity.user.embed;

import jakarta.persistence.Embeddable;

@Embeddable
public class PasswordEmbeddable {

    private String value;

    public PasswordEmbeddable(String value) {
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}