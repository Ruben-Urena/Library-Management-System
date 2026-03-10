package com.ruben.sigebi.domain.User.exeption;

import com.ruben.sigebi.domain.common.exception.InvalidationException;

public class InvalidSpecialNameException extends InvalidationException {
    public InvalidSpecialNameException(String message) {
        super(message);
    }
}
