package com.ruben.sigebi.infrastructure.persistence.entity.user.embed;
import jakarta.persistence.Embeddable;

@Embeddable
public class FullNameEmbeddable {

    private String name;
    private String lastname;

    public FullNameEmbeddable(String name, String s) {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }
}