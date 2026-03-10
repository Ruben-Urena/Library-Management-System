package com.ruben.sigebi.domain.User.valueObject;

import java.util.Objects;
import java.util.UUID;

public record UserId(UUID value) {
    public UserId {
        Objects.requireNonNull(value);
    }
}
