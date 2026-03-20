package com.ruben.sigebi.application.usecases.user;

import com.ruben.sigebi.application.commands.user.AssignRoleCommand;
import com.ruben.sigebi.api.dto.response.user.AssignRoleResponse;
import com.ruben.sigebi.application.interfaces.UseCase;
import com.ruben.sigebi.api.mappers.UserMapper;
import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.roles.entity.Role;
import com.ruben.sigebi.application.service.UserAuthorizationService;
import com.ruben.sigebi.domain.User.entity.User;
import com.ruben.sigebi.domain.roles.repository.RoleRepository;
import com.ruben.sigebi.domain.User.repository.UserRepository;
import com.ruben.sigebi.domain.common.exception.InvalidationException;
import org.springframework.stereotype.Service;


import java.util.*;

@Service
public class AssignRoleToUserUseCase implements UseCase<AssignRoleResponse, AssignRoleCommand> {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserAuthorizationService userAuthorizationService;

    public AssignRoleToUserUseCase(UserRepository userRepository, RoleRepository roleRepository, RoleRepository roleRepository1) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository1;
        userAuthorizationService = new UserAuthorizationService(userRepository, roleRepository);
    }

    @Override
    public AssignRoleResponse execute(AssignRoleCommand assignRoleCommand){
        Optional<User> actor = userRepository.findById(new UserId(assignRoleCommand.actor().value()));
        Optional<User> target = userRepository.findById(new UserId(assignRoleCommand.target().value()));

        if (actor.isEmpty()) {
            throw new RuntimeException("User actor not found.");
        }

        if (target.isEmpty()) {
            throw new InvalidationException("User target not found.");
        }

        userAuthorizationService.checkUserCanAssignRole(actor.get().getUserId(), target.get().getUserId());
        Set<Role> listOfRoles = new HashSet<>();
        for (var a: assignRoleCommand.role()){
            var x = roleRepository.findById(a);
            roleRepository.save(x.get());
            listOfRoles.add(x.get());
        }
        return UserMapper.RoleToResponse(actor.get(), listOfRoles);
    }
}
