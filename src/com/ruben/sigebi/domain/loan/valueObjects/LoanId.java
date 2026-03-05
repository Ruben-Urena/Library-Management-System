package com.ruben.sigebi.domain.loan.valueObjects;
import java.util.Objects;
import java.util.UUID;

public record LoanId(UUID loanID) {
    public LoanId {
        Objects.requireNonNull(loanID);
    }
}
