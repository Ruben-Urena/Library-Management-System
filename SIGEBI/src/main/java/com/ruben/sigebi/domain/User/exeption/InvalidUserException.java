package com.ruben.sigebi.domain.User.exeption;

public class InvalidUserException extends RuntimeException {
    public InvalidUserException(String message) {
        super(message);
    }
}
