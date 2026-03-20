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
    // ✅ JPA navega: loan → id, user → id, status es campo directo
    Optional<PenaltyEntity> findByLoanIdAndUserIdAndStatus(
            UUID loanId,
            UUID userId,
            Status status
    );

    // ✅ JPA navega: user → id
    List<PenaltyEntity> findByUserId(UUID userId);

    // ✅ status es campo directo, endDate es el nombre correcto del campo
    // (antes era findByStatusAndDueDateBefore — dueDate no existe en PenaltyEntity)
    List<PenaltyEntity> findByStatusAndEndDateBefore(Status status, Instant now);

    // ❌ PenaltyEntity no tiene campo resource — navega loan → resource → id
    @Query("SELECT p FROM PenaltyEntity p WHERE p.loan.resource.id = :resourceId")
    List<PenaltyEntity> findByResourceId(@Param("resourceId") UUID resourceId);
}
