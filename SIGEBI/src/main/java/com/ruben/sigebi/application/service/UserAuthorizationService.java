package com.ruben.sigebi.application.service;

import com.ruben.sigebi.application.exceptions.ForbiddenException;
import com.ruben.sigebi.domain.User.entity.User;
import com.ruben.sigebi.domain.common.exception.ElementNotFoundInTheDatabaseException;
import com.ruben.sigebi.domain.common.exception.InvalidStateException;
import com.ruben.sigebi.domain.roles.entity.Role;
import com.ruben.sigebi.domain.roles.repository.RoleRepository;
import com.ruben.sigebi.domain.User.repository.UserRepository;
import com.ruben.sigebi.domain.roles.valueObjects.Permission;
import com.ruben.sigebi.domain.User.valueObject.UserId;
import org.springframework.stereotype.Service;

@Service
public class UserAuthorizationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserAuthorizationService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public void checkUserCanAssignPermission(UserId actorId, UserId targetId) {
        User actor = getActiveUser(actorId);
        User target = getActiveUser(targetId);
        checkPermission(actor, "ASSIGN_PERMISSION",
                "User does not have permission to assign permissions.");
    }

    public void checkUserCanAssignRole(UserId actorId, UserId targetId) {
        User actor = getActiveUser(actorId);
        User target = getActiveUser(targetId);
        checkPermission(actor, "ASSIGN_ROLE",
                "User does not have permission to assign roles.");
    }



    private User getActiveUser(UserId userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ElementNotFoundInTheDatabaseException("User not found: " + userId));
        if (!user.isActive()) {
            throw new InvalidStateException("User is not active: " + userId);
        }
        return user;
    }

    private void checkPermission(User actor, String action, String errorMessage) {
        for (var roleId : actor.getRoles()) {
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new ElementNotFoundInTheDatabaseException("Role not found: " + roleId));
            if (role.hasPermission(new Permission("USER", action))) {
                return;
            }
        }
        throw new ForbiddenException(errorMessage);
    }
}