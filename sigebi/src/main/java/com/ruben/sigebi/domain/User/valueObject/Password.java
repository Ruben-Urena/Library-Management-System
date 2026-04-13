package com.ruben.sigebi.domain.User.valueObject;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;


public record Password(
        String value) {
    public Password {

        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Password cannot be empty");
        }

        if (value.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }


        if (!value.matches(".*[0-9].*")) {
            throw new IllegalArgumentException("Password must contain at least one number");
        }


        if (!value.matches(".*[A-Z].*")) {
            throw new IllegalArgumentException("Password must contain at least one uppercase letter");
        }
    }
    @Override
    public String toString() {
        return "********";
    }
}