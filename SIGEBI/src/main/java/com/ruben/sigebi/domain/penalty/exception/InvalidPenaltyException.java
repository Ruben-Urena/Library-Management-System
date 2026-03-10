package com.ruben.sigebi.domain.penalty.exception;
import com.ruben.sigebi.domain.common.exception.InvalidationException;

public class InvalidPenaltyException extends InvalidationException {
    public InvalidPenaltyException(String message) {
        super(message);
    }
}
