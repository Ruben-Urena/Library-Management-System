package com.ruben.sigebi.domain.User.exeption;
import com.ruben.sigebi.domain.common.exception.InvalidationException;
public class InvalidPermissionException extends InvalidationException {
    public InvalidPermissionException(String message) {
        super(message);
    }
}
