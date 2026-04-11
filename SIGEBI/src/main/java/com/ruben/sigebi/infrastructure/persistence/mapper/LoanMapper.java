package com.ruben.sigebi.infrastructure.persistence.mapper;

import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceCopyId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceID;
import com.ruben.sigebi.domain.loan.entity.Loan;
import com.ruben.sigebi.domain.loan.valueObjects.LoanId;
import com.ruben.sigebi.infrastructure.persistence.entity.bibliographyResource.BibliographyResourceEntity;
import com.ruben.sigebi.infrastructure.persistence.entity.bibliographyResource.ResourceCopyEntity;
import com.ruben.sigebi.infrastructure.persistence.entity.loan.LoanEntity;
import com.ruben.sigebi.infrastructure.persistence.entity.user.UserEntity;

import java.util.UUID;

public class LoanMapper {

    private LoanMapper() {}

    public static LoanEntity toEntity(Loan loan, UserEntity user, ResourceCopyEntity resourceCopy) {
        LoanEntity entity = new LoanEntity();
        entity.setId(loan.getLoanID().loanID());
        entity.setStatus(loan.getStatus());
        entity.setUser(user);
        entity.setResourceCopy(resourceCopy);
        entity.setStartDate(loan.getStartDate());
        entity.setDueDate(loan.getDueDate());
        entity.setPendingState(loan.getPendingState());
        return entity;
    }

    public static Loan toDomain(LoanEntity entity) {
        Loan loan = new Loan(
                new LoanId(entity.getId()),
                new UserId(entity.getUser().getId()),
                new ResourceCopyId(entity.getResourceCopy().getId()),
                entity.getStartDate(),
                entity.getDueDate(),
                entity.getPendingState()
        );
        loan.setStatus(entity.getStatus());
        return loan;
    }
}