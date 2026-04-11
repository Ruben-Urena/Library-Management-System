package com.ruben.sigebi.infrastructure.persistence.repository.penalty;

import com.ruben.sigebi.domain.common.enums.Status;
import com.ruben.sigebi.infrastructure.persistence.entity.penalty.PenaltyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SpringDataPenaltyRepository extends JpaRepository<PenaltyEntity, UUID> {


    Optional<PenaltyEntity> findByLoanIdAndUserIdAndStatus(
            UUID loanId,
            UUID userId,
            Status status
    );


    List<PenaltyEntity> findByUserId(UUID userId);


    List<PenaltyEntity> findByStatusAndEndDateBefore(Status status, Instant now);


    @Query("SELECT p FROM PenaltyEntity p WHERE p.loan.resourceCopy.id = :copyId")
    List<PenaltyEntity> findByCopyId(@Param("copyId") UUID copyId);
}
