package com.ruben.sigebi.infrastructure.persistence.entity.role.embed;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class SpecialNameEmbeddable {

    @Column(name = "role_name", nullable = false, unique = true)
    private String special;

    protected SpecialNameEmbeddable() {
    }

    public SpecialNameEmbeddable(String special) {
        this.special = special;
    }

    public String getSpecial() {
        return special;
    }

    public void setSpecial(String special) {
        this.special = special;
    }
}