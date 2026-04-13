package com.ruben.sigebi.domain.roles.valueObjects;
import java.util.Objects;
import java.util.UUID;

public record RoleID(UUID value) {
    public RoleID {
        Objects.requireNonNull(value);
    }
}
