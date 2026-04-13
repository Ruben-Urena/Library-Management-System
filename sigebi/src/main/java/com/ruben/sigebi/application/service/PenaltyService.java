package com.ruben.sigebi.application.service;


import com.ruben.sigebi.domain.User.entity.User;
import com.ruben.sigebi.domain.User.repository.UserRepository;
import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.PenaltyId;
import com.ruben.sigebi.domain.common.enums.Status;
import com.ruben.sigebi.domain.common.exception.BusinessRuleViolationException;
import com.ruben.sigebi.domain.common.exception.ElementNotFoundInTheDatabaseException;
import com.ruben.sigebi.domain.loan.entity.Loan;
import com.ruben.sigebi.domain.loan.enums.PendingState;
import com.ruben.sigebi.domain.loan.repository.LoanRepository;
import com.ruben.sigebi.domain.loan.valueObjects.LoanId;
import com.ruben.sigebi.domain.penalty.entity.Penalty;
import com.ruben.sigebi.domain.penalty.repository.PenaltyRepository;
import com.ruben.sigebi.domain.roles.entity.Role;
import com.ruben.sigebi.domain.roles.repository.RoleRepository;
import com.ruben.sigebi.domain.roles.valueObjects.Permission;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
public class PenaltyService{
    //PENALTY IS CREATED IF:   USER HAS A LOAN -> THIS LOAN END DATE IS BEFORE TODAY;
    //PENALTY IS REMOVED IF:   (ADMIN WANT XD) USER HAS A PENALTY -> THAT PENALTY END DATE IS BEFORE TODAY;
    private final LoanRepository loanRepository;
    private  static final int daysOfPenalty = 7;
    private final UserRepository userRepository;
    private final PenaltyRepository penaltyRepository;
    private final RoleRepository roleRepository;

    public PenaltyService(LoanRepository loanRepository, UserRepository userRepository, PenaltyRepository penaltyRepository, RoleRepository roleRepository) {
        this.loanRepository = Objects.requireNonNull(loanRepository);
        this.userRepository = Objects.requireNonNull(userRepository);
        this.penaltyRepository = Objects.requireNonNull(penaltyRepository);
        this.roleRepository = roleRepository;
    }


    //penalty - resource
    public Instant applyPenalty(Loan loan){

        var userId = loan.getUserId();
        if (!(isOverDue(loan)) ){
            throw new BusinessRuleViolationException("Cannot penalize if user is not overdue.");
        }

        var penalty = penaltyRepository.findActivePenaltyByLoan(loan.getLoanID(), userId);
        if (penalty.isPresent()){
            throw new BusinessRuleViolationException("Cannot penalize if user is already penalized with:  "+loan.getUserId());
        }

        var user = userRepository.findById(userId);
        if (user.isEmpty()){
            throw new ElementNotFoundInTheDatabaseException("User not found: " + userId);
        }
        user.get().markAsPenalize();
        return Instant.now().plus(Duration.ofDays(daysOfPenalty));
    };
    public void applyPenaltyAdmin(UserId borrowerId, UserId adminId){
        Optional<User> borrower = userRepository.findById(borrowerId);

        if (borrower.isEmpty()){
            throw new ElementNotFoundInTheDatabaseException("Borrower not found: " + borrowerId);
        }

        Optional<User> admin = userRepository.findById(adminId);
        if (admin.isEmpty()){
            throw new ElementNotFoundInTheDatabaseException("Admin not found: " + adminId);
        }

        Set<Role> roles = roleRepository.findByUser(adminId);

        if (roles.isEmpty()){
            throw new ElementNotFoundInTheDatabaseException("User does not have roles: " + adminId);
        }
        for( var role : roles){
            boolean hasPermission = role.getPermissions().stream()
                    .anyMatch(p ->
                            p.source().equals("USER") &&
                                    p.action().equals("APPLY_PENALTY")
                    );

            if (hasPermission){
                borrower.get().markAsPenalize();
                return;
            }
        }
        throw new BusinessRuleViolationException("User does not have permission to apply penalty: "+ adminId);
    }
    public void removePenalty(Penalty penalty){
        var penaltyId =  penalty.getPenaltyId();
        //Penalty has been removed?
        if ( !(penalty.isActive()) ){
            throw new BusinessRuleViolationException("Penalty is already removed: "+penaltyId);
        }

        //Penalty due date
        if(! (penalty.isExpired()) ){
            throw new BusinessRuleViolationException("Cannot remove the penalty before its due date : "+penaltyId);
        }

        //Marking penalty as inactive.
        penalty.endPenalty();

        var userId = penalty.getUserId();
        Optional<User> user =  userRepository.findById(userId);
        if (user.isEmpty()){
            throw new ElementNotFoundInTheDatabaseException("User not found: " + userId);
        }

        //Marking user as eligible, so he or she can loan again
        user.get().markAsEligible();
    };
    public void removePenaltyAdmin(PenaltyId penaltyId, UserId adminId){
        Optional<Penalty> penaltyOptional = penaltyRepository.findById(penaltyId);
        Optional<User> admin = userRepository.findById(adminId);

        if(admin.isEmpty()){
            throw new ElementNotFoundInTheDatabaseException("Admin not found: " + adminId);
        }

        Set<Role> roles = roleRepository.findByUser(adminId);
        if (roles.isEmpty()){
            throw new ElementNotFoundInTheDatabaseException("User does not have roles: " + adminId);
        }

        //penalty not found in the DB
        if (penaltyOptional.isEmpty()){
            throw new ElementNotFoundInTheDatabaseException("Penalty not found: " + penaltyId);
        }

        //Penalty has been removed?
        if ( !(penaltyOptional.get().isActive()) ){
            throw new BusinessRuleViolationException("Penalty is already removed: "+penaltyId);
        }

        for( var role : roles){
            boolean hasPermission = role.getPermissions().stream()
                    .anyMatch(p ->
                            p.source().equals("USER") &&
                                    p.action().equals("REMOVE_PENALTY")
                    );
            System.out.println(role.getPermissions());
            if (hasPermission){
                //Marking penalty as inactive.
                penaltyOptional.get().endPenalty();

                var userId = penaltyOptional.get().getUserId();
                Optional<User> user =  userRepository.findById(userId);
                if (user.isEmpty()){
                    throw new ElementNotFoundInTheDatabaseException("User not found: " + userId);
                }

                //Marking user as eligible, so he or she can loan again
                user.get().markAsEligible();
                return;
            }
        }
        throw new BusinessRuleViolationException("User does not have permission to remove a penalty: "+adminId);
    };
    public boolean isOverDue(LoanId loanId){
        Optional<Loan> loan = loanRepository.findById(loanId);
        if (loan.isEmpty()){
            throw new ElementNotFoundInTheDatabaseException("Loan not found: " + loanId);
        }
        return loan.get().isOverdue();
    }
    public boolean isOverDue(Loan loan){
        Objects.requireNonNull(loan);
        return loan.isOverdue();
    }
    public boolean UserHasALoan(UserId userId){
        Set<Loan> loan = loanRepository.findByStatusAndUserId(Status.ACTIVE,userId);//active loans
        return !(loan.isEmpty());
    }

}
