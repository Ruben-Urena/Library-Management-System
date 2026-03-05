package com.ruben.sigebi.domain.User.entity;
import com.ruben.sigebi.domain.User.exeption.InvalidRoleException;
import com.ruben.sigebi.domain.User.valueObject.RoleID;
import com.ruben.sigebi.domain.User.valueObject.SpecialName;
import java.util.*;

public class Role {

    private final RoleID roleID;
    private Set<Permission> permissions;
    private SpecialName roleName;
    private String roleDescription;

    public Role(SpecialName roleName, Set<Permission> permissions, String roleDescription) {
        this.permissions =  new HashSet<>(Objects.requireNonNull(permissions));
        this.roleName = Objects.requireNonNull(roleName);
        this.roleDescription = Objects.requireNonNull(roleDescription);
        this.roleID = new RoleID(UUID.randomUUID());
    }

    public RoleID getRoleID() {
        return roleID;
    }
    public Set<Permission> getPermissions() {
        return Collections.unmodifiableSet(permissions);
    }

    public SpecialName getRoleName() {
        return roleName;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void changeRoleName(SpecialName roleName) {
        if (this.roleName.equals(roleName)){
            throw new InvalidRoleException("new role name is the same as the actual name: "+ this.roleName);
        }
        this.roleName = Objects.requireNonNull(roleName," role name cannot be null");
    }

    public void changeRoleDescription(String roleDescription) {
        if (this.roleDescription.equals(roleDescription)){
            throw new InvalidRoleException("new description is the same as the actual description: "+ this.roleDescription);
        }
        this.roleDescription = Objects.requireNonNull(roleDescription," role description cannot be null");
    }

    public boolean addPermission(Permission permission) {
        return this.permissions.add(Objects.requireNonNull(permission," permission cannot be null"));
    }

    public boolean addPermissions(Set<Permission> permissions){
        Objects.requireNonNull(permissions," permissions cannot be null");
        boolean wasAdded = false;
        Set<Permission> permissionsToAdd = new HashSet<>();
        for (var a : permissions){
            wasAdded |= permissionsToAdd.add(a);
        }
        return wasAdded;
    }

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
