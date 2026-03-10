package com.ruben.sigebi.domain.service;

import com.ruben.sigebi.application.exceptions.ForbiddenException;
import com.ruben.sigebi.domain.User.entity.Role;
import com.ruben.sigebi.domain.User.entity.User;
import com.ruben.sigebi.domain.User.repository.RoleRepository;
import com.ruben.sigebi.domain.User.repository.UserRepository;
import com.ruben.sigebi.domain.User.valueObject.Permission;
import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.bibliographyResource.entity.PhysicalResource;
import com.ruben.sigebi.domain.bibliographyResource.repository.BibliographyRepository;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceID;
import com.ruben.sigebi.domain.common.exception.DomainException;
import com.ruben.sigebi.domain.penalty.repository.PenaltyRepository;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.Optional;

public class LoanService {

    private final static int daysOfLan = 7;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BibliographyRepository bibliographyRepository;
    private final PenaltyRepository penaltyRepository;

    public LoanService(UserRepository userRepository, RoleRepository roleRepository, BibliographyRepository bibliographyRepository, PenaltyRepository penaltyRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bibliographyRepository = bibliographyRepository;
        this.penaltyRepository = penaltyRepository;
    }

    public void checkUserCanBorrow(UserId userId){
        checkUserBorrowPermission(userId);
        checkUserPenalty(userId);
    }

    public Instant LoanResource(ResourceID resourceID, UserId userId){

        checkUserBorrowPermission(userId);
        checkUserPenalty(userId);
        checkResourceState(resourceID);
        Optional<PhysicalResource> resource = bibliographyRepository.findById(resourceID);

        if (resource.isEmpty()){
            throw new RuntimeException();
        }

        resource.get().markAsLoaned(userId);
        bibliographyRepository.save(resource.get());
        return Instant.now().plus(Duration.ofDays(daysOfLan));

    }

    private void checkResourceState(ResourceID resourceID) {
        Optional<PhysicalResource> resource = bibliographyRepository.findById(resourceID);
        if (resource.isEmpty()) {
            throw new RuntimeException();
        }
        if (!resource.get().canBeLoaned()){
            throw new DomainException("Resource can't be loaned.");
        }
    }

    private void checkUserPenalty(UserId userId) {
        var penalty = penaltyRepository.findUnpaidByUserId(userId);
        if (penalty.isEmpty()) {
            return;
        }
        for (var a : penalty){
            Objects.requireNonNull(a);
            if (a.isActive()){
                throw new DomainException("User cannot loan while have an active penalty.");
            }
        }
    }

    private void checkUserBorrowPermission(UserId userId){
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()){
            throw new RuntimeException();
        }

        for (var a : user.get().getRoles()){
            Optional<Role> roles = roleRepository.findById(a);
            if (roles.isEmpty()){
                throw new RuntimeException();
            }
            if ((roles.get().hasPermission(new Permission("RESOURCE","LOAN")))){
                break;
            }
        }
        throw new ForbiddenException("User does not have permission to loan.");
    }

}
