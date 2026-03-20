package com.ruben.sigebi.application.usecases.user;
import com.ruben.sigebi.application.commands.user.AssignPermissionCommand;
import com.ruben.sigebi.api.dto.response.user.AssignPermissionResponse;
import com.ruben.sigebi.application.interfaces.UseCase;
import com.ruben.sigebi.api.mappers.UserMapper;
import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.roles.valueObjects.RoleID;
import com.ruben.sigebi.application.service.UserAuthorizationService;
import com.ruben.sigebi.domain.roles.entity.Role;
import com.ruben.sigebi.domain.User.entity.User;
import com.ruben.sigebi.domain.roles.repository.RoleRepository;
import com.ruben.sigebi.domain.User.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AssignPermissionToUserUseCase implements UseCase<AssignPermissionResponse, AssignPermissionCommand> {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserAuthorizationService userAuthorizationService;


    public AssignPermissionToUserUseCase(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        userAuthorizationService = new UserAuthorizationService(userRepository,roleRepository);
    }

    @Override
    public AssignPermissionResponse execute(AssignPermissionCommand command){

        Optional<User> target = userRepository.findById(new UserId(command.target().value()));
        Optional<User> actor = userRepository.findById(new UserId(command.actor().value()));
        Optional<Role> role = roleRepository.findById(new RoleID(command.roleID().value()));

        if (target.isEmpty()){
            throw new RuntimeException("User target is not found");
        }
        if(role.isEmpty()){
            throw new RuntimeException("role to assign permission is not found");
        }
        if (actor.isEmpty()){
            throw new RuntimeException("User actor is not found");
        }

        userAuthorizationService.checkUserCanAssignPermission(command.actor(),command.target());
        role.get().addPermissions(command.permissions());
        roleRepository.save(role.get());
        return UserMapper.PermissionToResponse(command);
    }


}
