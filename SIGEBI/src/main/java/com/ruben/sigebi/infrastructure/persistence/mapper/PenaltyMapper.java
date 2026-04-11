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

    private PenaltyMapper() {}

    public static PenaltyEntity toEntity(Penalty penalty, UserEntity user, LoanEntity loan) {
        PenaltyEntity entity = new PenaltyEntity();
        entity.setId(penalty.getPenaltyId().value());
        entity.setStatus(penalty.getStatus());
        entity.setUser(user);
        entity.setLoan(loan);
        entity.setStartDate(penalty.getStartDate());
        entity.setEndDate(penalty.getEndDate());
        entity.setDescription(penalty.getDescription());
        return entity;
    }

    public static Penalty toDomain(PenaltyEntity entity) {
        
        Penalty penalty = new Penalty(
                new PenaltyId(entity.getId()),
                new UserId(entity.getUser().getId()),
                new LoanId(entity.getLoan().getId()),
                entity.getDescription(),
                entity.getStartDate(),
                entity.getEndDate()
        );
        penalty.setStatus(entity.getStatus());
        return penalty;
    }
}