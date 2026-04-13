package com.ruben.sigebi.infrastructure.persistence.repository.roleRepo;

import com.ruben.sigebi.infrastructure.persistence.entity.role.RoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface SpringDataRoleRepository
        extends JpaRepository<RoleEntity, UUID> {
    Optional<RoleEntity> findByRoleNameSpecial(String special);

    @Query("SELECT r FROM RoleEntity r JOIN r.permissions p WHERE CONCAT(p.source, ':', p.action) = :permissionValue")
    Set<RoleEntity> findByPermissions(@Param("permissionValue") String permissionValue);

    @Query("SELECT r FROM RoleEntity r JOIN r.users u WHERE u.id = :userId")
    Set<RoleEntity> findRolesByUserId(@Param("userId") UUID userId);
}