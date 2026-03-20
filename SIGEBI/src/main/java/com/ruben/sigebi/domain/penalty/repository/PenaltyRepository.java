package com.ruben.sigebi.domain.penalty.repository;

import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.PenaltyId;
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

public interface PenaltyRepository  {
    public Optional<Penalty> findById(PenaltyId penaltyId);
    public void save(Penalty penalty);
    public Optional<Penalty> findActivePenaltyByLoan(LoanId loanId, UserId userId);
    public Optional<List<Penalty>> findPenaltyByUserId(UserId userId);
    public List<Penalty> findAllActiveAndDueDatePenalty();
    public Optional<List<Penalty>> findPenaltyByResourceId(ResourceID resourceID);

}