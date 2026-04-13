package com.ruben.sigebi.application.usecases.penalty.schedule.view;

import com.ruben.sigebi.api.dto.request.penalty.GetUserPenaltyRequest;
import com.ruben.sigebi.api.dto.response.resource.GetUserPenaltiesResponse;
import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.common.exception.ElementNotFoundInTheDatabaseException;
import com.ruben.sigebi.domain.penalty.entity.Penalty;
import com.ruben.sigebi.domain.penalty.repository.PenaltyRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class GetUserPenaltiesUseCase {

    private final PenaltyRepository penaltyRepository;

    public GetUserPenaltiesUseCase(PenaltyRepository penaltyRepository) {
        this.penaltyRepository = penaltyRepository;
    }

    public List<GetUserPenaltiesResponse> execute(UUID userId) {

        List<Penalty> penalties = penaltyRepository
                .findPenaltyByUserId(new UserId(userId))
                .orElse(Collections.emptyList());


        if (penalties.isEmpty()) {
            throw new ElementNotFoundInTheDatabaseException("No se encontraron penalizaciones para el usuario: " + userId);
        }

        return penalties.stream()
                .map(this::toResponse)
                .toList();
    }

    private GetUserPenaltiesResponse toResponse(Penalty penalty) {
        return new GetUserPenaltiesResponse(
                penalty.getPenaltyId().value(),
                penalty.getUserId().value(),
                penalty.getLoanId().loanID(),
                penalty.getDescription(),
                penalty.getStartDate(),
                penalty.getEndDate(),
                penalty.getStatus()
        );
    }
}