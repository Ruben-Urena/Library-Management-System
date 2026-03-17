package com.ruben.sigebi.application.usecases.penalty.schedule;
import com.ruben.sigebi.application.interfaces.Scheduler;
import com.ruben.sigebi.domain.User.entity.User;
import com.ruben.sigebi.domain.User.repository.UserRepository;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.PenaltyId;
import com.ruben.sigebi.domain.common.enums.Status;
import com.ruben.sigebi.domain.common.exception.DomainException;
import com.ruben.sigebi.domain.common.exception.ElementNotFoundInTheDatabaseException;
import com.ruben.sigebi.domain.loan.enums.PendingState;
import com.ruben.sigebi.domain.loan.repository.LoanRepository;
import com.ruben.sigebi.domain.penalty.entity.Penalty;
import com.ruben.sigebi.domain.penalty.repository.PenaltyRepository;
import com.ruben.sigebi.domain.service.PenaltyService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


@Service
public class ApplyPenaltyUseCase implements Scheduler{
    private final LoanRepository loanRepository;
    private final PenaltyService penaltyService;
    private final UserRepository userRepository;
    private final PenaltyRepository penaltyRepository;

    public ApplyPenaltyUseCase(LoanRepository loanRepository, PenaltyService penaltyService, UserRepository userRepository, PenaltyRepository penaltyRepository) {
        this.loanRepository = loanRepository;
        this.penaltyService = penaltyService;
        this.userRepository = userRepository;
        this.penaltyRepository = penaltyRepository;
    }

    @Override
    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.DAYS)
    public void execute() {

        var overdueActiveLoans = loanRepository.findLoansByStateAndStatus(PendingState.OVERDUE, Status.ACTIVE);//pending,Status

        if (!overdueActiveLoans.isEmpty()) {
            for (var loan : overdueActiveLoans) {
                Instant instant = null;
                try{
                    instant = penaltyService.applyPenalty(loan);

                }catch (NullPointerException |DomainException a){
                    System.err.println("Failure to apply penalty: "+ a);
                }
                Optional<User> user = userRepository.findById(loan.getUserId());
                if(user.isEmpty()){
                    throw new ElementNotFoundInTheDatabaseException("User to apply penalty not found: ");
                }
                user.ifPresent(userRepository::save);

                penaltyRepository.save(new Penalty(
                        new PenaltyId(UUID.randomUUID()),
                        user.get().getUserId(),
                        loan.getLoanID(),
                        instant
                ));

            }
        }

    }
}
