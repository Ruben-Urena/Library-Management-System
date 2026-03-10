package com.ruben.sigebi.domain.loan.exception;
import com.ruben.sigebi.domain.common.exception.InvalidationException;

public class InvalidLoanException extends InvalidationException {
    public InvalidLoanException(String message) {
        super(message);
    }
}
