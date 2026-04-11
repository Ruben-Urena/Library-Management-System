package com.ruben.sigebi.domain.roles.entity;
import com.ruben.sigebi.domain.roles.exeption.InvalidRoleException;
import com.ruben.sigebi.domain.roles.valueObjects.Permission;
import com.ruben.sigebi.domain.roles.valueObjects.RoleID;
import com.ruben.sigebi.domain.roles.valueObjects.SpecialName;
import com.ruben.sigebi.domain.common.objectValue.ActivatableAggregate;

import java.util.*;

public class Role extends ActivatableAggregate {

    private final RoleID roleID;
    private final Set<Permission> permissions;
    private SpecialName roleName;
    private String roleDescription;

    public Role(RoleID roleID, SpecialName roleName, Set<Permission> permissions, String roleDescription) {
        this.roleID = Objects.requireNonNull(roleID);
        this.permissions = new HashSet<>(Objects.requireNonNull(permissions));
        this.roleName = Objects.requireNonNull(roleName);
        this.roleDescription = Objects.requireNonNull(roleDescription);
    }


    public Role(SpecialName roleName, Set<Permission> permissions, String roleDescription) {
        this(new RoleID(UUID.randomUUID()), roleName, permissions, roleDescription);
    }

    public boolean hasPermission(Permission permission) {
        return permissions.contains(permission);
    }

    public void changeRoleName(SpecialName roleName) {
        if (this.roleName.equals(roleName)) {
            throw new InvalidRoleException("New role name is the same as the current one: " + this.roleName);
        }
        this.roleName = Objects.requireNonNull(roleName, "Role name cannot be null");
    }

    public void changeRoleDescription(String roleDescription) {
        if (this.roleDescription.equals(roleDescription)) {
            throw new InvalidRoleException("New description is the same as the current one: " + this.roleDescription);
        }
        this.roleDescription = Objects.requireNonNull(roleDescription, "Role description cannot be null");
    }

    public boolean addPermission(Permission permission) {
        Objects.requireNonNull(permission, "Permission cannot be null");
        return this.permissions.add(permission);
    }

    public boolean addPermissions(Set<Permission> permissions) {
        Objects.requireNonNull(permissions, "Permissions cannot be null");
        boolean wasAdded = false;
        for (var p : permissions) {
            wasAdded |= this.permissions.add(p); // ✅ añade a this.permissions, no a un set local
        }
        return wasAdded;
    }

    public RoleID getRoleID() { return roleID; }
    public SpecialName getRoleName() { return roleName; }
    public Set<Permission> getPermissions() { return Collections.unmodifiableSet(permissions); }
    public String getRoleDescription() { return roleDescription; }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return getRoleID().equals(role.getRoleID());
    }

    @Override
    public int hashCode() {
        return getRoleID().hashCode();
    }
}