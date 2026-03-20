package com.ruben.sigebi.domain.roles.repository;

import com.ruben.sigebi.domain.roles.entity.Role;
import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.roles.valueObjects.Permission;
import com.ruben.sigebi.domain.roles.valueObjects.RoleID;
import com.ruben.sigebi.domain.roles.valueObjects.SpecialName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface RoleRepository {

    void save(Role role);
    Optional<Role> findById(RoleID roleID);
    Set<Role> findByUser(UserId userId);
    public Optional<Role> findByName(SpecialName specialName);
    Set<Role> findByPermission(Permission permission);
}
