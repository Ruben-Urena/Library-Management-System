package com.ruben.sigebi.domain.bibliographyResource.valueObject;

import java.util.Objects;

public record LoanableResourceId(ResourceID value) {
    public LoanableResourceId {
        Objects.requireNonNull(value);
    }
}
