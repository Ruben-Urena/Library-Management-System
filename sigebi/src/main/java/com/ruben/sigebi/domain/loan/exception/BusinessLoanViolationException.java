package com.ruben.sigebi.domain.loan.exception;
import com.ruben.sigebi.domain.common.exception.BusinessRuleViolationException;


public class BusinessLoanViolationException extends BusinessRuleViolationException {
    public BusinessLoanViolationException(String message) {
        super(message);
    }
}
