package com.ruben.sigebi.domain.User.valueObject;
import java.util.Objects;
import java.util.UUID;

public record RoleID(UUID value) {
    public RoleID {
        Objects.requireNonNull(value);
    }
}
