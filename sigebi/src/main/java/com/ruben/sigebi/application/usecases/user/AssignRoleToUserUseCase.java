package com.ruben.sigebi.application.usecases.user;

import com.ruben.sigebi.application.commands.user.AssignRoleCommand;
import com.ruben.sigebi.api.dto.response.user.AssignRoleResponse;
import com.ruben.sigebi.application.interfaces.UseCase;
import com.ruben.sigebi.api.mappers.UserMapper;
import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.common.exception.ElementNotFoundInTheDatabaseException;
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

    public AssignRoleToUserUseCase(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.userAuthorizationService = new UserAuthorizationService(userRepository, roleRepository);
    }

    @Override
    public AssignRoleResponse execute(AssignRoleCommand command) {
        User actor = userRepository.findById(command.actor())
                .orElseThrow(() -> new ElementNotFoundInTheDatabaseException("Actor not found: " + command.actor()));

        User target = userRepository.findById(command.target())
                .orElseThrow(() -> new ElementNotFoundInTheDatabaseException("Target user not found: " + command.target()));

        userAuthorizationService.checkUserCanAssignRole(actor.getUserId(), target.getUserId());

        Set<Role> assignedRoles = new HashSet<>();
        for (var roleId : command.role()) {
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new ElementNotFoundInTheDatabaseException("Role not found: " + roleId));


            target.assignRole(roleId);
            assignedRoles.add(role);
        }

        userRepository.save(target);

        return UserMapper.RoleToResponse(target, assignedRoles);
    }
}