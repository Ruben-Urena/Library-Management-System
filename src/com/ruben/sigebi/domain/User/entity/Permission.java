package com.ruben.sigebi.domain.User.entity;
import com.ruben.sigebi.domain.User.enums.PermissionStatus;
import com.ruben.sigebi.domain.User.exeption.InvalidPermissionException;
import com.ruben.sigebi.domain.User.valueObject.PermissionID;
import com.ruben.sigebi.domain.User.valueObject.SpecialName;
import java.util.Objects;
import java.util.UUID;

public class Permission {
    private final PermissionID permissionID;
    private SpecialName permissionName;
    private  String description;
    private  PermissionStatus permissionStatus;

    public Permission(SpecialName permissionName, String description){
        this.permissionName = Objects.requireNonNull(permissionName);
        this.description = Objects.requireNonNull(description);
        this.permissionID = new PermissionID(UUID.randomUUID());
        this.permissionStatus = PermissionStatus.ACTIVE;
    }

    public void changeStatus(PermissionStatus permissionStatus){
        if (this.permissionStatus.equals(permissionStatus)){
            throw new InvalidPermissionException(permissionStatus+" is the same as the actual status: "+ permissionStatus);
        }
        this.permissionStatus =Objects.requireNonNull(permissionStatus);
    }

    public PermissionID getPermissionID() {
        return permissionID;
    }

    public SpecialName getPermissionName(){
        return permissionName;
    }

    public String getDescription(){
        return description;
    }

    public void changePermissionName(SpecialName permissionName) {
        if(this.permissionName.equals(permissionName)){
            throw new InvalidPermissionException(permissionName+" is the same as the actual name: "+ this.permissionName);
        }
        this.permissionName = Objects.requireNonNull(permissionName);
    }

    public void changeDescription(String description) {
        if(this.description.equals(description)){
            throw new InvalidPermissionException(description+" is the same as the actual description: "+this.description);
        }
        this.description = Objects.requireNonNull(description);
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Permission that = (Permission) o;
        return getPermissionID().equals(that.getPermissionID());
    }

    @Override
    public int hashCode() {
        return getPermissionID().hashCode();
    }
}
