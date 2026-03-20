package com.ruben.sigebi.infrastructure.persistence.entity.bibliographyResource.embed;

import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class LanguageEmbeddable {

    @Column(name = "language", nullable = false)
    private String language;

    protected LanguageEmbeddable() {}

    public LanguageEmbeddable(String language) {
        this.language = language;
    }

    @JsonValue
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}