package com.ruben.sigebi.infrastructure.persistence.repository.roleRepo.adapter;

import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.roles.entity.Role;
import com.ruben.sigebi.domain.roles.repository.RoleRepository;
import com.ruben.sigebi.domain.roles.valueObjects.Permission;
import com.ruben.sigebi.domain.roles.valueObjects.RoleID;
import com.ruben.sigebi.domain.roles.valueObjects.SpecialName;
import com.ruben.sigebi.infrastructure.persistence.mapper.RoleMapper;
import com.ruben.sigebi.infrastructure.persistence.repository.roleRepo.SpringDataRoleRepository;
import org.springframework.stereotype.Repository;
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
    public Set<Role> findByUser(UserId userId) {
        return repository.findRolesByUserId(userId.value())
                .stream()
                .map(RoleMapper::toDomain)
                .collect(Collectors.toSet());
    }

    @Override
    public void save(Role role) {
        repository.save(RoleMapper.toEntity(role));
    }

    @Override
    public Optional<Role> findById(RoleID roleID) {
        return repository.findById(roleID.value())
                .map(RoleMapper::toDomain);
    }

    @Override
    public Optional<Role> findByName(SpecialName name) {
        return repository.findByRoleNameSpecial(name.special())
                .map(RoleMapper::toDomain);
    }

    @Override
    public Set<Role> findByPermission(Permission permission) {
        return repository.findByPermissions(permission.value())
                .stream()
                .map(RoleMapper::toDomain)
                .collect(Collectors.toSet());
    }

}