package com.ruben.sigebi.application.usecases.penalty.schedule;
import com.ruben.sigebi.application.interfaces.Scheduler;
import com.ruben.sigebi.domain.User.entity.User;
import com.ruben.sigebi.domain.User.repository.UserRepository;
import com.ruben.sigebi.domain.common.exception.DomainException;
import com.ruben.sigebi.domain.common.exception.ElementNotFoundInTheDatabaseException;
import com.ruben.sigebi.domain.penalty.entity.Penalty;
import com.ruben.sigebi.domain.penalty.repository.PenaltyRepository;
import com.ruben.sigebi.application.service.PenaltyService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;


@Service
public class RemovePenaltyUseCase implements Scheduler{
    private final PenaltyService penaltyService;
    private final UserRepository userRepository;
    private final PenaltyRepository penaltyRepository;

    public RemovePenaltyUseCase(PenaltyService penaltyService, UserRepository userRepository, PenaltyRepository penaltyRepository) {
        this.penaltyService = penaltyService;
        this.userRepository = userRepository;
        this.penaltyRepository = penaltyRepository;
    }

    @Override
//    @Scheduled(fixedRate = 1, timeUnit = TimeUnit.DAYS)
    public void execute() {
        List<Penalty> penalties = penaltyRepository.findAllActiveAndDueDatePenalty();

        if (!(penalties.isEmpty())){

            for (var penalty : penalties) {

                try{
                    penaltyService.removePenalty(penalty);
                }catch (NullPointerException|DomainException e){
                    System.err.println("Penalty cannot be removed: "+e);
                }
                Optional<User> user = userRepository.findById(penalty.getUserId());

                if (user.isEmpty()){
                    throw new ElementNotFoundInTheDatabaseException("User of penalty: "+penalty.getUserId()+" not found");
                }

                penaltyRepository.save(penalty);
                userRepository.save(user.get());
            }

        }


    }


}
