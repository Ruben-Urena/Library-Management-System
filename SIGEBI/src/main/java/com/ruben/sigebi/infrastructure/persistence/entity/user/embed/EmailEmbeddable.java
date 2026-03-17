package com.ruben.sigebi.infrastructure.persistence.entity.user.embed;
import jakarta.persistence.Embeddable;

@Embeddable
public class EmailEmbeddable {
    private String email;

    public EmailEmbeddable(String email) {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}