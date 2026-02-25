package Sigebi.domain.valueObject.resource;
import Sigebi.domain.common.exception.InValidationException;

import java.util.ArrayList;
import java.util.List;

public class SubjectsList {
    private final List<String> strings = new ArrayList<>();

    public void add(String value) {
        if (value == null || value.isBlank()) {
            throw new InValidationException("String cannot be blank or null");
        }
        strings.add(value);
    }

    public List<String> getStrings() {
        return List.copyOf(strings);
    }

}
