package com.ruben.sigebi.application.service;

import com.ruben.sigebi.domain.bibliographyResource.entity.ResourceCopy;
import com.ruben.sigebi.domain.bibliographyResource.repository.ResourceCopyRepository;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceCopyId;
import com.ruben.sigebi.domain.common.enums.Status;
import com.ruben.sigebi.domain.common.exception.BusinessRuleViolationException;
import com.ruben.sigebi.domain.common.exception.ElementNotFoundInTheDatabaseException;
import com.ruben.sigebi.domain.common.exception.InvalidStateException;
import com.ruben.sigebi.domain.loan.entity.Loan;
import com.ruben.sigebi.domain.loan.repository.LoanRepository;
import com.ruben.sigebi.domain.loan.valueObjects.LoanId;
import com.ruben.sigebi.domain.roles.entity.Role;
import com.ruben.sigebi.domain.User.entity.User;
import com.ruben.sigebi.domain.roles.repository.RoleRepository;
import com.ruben.sigebi.domain.User.repository.UserRepository;
import com.ruben.sigebi.domain.roles.valueObjects.Permission;
import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceID;
import com.ruben.sigebi.domain.penalty.repository.PenaltyRepository;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class LoanService {

    private static  int DAYS_OF_LOAN = 7;
    private static int LIMIT_OF_LOAN_PER_USER = 4;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PenaltyRepository penaltyRepository;
    private final LoanRepository loanRepository;
    private final ResourceCopyRepository resourceCopyRepository;


    public LoanService(UserRepository userRepository, RoleRepository roleRepository,
                       PenaltyRepository penaltyRepository, LoanRepository loanRepository,
                       ResourceCopyRepository resourceCopyRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.penaltyRepository = penaltyRepository;
        this.loanRepository = loanRepository;
        this.resourceCopyRepository = resourceCopyRepository;
    }


    public LoanResult loanResource(ResourceID resourceID, UserId userId) {
        Objects.requireNonNull(resourceID);
        Objects.requireNonNull(userId);



        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ElementNotFoundInTheDatabaseException(
                        "User not found: " + userId));

        if (!user.isActive()) {
            throw new InvalidStateException("User is not active: " + userId);
        }
        if (!userHasPermissionToLoan(user)) {
            throw new BusinessRuleViolationException(
                    "User does not have permission to loan: " + userId);
        }
        if (userHasPenalty(userId)) {
            throw new BusinessRuleViolationException("User has a penalty: " + userId);
        }


        if (loanRepository.findByStatusAndUserId(Status.ACTIVE, userId).size() >= LIMIT_OF_LOAN_PER_USER){
            throw new BusinessRuleViolationException("User: "+ userId+" cannot loan more than : " + LIMIT_OF_LOAN_PER_USER+" resources");
        }
        if (alreadyHasResource(userId, resourceID)){
            throw new BusinessRuleViolationException("User: "+ userId+" cannot loan the same resource: " +resourceID+" resources ");
        }

        ResourceCopy copy = resourceCopyRepository.findFirstAvailable(resourceID)
                .orElseThrow(() -> new BusinessRuleViolationException(
                        "No available copies for resource: " + resourceID));

        System.out.println("COPY REFERENCE TO:"+copy.getPhysicalResourceId().value());
        copy.markAsLoaned();
        resourceCopyRepository.save(copy);////error here


        Instant dueDate = Instant.now().plus(DAYS_OF_LOAN, ChronoUnit.DAYS);
        return new LoanResult(copy.getId(), dueDate);
    }


    public Loan endLoan(LoanId loanId, Instant returnedAt) {
        Loan loan = loanRepository.findById(loanId)
                .orElseThrow(() -> new ElementNotFoundInTheDatabaseException(
                        "Loan not found: " + loanId));


        ResourceCopy copy = resourceCopyRepository.findById(loan.getCopyId())
                .orElseThrow(() -> new ElementNotFoundInTheDatabaseException(
                        "ResourceCopy not found: " + loan.getCopyId()));

        copy.returnLoan();
        resourceCopyRepository.save(copy);

        loan.returnLoan(returnedAt);
        return loan;
    }

    // ─── Helpers ─────────────────────────────────────────────────────────────

    public boolean userHasPenalty(UserId userId) {
        Objects.requireNonNull(userId);
        var penalty = penaltyRepository.findPenaltyByUserId(userId);
        if (penalty.isPresent()) {
            for (var p : penalty.get()) {
                if (p.isActive()) return true;
            }
        }
        return false;
    }

    public boolean alreadyHasResource(UserId userId, ResourceID resourceId) {
        return loanRepository.findByStatusAndUserId(Status.ACTIVE, userId)
                .stream()
                .map(Loan::getCopyId)
                .map(resourceCopyRepository::findById)
                .flatMap(Optional::stream)
                .anyMatch(rc -> rc.getPhysicalResourceId().equals(resourceId));
    }

    public boolean userHasPermissionToLoan(User user) {
        for (var roleId : user.getRoles()) {
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new ElementNotFoundInTheDatabaseException("Role not found."));
            if (role.hasPermission(new Permission("RESOURCE", "LOAN"))) return true;
        }
        return false;
    }
    public boolean userHasPermissionToChangeDaysLoan(User user) {
        for (var roleId : user.getRoles()) {
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new ElementNotFoundInTheDatabaseException("Role not found."));
            if (role.hasPermission(new Permission("LOAN_DAYS", "CHANGE"))) return true;
        }
        return false;
    }
    public boolean userHasPermissionToChangeLimitOfLoanPerUser(User user) {
        for (var roleId : user.getRoles()) {
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new ElementNotFoundInTheDatabaseException("Role not found."));
            if (role.hasPermission(new Permission("LOAN", "MAX"))) return true;
        }
        return false;
    }

    public void setDaysOfLoan (int daysOfLoan, User user) {
        if(userHasPermissionToChangeDaysLoan(user)){
            DAYS_OF_LOAN = daysOfLoan;
        }else {
            throw new BusinessRuleViolationException("User does not have permission to set days of loan: " + user.getUserId());
        }
    }

    public void setLimitOfLoanPerUser(int limitOfLoanPerUser, User user) {
        if (userHasPermissionToChangeLimitOfLoanPerUser(user)){
            LIMIT_OF_LOAN_PER_USER = limitOfLoanPerUser;
        }else {
            throw new BusinessRuleViolationException("User does not have permission to set max of loan per user: " + user.getUserId());
        }
    }

    public static int getDaysOfLoan() {
        return DAYS_OF_LOAN;
    }

    public static int getLimitOfLoanPerUser() {
        return LIMIT_OF_LOAN_PER_USER;
    }

    public record LoanResult(ResourceCopyId copyId, Instant dueDate) {}

}
