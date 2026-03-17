package com.ruben.sigebi.infrastructure.persistence.repository.loanRepo;
import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.common.enums.Status;
import com.ruben.sigebi.domain.loan.entity.Loan;
import com.ruben.sigebi.domain.loan.enums.PendingState;
import com.ruben.sigebi.domain.loan.valueObjects.LoanId;
import com.ruben.sigebi.infrastructure.persistence.entity.loan.LoanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.time.Instant;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface SpringDataLoanRepository
        extends JpaRepository<LoanEntity, UUID> {
    Set<LoanEntity> findByUserId(UUID userId);
    public List<LoanEntity> findLoansByUserStateAndStatus(String userId, String pendingState, String status);
    public List<LoanEntity> findAllActiveOverdueLoans(String status, String pendingState);
    public Optional<LoanEntity> findByStatus(String status);
    public Set<LoanEntity> findAllActiveLoans(String status);
    public Set<LoanEntity> findByStatusAndUser(String status, String userId);
    public List<LoanEntity> findLoansByStateAndStatus(String pendingState, String status);
}