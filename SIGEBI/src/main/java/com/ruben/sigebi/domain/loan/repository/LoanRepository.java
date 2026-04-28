package com.ruben.sigebi.domain.loan.repository;

import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.common.enums.Status;
import com.ruben.sigebi.domain.loan.entity.Loan;
import com.ruben.sigebi.domain.loan.enums.PendingState;
import com.ruben.sigebi.domain.loan.valueObjects.LoanId;
import com.ruben.sigebi.infrastructure.persistence.entity.loan.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface LoanRepository{

    void save(Loan loan);
    Optional<Loan> findById(LoanId loanId);
    Set<Loan> findByUser(UserId userId);
    List<Loan> findLoansByUserStateAndStatus(UserId userId, PendingState pendingState, Status status);
    List<Loan> findAllActiveOverdueLoans();
    Optional<Loan> findByStatus(Status status);
    Set<Loan> findAllActiveLoans();
    List<Loan> findLoansByStateAndStatus(PendingState pendingState, Status status);
    Set<Loan> findByStatusAndUserId(Status status, UserId userId);
    boolean existByReservationCode();
    boolean existByReturnCodes();

}