package com.ruben.sigebi.infrastructure.persistence.repository.roleRepo.adapter;

import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.roles.entity.Role;
import com.ruben.sigebi.domain.roles.repository.RoleRepository;
import com.ruben.sigebi.domain.roles.valueObjects.Permission;
import com.ruben.sigebi.domain.roles.valueObjects.RoleID;
import com.ruben.sigebi.domain.roles.valueObjects.SpecialName;
import com.ruben.sigebi.infrastructure.persistence.entity.role.RoleEntity;
import com.ruben.sigebi.infrastructure.persistence.mapper.RoleMapper;
import com.ruben.sigebi.infrastructure.persistence.repository.roleRepo.SpringDataRoleRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class JpaRoleRepository implements RoleRepository {

    private final SpringDataRoleRepository repository;

    public JpaRoleRepository(SpringDataRoleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Set<Role> findByUser(UserId userid) {
        return repository.findByUser(userid.value().toString())
                .stream()
                .map(RoleMapper::toDomain)
                .collect(Collectors.toSet());
    }

    @Override
    public void save(Role role) {
        RoleEntity entity = RoleMapper.toEntity(role);
        repository.save(entity);
    }

    @Override
    public Optional<Role> findById(RoleID roleID) {
        return repository.findById(roleID.value())
                .map(RoleMapper::toDomain);
    }

    @Override
    public Optional<Role> findByName(SpecialName name) {
        return repository
                .findByRoleNameSpecial(name.special())
                .map(RoleMapper::toDomain);
    }

    @Override
    public Optional<Role> findByPermission(Permission permission) {
        return repository
                .findByPermission(permission.value())
                .map(RoleMapper::toDomain);
    }
}