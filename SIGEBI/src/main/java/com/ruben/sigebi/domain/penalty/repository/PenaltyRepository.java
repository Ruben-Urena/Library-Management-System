package com.ruben.sigebi.domain.penalty.repository;

import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.PenaltyId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceID;
import com.ruben.sigebi.domain.loan.valueObjects.LoanId;
import com.ruben.sigebi.domain.penalty.entity.Penalty;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PenaltyRepository  {
    Optional<Penalty> findById(PenaltyId penaltyId);
    void save(Penalty penalty);
    Optional<Penalty> findActivePenaltyByLoan(LoanId loanId, UserId userId);
    Optional<List<Penalty>> findPenaltyByUserId(UserId userId);
    List<Penalty> findAllActiveAndDueDatePenalty();
    Optional<List<Penalty>> findPenaltyByResourceId(ResourceID resourceID);
}