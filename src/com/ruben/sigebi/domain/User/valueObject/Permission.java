package com.ruben.sigebi.domain.User.valueObject;
import com.ruben.sigebi.domain.User.enums.PermissionStatus;
import com.ruben.sigebi.domain.User.exeption.InvalidPermissionException;
import com.ruben.sigebi.domain.common.exception.InvalidationException;

import java.util.Objects;

public record Permission (String source, String action){
    public Permission  {
        Objects.requireNonNull(source);
        Objects.requireNonNull(action);
        if (source.isBlank() || action.isBlank()) {
            throw  new InvalidationException("source or action cannot be blank");
        }
    }
    public String value(){
        return source + ":" + action;
    }
}
