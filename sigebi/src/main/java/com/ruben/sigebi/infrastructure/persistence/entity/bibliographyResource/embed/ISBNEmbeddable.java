package com.ruben.sigebi.infrastructure.persistence.entity.bibliographyResource.embed;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class ISBNEmbeddable {

    @Column(name = "isbn", nullable = false, unique = true)
    private String value;

    protected ISBNEmbeddable() {

    }

    public ISBNEmbeddable(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}