package com.ruben.sigebi.infrastructure.persistence.mapper;

import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceID;
import com.ruben.sigebi.domain.loan.entity.Loan;
import com.ruben.sigebi.domain.loan.valueObjects.LoanId;
import com.ruben.sigebi.infrastructure.persistence.entity.bibliographyResource.BibliographyResourceEntity;
import com.ruben.sigebi.infrastructure.persistence.entity.loan.LoanEntity;
import com.ruben.sigebi.infrastructure.persistence.entity.user.UserEntity;

import java.util.UUID;

public class LoanMapper {

    private LoanMapper(){}

    public static LoanEntity toEntity(
            Loan loan,
            UserEntity user,
            BibliographyResourceEntity resource
    ){

        LoanEntity entity = new LoanEntity( );

        entity.setId(loan.getLoanID().loanID().toString());

        entity.setUser(user);

        entity.setResource(resource);

        entity.setStartDate(loan.getStartDate());

        entity.setDueDate(loan.getDueDate());

        entity.setPendingState(loan.getPendingState());

        return entity;
    }


    public static Loan toDomain(LoanEntity entity) {
        return new Loan(
                new LoanId(UUID.fromString(entity.getId())),
                new UserId(UUID.fromString(entity.getId())),
                new ResourceID(UUID.fromString(entity.getId())),
                entity.getStartDate(),
                entity.getDueDate()
        );
    }
}