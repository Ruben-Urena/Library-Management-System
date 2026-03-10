package com.ruben.sigebi.domain.bibliographyResource.valueObject;

public class ISBN {
    private final String value;

    public ISBN(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("ISBN cannot be empty");
        }
        this.value = value;
    }

    public String value() {
        return value;
    }
}