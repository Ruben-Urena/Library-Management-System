package com.ruben.sigebi.infrastructure.persistence.repository.penalty;

import com.ruben.sigebi.infrastructure.persistence.entity.penalty.PenaltyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpringDataPenaltyRepository extends JpaRepository<PenaltyEntity, UUID> {
    Optional<PenaltyEntity> findByLoanIdAndUserIdAndStatus(
            String loanId,
            String userId,
            String status
    );
    List<PenaltyEntity> findByUserId(String userId);
    List<PenaltyEntity> findByStatusAndDueDateBefore(String status, Instant now);
    List<PenaltyEntity> findByResourceId(String resourceId);

}
