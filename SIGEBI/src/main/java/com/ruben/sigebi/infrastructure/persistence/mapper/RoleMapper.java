package com.ruben.sigebi.infrastructure.persistence.mapper;

import com.ruben.sigebi.domain.roles.entity.Role;
import com.ruben.sigebi.domain.roles.valueObjects.SpecialName;
import com.ruben.sigebi.infrastructure.persistence.entity.role.RoleEntity;
import com.ruben.sigebi.infrastructure.persistence.entity.permission.Permission;
import com.ruben.sigebi.infrastructure.persistence.entity.role.embed.SpecialNameEmbeddable;

import java.util.Set;
import java.util.stream.Collectors;

public class RoleMapper {

    private RoleMapper(){}

    public static RoleEntity toEntity(Role role){

        RoleEntity entity = new RoleEntity();

        entity.setId(role.getRoleID().value().toString());

        entity.setRoleName(
                new SpecialNameEmbeddable(
                        role.getRoleName().special()
                )
        );

        entity.setRoleDescription(role.getRoleDescription());

        entity.setPermissions(
                role.getPermissions()
                        .stream()
                        .map(p -> new Permission(
                                p.source(),
                                p.action()
                        ))
                        .collect(Collectors.toSet())
        );
        return entity;
    }


    public static Role toDomain(RoleEntity entity){

        Set<com.ruben.sigebi.domain.roles.valueObjects.Permission> permissions =
                entity.getPermissions()
                        .stream()
                        .map(p ->
                                new com.ruben.sigebi.domain.roles.valueObjects.Permission(
                                        p.getSource(),
                                        p.getAction()
                                )
                        )
                        .collect(Collectors.toSet());

        return new Role(
                new SpecialName(entity.getRoleName().getSpecial()),
                permissions,
                entity.getRoleDescription()
        );
    }
}