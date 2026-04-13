package com.ruben.sigebi.domain.User.valueObject;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;


public record EmailAddress(

        String email) {
        public EmailAddress{
                if (email == null || email.isBlank()) {
                        throw new IllegalArgumentException("Email cannot be empty");
                }
                String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[a-z]{2,}$";

                if (!email.matches(emailRegex)) {
                        throw new IllegalArgumentException("Invalid email format: " + email);
                }
        }

}
