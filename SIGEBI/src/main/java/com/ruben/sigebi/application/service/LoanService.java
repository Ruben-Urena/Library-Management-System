package com.ruben.sigebi.application.service;

import com.ruben.sigebi.domain.bibliographyResource.entity.BibliographyResource;
import com.ruben.sigebi.domain.bibliographyResource.entity.PhysicalResource;
import com.ruben.sigebi.domain.bibliographyResource.interfaces.Loanable;
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
import com.ruben.sigebi.domain.bibliographyResource.repository.BibliographyRepository;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceID;
import com.ruben.sigebi.domain.penalty.repository.PenaltyRepository;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.util.Objects;
import java.util.Optional;


@Service
public class LoanService {

    private final static int daysOfLan = 7;
    //here, I need a service called; calculate Days Of Loan;( I need to apply latter).
    //also a policy of MAX LOAN PER USER or ROLE; ( I need to apply latter.)
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BibliographyRepository bibliographyRepository;
    private final PenaltyRepository penaltyRepository;
    private final LoanRepository loanRepository;

    public LoanService(UserRepository userRepository, RoleRepository roleRepository, BibliographyRepository bibliographyRepository, PenaltyRepository penaltyRepository, LoanRepository loanRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bibliographyRepository = bibliographyRepository;
        this.penaltyRepository = penaltyRepository;
        this.loanRepository = loanRepository;
    }

    //CREATE LOAN;
    public Instant LoanResource(ResourceID resourceID, UserId userId){
        Optional<BibliographyResource> resource = bibliographyRepository.findById(Objects.requireNonNull(resourceID));
        Optional<User> user = userRepository.findById(Objects.requireNonNull(userId));
        if (resource.isEmpty()) {
            throw new ElementNotFoundInTheDatabaseException("Resource not found: "+resourceID);
        }
        if (resource.get() instanceof PhysicalResource P){
            if (P.getQuantity() == 0){


            }
        }
        if (user.isEmpty()) {
            throw new ElementNotFoundInTheDatabaseException("User not found: "+ userId);
        }
        if (!user.get().isActive()){
            throw new InvalidStateException("User is not active: "+userId);
        }
        if ( !(userHasPermissionToLoan(user.get())) ){
            throw new BusinessRuleViolationException("User does not have has permission to loan: "+userId);
        }
        if (userHasPenalty(userId)){
            throw new BusinessRuleViolationException("User has Penalty: " +userId);
        }
        if( !(resource.get().isActive()) ){
            throw new InvalidStateException("Resource is not active: "+resourceID);
        }
        var L = isResourceLoanable(resource.get());
        if (L == null){
            throw new BusinessRuleViolationException("Resource is not loanable: "+ resourceID);
        }

        if (L.isLoaned()){
            throw new BusinessRuleViolationException("The resource is either loaned or temporarily deactivated: " +resourceID);
        }

        L.markAsLoaned(userId);
        return Instant.now();
    }
    private Loanable isResourceLoanable(BibliographyResource resource){
        if (!(resource instanceof Loanable L)){
            return null;
        }
        return L;
    }
    public Loanable isResourceLoanable(ResourceID resourceId){

        Objects.requireNonNull(resourceId);
        Optional<BibliographyResource> resource = bibliographyRepository.findById(resourceId);
        if (resource.isPresent()){

            if(resource.get() instanceof Loanable L){
                return L;
            }

        }
        return null;
    }
    private boolean loanableIsAvailable(Loanable L){
        Objects.requireNonNull(L);
        return !L.isLoaned();
    }
    public boolean userHasPenalty(UserId userId) {
        Objects.requireNonNull(userId);
        var penalty = penaltyRepository.findPenaltyByUserId(userId);
        if (!penalty.isEmpty()){
            for (var a : penalty.get()){
                Objects.requireNonNull(a);
                if (a.isActive()){
                   return true;
                }
            }
        }
        return false;
    }
    public boolean userHasPermissionToLoan(UserId userid){
        Objects.requireNonNull(userid);
        Optional<User> userOptional = userRepository.findById(userid);
        if (userOptional.isEmpty()){
            throw new ElementNotFoundInTheDatabaseException("User not found.");
        }
        for (var a : userOptional.get().getRoles()){
            Optional<Role> roles = roleRepository.findById(a);
            if (roles.isEmpty()){
                throw new ElementNotFoundInTheDatabaseException("Role not found.");
            }
            if ((roles.get().hasPermission(new Permission("RESOURCE","LOAN")))){
                return true;
            }
        }
        return false;
    }
    public boolean userHasPermissionToLoan(User user){
        for (var a : user.getRoles()){
            Optional<Role> roles = roleRepository.findById(a);
            if (roles.isEmpty()){
                throw new ElementNotFoundInTheDatabaseException("Role not found.");
            }
            if ((roles.get().hasPermission(new Permission("RESOURCE","LOAN")))){
                return true;
            }
        }
        return false;
    }

    //END LOAN;
    public Loan endLoan(LoanId loanId, Instant instant){
        Optional<Loan> loanOptional = loanRepository.findById(loanId);
        if (loanOptional.isEmpty()){
            throw new ElementNotFoundInTheDatabaseException("Loan not found: "+loanId);
        }
        loanOptional.get().returnLoan(instant); // ← muta el aggregate
        return loanOptional.get();              // ← retorna el loan mutado
    }


}
