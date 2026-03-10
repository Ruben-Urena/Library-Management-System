package com.ruben.sigebi.domain.service;

import com.ruben.sigebi.application.exceptions.ForbiddenException;
import com.ruben.sigebi.domain.User.entity.User;
import com.ruben.sigebi.domain.User.repository.RoleRepository;
import com.ruben.sigebi.domain.User.repository.UserRepository;
import com.ruben.sigebi.domain.User.valueObject.Permission;
import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.common.exception.DomainException;

import java.util.Optional;

public class UserAuthorizationService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserAuthorizationService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public void checkUserCanAssignPermission(UserId actorId, UserId targetId) {

        Optional<User> actor = this.userRepository.findById(actorId);
        Optional<User> target = this.userRepository.findById(targetId);

        if (actor.isEmpty()) {
            throw new RuntimeException("User actor is not found");
        }

        if (target.isEmpty()) {
            throw new RuntimeException("User target is not found");
        }

        if (!target.get().isActive()){
            throw new DomainException("Cannot assign permissions to an user that is not active.");
        }

        if (!actor.get().isActive()){
            throw new DomainException("User actor is not active.");
        }

        for (var a : actor.get().getRoles()){

            var b = roleRepository.findById(a);
            if (b.isEmpty()) {
                throw  new RuntimeException("User actor does not have roles.");
            }
            if (b.get().hasPermission(new Permission("USER", "ASSIGN_PERMISSION"))) {
                break;
            }

        }
        throw new ForbiddenException("User does not have permission to assign permission.");

    }

    public  void checkUserCanAssignRole(UserId actorId, UserId targetId) {
        Optional<User> actor = this.userRepository.findById(actorId);
        Optional<User> target = this.userRepository.findById(targetId);

        if (actor.isEmpty()) {
            throw new RuntimeException("User actor is not found");
        }

        if (target.isEmpty()) {
            throw new RuntimeException("User target is not found");
        }

        if (!target.get().isActive()){
            throw new DomainException("Cannot assign permissions to an user that is not active.");
        }

        if (!actor.get().isActive()){
            throw new DomainException("User actor is not active.");
        }

        for (var a : actor.get().getRoles()){

            var b = roleRepository.findById(a);
            if (b.isEmpty()) {
                throw  new RuntimeException("User actor does not have roles.");
            }
            if (b.get().hasPermission(new Permission("USER", "ASSIGN_ROLE"))) {
                break;
            }

        }
        throw new ForbiddenException("User does not have permission to assign role.");
    }

    public void assignRole(UserId actorId, UserId targetId) {
        Optional<User> actor = this.userRepository.findById(actorId);
        Optional<User> target = this.userRepository.findById(targetId);
        if (actor.isEmpty()) {
            throw new RuntimeException("User actor is not found");
        }
        if (target.isEmpty()) {
            throw new RuntimeException("User target is not found");
        }

    }


}
