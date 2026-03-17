package com.ruben.sigebi.infrastructure.persistence.entity.role;
import com.ruben.sigebi.infrastructure.persistence.entity.permission.Permission;
import com.ruben.sigebi.infrastructure.persistence.entity.role.embed.SpecialNameEmbeddable;
import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "roles")
public class RoleEntity {
    @Id
    private String id;

    @Embedded
    private SpecialNameEmbeddable roleName;

    private String roleDescription;

    public RoleEntity() {
    }


    @JoinTable(
            name = "role_permissions",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "permission_id")
    )
    private Set<Permission> permissions = new HashSet<>();


    public String getId() {
        return id;
    }

    public void setId(String id) {
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