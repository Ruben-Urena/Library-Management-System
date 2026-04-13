package com.ruben.sigebi.domain.roles.exeption;
import com.ruben.sigebi.domain.common.exception.InvalidationException;

public class InvalidRoleException extends InvalidationException {
    public InvalidRoleException(String message) {
        super(message);
    }
}
