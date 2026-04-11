package com.ruben.sigebi.application.usecases.penalty.manualy;

import com.ruben.sigebi.api.dto.request.penalty.RemovePenaltyRequest;
import com.ruben.sigebi.api.dto.response.penalty.PenaltyResponse;
import com.ruben.sigebi.application.service.PenaltyService;
import com.ruben.sigebi.domain.User.repository.UserRepository;
import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.PenaltyId;
import com.ruben.sigebi.domain.common.exception.DomainException;
import com.ruben.sigebi.domain.common.exception.ElementNotFoundInTheDatabaseException;
import com.ruben.sigebi.domain.penalty.entity.Penalty;
import com.ruben.sigebi.domain.penalty.repository.PenaltyRepository;
import org.springframework.stereotype.Service;

@Service
public class RemovePenaltyManualUseCase {

    private final PenaltyService penaltyService;
    private final PenaltyRepository penaltyRepository;
    private final UserRepository userRepository;

    public RemovePenaltyManualUseCase(PenaltyService penaltyService,
                                      PenaltyRepository penaltyRepository,
                                      UserRepository userRepository) {
        this.penaltyService = penaltyService;
        this.penaltyRepository = penaltyRepository;
        this.userRepository = userRepository;
    }

    public PenaltyResponse execute(RemovePenaltyRequest request) {
        try {
            var penaltyId = new PenaltyId(request.penaltyId());
            var adminId = new UserId(request.adminId());


            Penalty penalty = penaltyRepository.findById(penaltyId)
                    .orElseThrow(() -> new ElementNotFoundInTheDatabaseException(
                            "Penalty not found: " + penaltyId));


            penaltyService.removePenaltyAdmin(penaltyId, adminId);


            penaltyRepository.save(penalty);


            userRepository.findById(penalty.getUserId()).ifPresent(userRepository::save);

            return PenaltyResponse.success(penalty.getUserId().value(), "Penalty removed successfully.");

        } catch (DomainException e) {
            return PenaltyResponse.failure(e.getMessage());
        }
    }
}
