package com.ruben.sigebi.domain.loan.repository;

import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.common.enums.Status;
import com.ruben.sigebi.domain.loan.entity.Loan;
import com.ruben.sigebi.domain.loan.enums.PendingState;
import com.ruben.sigebi.domain.loan.valueObjects.LoanId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface LoanRepository{
    List<Loan> findLoansByUserStateAndStatus(UserId userId, PendingState pendingState, Status status);
    List<Loan> findLoansByStateAndStatus( PendingState pendingState, Status status);
    List<Loan> findAllActiveOverdueLoans();
    void save(Loan loan);
    Optional<Loan> findById(LoanId loanId);
    Set<Loan> findByStatus(Status status);
    Set<Loan> findByStatusAndUser (Status status, UserId userId);
    Set<Loan> findByUser(UserId userId);
    Set<Loan> findAllActiveLoans();
}