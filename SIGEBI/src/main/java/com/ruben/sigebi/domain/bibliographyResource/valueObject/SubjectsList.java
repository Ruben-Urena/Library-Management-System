package com.ruben.sigebi.domain.bibliographyResource.valueObject;
import com.ruben.sigebi.domain.common.exception.InvalidationException;

import java.util.ArrayList;
import java.util.List;

public class SubjectsList {
    private final List<String> strings = new ArrayList<>();

    public void add(String value) {
        if (value == null || value.isBlank()) {
            throw new InvalidationException("String cannot be blank or null");
        }
        strings.add(value);
    }

    public List<String> getStrings() {
        return List.copyOf(strings);
    }

}
