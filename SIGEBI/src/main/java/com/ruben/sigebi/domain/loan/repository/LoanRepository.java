package com.ruben.sigebi.domain.loan.repository;

import com.ruben.sigebi.domain.loan.entity.Loan;
import com.ruben.sigebi.domain.loan.valueObjects.LoanId;
import java.util.List;
import java.util.Optional;

public interface LoanRepository {
    void save(Loan loan);
    Optional<Loan> findById(LoanId id);

    List<Loan> findActiveLoansByUserId(String userId);
}