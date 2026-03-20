package com.ruben.sigebi.infrastructure.persistence.entity.user.embed;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class FullNameEmbeddable {

    @Column(name = "first_name")
    private String name;
    @Column(name = "last_name")
    private String lastname;

    public FullNameEmbeddable() {
    }

    public FullNameEmbeddable(String name, String lastname) {
        this.name = name;
        this.lastname = lastname;
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