package com.ruben.sigebi.infrastructure.persistence.repository.roleRepo;

import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.roles.entity.Role;
import com.ruben.sigebi.domain.roles.valueObjects.Permission;
import com.ruben.sigebi.infrastructure.persistence.entity.role.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface SpringDataRoleRepository
        extends JpaRepository<RoleEntity, UUID> {

    Optional<RoleEntity> findByRoleNameSpecial(String special);
    public Set<RoleEntity> findByUser(String userid);
    public Optional<RoleEntity> findByPermission(String permission);

}