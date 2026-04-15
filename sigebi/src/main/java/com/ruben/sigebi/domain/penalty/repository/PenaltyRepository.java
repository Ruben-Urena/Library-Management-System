package com.ruben.sigebi.domain.penalty.repository;

import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.PenaltyId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceCopyId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceID;
import com.ruben.sigebi.domain.common.enums.Status;
import com.ruben.sigebi.domain.loan.valueObjects.LoanId;
import com.ruben.sigebi.domain.penalty.entity.Penalty;
import com.ruben.sigebi.infrastructure.persistence.entity.penalty.PenaltyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface PenaltyRepository {
    Optional<Penalty> findById(PenaltyId penaltyId);
    void save(Penalty penalty);
    Optional<Penalty> findActivePenaltyByLoan(LoanId loanId, UserId userId);
    Optional<List<Penalty>> findPenaltyByUserId(UserId userId);
    List<Penalty> findAllActiveAndDueDatePenalty();
    Optional<List<Penalty>> findPenaltyByCopyId(ResourceCopyId copyId);
    List<Penalty> findByStatusAndUserId(Status status, UserId userId);
}
