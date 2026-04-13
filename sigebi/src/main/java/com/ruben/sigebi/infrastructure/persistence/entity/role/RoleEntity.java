package com.ruben.sigebi.infrastructure.persistence.entity.role;
import com.ruben.sigebi.domain.common.enums.Status;
import com.ruben.sigebi.infrastructure.persistence.entity.permission.Permission;
import com.ruben.sigebi.infrastructure.persistence.entity.role.embed.SpecialNameEmbeddable;
import com.ruben.sigebi.infrastructure.persistence.entity.user.UserEntity;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "roles")
public class RoleEntity {
    @Id
    private UUID id;

    @Embedded
    private SpecialNameEmbeddable roleName;

    @Column(name = "description")
    private String roleDescription;

    @Enumerated(EnumType.STRING)
    private Status status;


    @ManyToMany
    @JoinTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions = new HashSet<>();

    @ManyToMany(mappedBy = "roles")
    private Set<UserEntity> users = new HashSet<>();


    public RoleEntity() {
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<UserEntity> getUsers() {
        return users;
    }

    public void setUsers(Set<UserEntity> users) {
        this.users = users;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public SpecialNameEmbeddable getRoleName() {
        return roleName;
    }

    public void setRoleName(SpecialNameEmbeddable roleName) {
        this.roleName = roleName;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }
}