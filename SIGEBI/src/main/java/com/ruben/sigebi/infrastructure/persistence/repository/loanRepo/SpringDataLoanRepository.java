package com.ruben.sigebi.infrastructure.persistence.repository.loanRepo;
import com.ruben.sigebi.domain.common.enums.Status;
import com.ruben.sigebi.domain.loan.enums.PendingState;
import com.ruben.sigebi.infrastructure.persistence.entity.loan.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface SpringDataLoanRepository
        extends JpaRepository<LoanEntity, UUID> {
    Set<LoanEntity> findByUser_Id(UUID userId);


    @Query("SELECT l FROM LoanEntity l WHERE l.user.id = :userId AND l.pendingState = :pendingState AND l.status = :status")
    List<LoanEntity> findLoansByUserStateAndStatus(
            @Param("userId") UUID userId,
            @Param("pendingState") PendingState pendingState,
            @Param("status") Status status
    );


    @Query("SELECT l FROM LoanEntity l WHERE l.pendingState = :pendingState AND l.status = :status")
    List<LoanEntity> findLoansByPendingStateAndStatus(
            @Param("pendingState") PendingState pendingState,
            @Param("status") Status status
    );


    @Query("SELECT l FROM LoanEntity l WHERE l.status = :status AND l.pendingState = :pendingState")
    List<LoanEntity> findAllActiveOverdueLoans(
            @Param("status") Status status,
            @Param("pendingState") PendingState pendingState
    );


    Optional<LoanEntity> findByStatus(Status status);

    // ✅
    @Query("SELECT l FROM LoanEntity l WHERE l.status = :status")
    Set<LoanEntity> findAllActiveLoans(@Param("status") Status status);


    Set<LoanEntity> findByStatusAndUserId(Status status, UUID userId);
}