package com.ruben.sigebi.application.usecases.user;

import com.ruben.sigebi.application.commands.user.AssignRoleCommand;
import com.ruben.sigebi.api.dto.response.user.AssignRoleResponse;
import com.ruben.sigebi.application.interfaces.UseCase;
import com.ruben.sigebi.api.mappers.UserMapper;
import com.ruben.sigebi.domain.service.UserAuthorizationService;
import com.ruben.sigebi.domain.User.entity.User;
import com.ruben.sigebi.domain.roles.repository.RoleRepository;
import com.ruben.sigebi.domain.User.repository.UserRepository;
import com.ruben.sigebi.domain.common.exception.InvalidationException;


import java.util.Optional;


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
        Optional<User> actor = userRepository.findById(assignRoleCommand.actor().value());
        Optional<User> target = userRepository.findById(assignRoleCommand.target().value());

        if (actor.isEmpty()) {
            throw new RuntimeException("User actor not found.");
        }

        if (target.isEmpty()) {
            throw new InvalidationException("User target not found.");
        }

        userAuthorizationService.checkUserCanAssignRole(actor.get().getUserId(), target.get().getUserId());
        for (var a: assignRoleCommand.role()){
            roleRepository.save(a);
        }
        return UserMapper.RoleToResponse(actor.get(), assignRoleCommand.role());
    }
}
