package com.ruben.sigebi.infrastructure.persistence.mapper;

import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.PenaltyId;
import com.ruben.sigebi.domain.loan.valueObjects.LoanId;
import com.ruben.sigebi.domain.penalty.entity.Penalty;
import com.ruben.sigebi.infrastructure.persistence.entity.loan.LoanEntity;
import com.ruben.sigebi.infrastructure.persistence.entity.penalty.PenaltyEntity;
import com.ruben.sigebi.infrastructure.persistence.entity.user.UserEntity;

import java.util.UUID;

public class PenaltyMapper {

    private PenaltyMapper(){}

    public static PenaltyEntity toEntity(
            Penalty penalty,
            UserEntity user,
            LoanEntity loan
    ){

        PenaltyEntity entity = new PenaltyEntity();

        entity.setId(penalty.getPenaltyId().value().toString());

        entity.setUser(user);

        entity.setLoan(loan);

        entity.setStartDate(penalty.getStartDate());

        entity.setEndDate(penalty.getEndDate());

        entity.setDescription(penalty.getDescription());

        return entity;
    }


    public static Penalty toDomain(PenaltyEntity entity){
        var _userid =  entity.getUser().getId();
        var _loanId =  entity.getLoan().getId();
        var _penaltyId =  entity.getId();

        return new Penalty(
                new PenaltyId(UUID.fromString(_penaltyId)),
                new UserId(UUID.fromString(_userid)),
                new LoanId(UUID.fromString(_loanId)),
                entity.getEndDate()
        );
    }
}