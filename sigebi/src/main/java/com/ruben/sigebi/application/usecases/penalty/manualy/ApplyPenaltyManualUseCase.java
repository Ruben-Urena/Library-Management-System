package com.ruben.sigebi.application.usecases.penalty.manualy;
import com.ruben.sigebi.api.dto.request.penalty.ApplyPenaltyRequest;
import com.ruben.sigebi.api.dto.response.penalty.PenaltyResponse;
import com.ruben.sigebi.application.service.PenaltyService;
import com.ruben.sigebi.domain.User.repository.UserRepository;
import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.PenaltyId;
import com.ruben.sigebi.domain.common.enums.Status;
import com.ruben.sigebi.domain.common.exception.DomainException;
import com.ruben.sigebi.domain.loan.enums.PendingState;
import com.ruben.sigebi.domain.loan.repository.LoanRepository;
import com.ruben.sigebi.domain.penalty.entity.Penalty;
import com.ruben.sigebi.domain.penalty.repository.PenaltyRepository;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class ApplyPenaltyManualUseCase {

    private final PenaltyService penaltyService;
    private final LoanRepository loanRepository;
    private final UserRepository userRepository;
    private final PenaltyRepository penaltyRepository;

    public ApplyPenaltyManualUseCase(PenaltyService penaltyService, LoanRepository loanRepository,
                                     UserRepository userRepository, PenaltyRepository penaltyRepository) {
        this.penaltyService = penaltyService;
        this.loanRepository = loanRepository;
        this.userRepository = userRepository;
        this.penaltyRepository = penaltyRepository;
    }

    public PenaltyResponse execute(ApplyPenaltyRequest request) {
        try {
            var borrowerId = new UserId(request.borrowerId());
            var adminId = new UserId(request.adminId());


            var overdueLoans = loanRepository.findLoansByUserStateAndStatus(
                    borrowerId, PendingState.OVERDUE, Status.ACTIVE);

            if (overdueLoans.isEmpty()) {
                return PenaltyResponse.failure("No overdue loans found for user: " + request.borrowerId());
            }

            var loan = overdueLoans.getFirst();


            penaltyService.applyPenaltyAdmin(borrowerId, adminId); // ← muta user
            Instant endDate = penaltyService.applyPenalty(loan);   // ← solo valida y calcula endDate

            penaltyRepository.save(new Penalty(
                    new PenaltyId(UUID.randomUUID()),
                    borrowerId,
                    loan.getLoanID(),
                    endDate
            ));


            userRepository.findById(borrowerId).ifPresent(userRepository::save);

            return PenaltyResponse.success(request.borrowerId(), "Penalty applied successfully.");

        } catch (DomainException e) {
            return PenaltyResponse.failure(e.getMessage());
        }
    }
}
