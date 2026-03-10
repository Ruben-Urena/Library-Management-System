package com.ruben.sigebi.domain.service;
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

import java.util.Optional;

public class ResourceAuthorizationService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BibliographyRepository bibliographyRepository;
    public ResourceAuthorizationService(UserRepository userRepository, RoleRepository roleRepository, BibliographyRepository bibliographyRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bibliographyRepository = bibliographyRepository;
    }

    public void deleteResource(UserId userId, ResourceID resourceId) {
        hasPermissionToDelete(userId);
        var resource = bibliographyRepository.findById(resourceId);
        if (resource.isEmpty()) {
            throw new RuntimeException("resource not found");
        }
        resource.get().deactivate();
    }

    public void addResource(UserId userId){
        hasPermissionToAdd(userId);
    }
    public void editResource(UserId userId){
        hasPermissionToEdit(userId);
    }


    private void hasPermissionToDelete(UserId userId){
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()){
            throw new RuntimeException("User does not exist.");
        }
        if (!user.get().isActive()){
            throw new DomainException("User is not active.");
        }
        for (var a : user.get().getRoles()){
            Optional<Role> roles = roleRepository.findById(a);
            if (roles.isEmpty()){
                throw new RuntimeException("Role "+a+" does not exist.");
            }
            if ((roles.get().hasPermission(new Permission("RESOURCE","DELETE")) )){
                break;
            }
        }
        throw new DomainException("User does not have permission to delete a resource.");
    }

    private void hasPermissionToAdd(UserId userId){
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()){
            throw new RuntimeException("User does not exist.");
        }
        if (!user.get().isActive()){
            throw new DomainException("User is not active.");
        }
        for (var a : user.get().getRoles()){
            Optional<Role> roles = roleRepository.findById(a);
            if (roles.isEmpty()){
                throw new RuntimeException("Role "+a+" does not exist.");
            }
            if ((roles.get().hasPermission(new Permission("RESOURCE","ADD")) )){
                break;
            }
        }
        throw new DomainException("User does not have permission to add a resource.");
    }
    private void hasPermissionToEdit(UserId userId){
        Optional<User> user = userRepository.findById(userId);
        if (user.isEmpty()){
            throw new RuntimeException("User does not exist.");
        }
        if (!user.get().isActive()){
            throw new DomainException("User is not active.");
        }
        for (var a : user.get().getRoles()){
            Optional<Role> roles = roleRepository.findById(a);
            if (roles.isEmpty()){
                throw new RuntimeException("Role "+a+" does not exist.");
            }
            if ((roles.get().hasPermission(new Permission("RESOURCE","EDIT")) )){
                break;
            }
        }
        throw new DomainException("User does not have permission to edit a resource.");
    }

}
