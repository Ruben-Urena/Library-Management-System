package com.ruben.sigebi.application.service;
import com.ruben.sigebi.domain.common.exception.BusinessRuleViolationException;
import com.ruben.sigebi.domain.common.exception.ElementNotFoundInTheDatabaseException;
import com.ruben.sigebi.domain.common.exception.InvalidStateException;
import com.ruben.sigebi.domain.roles.entity.Role;
import com.ruben.sigebi.domain.User.entity.User;
import com.ruben.sigebi.domain.roles.repository.RoleRepository;
import com.ruben.sigebi.domain.User.repository.UserRepository;
import com.ruben.sigebi.domain.roles.valueObjects.Permission;
import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.bibliographyResource.repository.BibliographyRepository;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceID;
import com.ruben.sigebi.domain.common.exception.DomainException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ResourceAuthorizationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BibliographyRepository bibliographyRepository;

    public ResourceAuthorizationService(UserRepository userRepository,
                                        RoleRepository roleRepository,
                                        BibliographyRepository bibliographyRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bibliographyRepository = bibliographyRepository;
    }

    public void deleteResource(UserId userId, ResourceID resourceId) {
        checkPermission(userId, "RESOURCE", "DELETE");
        var resource = bibliographyRepository.findById(resourceId)
                .orElseThrow(() -> new ElementNotFoundInTheDatabaseException("Resource not found: " + resourceId));
        resource.deactivate();
    }

    public void addResource(UserId userId) {
        checkPermission(userId, "RESOURCE", "ADD");
    }

    public void editResource(UserId userId) {
        checkPermission(userId, "RESOURCE", "EDIT");
    }


    private void checkPermission(UserId userId, String source, String action) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ElementNotFoundInTheDatabaseException("User not found: " + userId));

        if (!user.isActive()) {
            throw new InvalidStateException("User is not active: " + userId);
        }

        for (var roleId : user.getRoles()) {
            Role role = roleRepository.findById(roleId)
                    .orElseThrow(() -> new ElementNotFoundInTheDatabaseException("Role not found: " + roleId));
            if (role.hasPermission(new Permission(source, action))) {
                return;
            }
        }

        throw new BusinessRuleViolationException(
                "User does not have permission [" + source + ":" + action + "]: " + userId);
    }
}