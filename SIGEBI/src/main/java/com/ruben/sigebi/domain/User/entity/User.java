package com.ruben.sigebi.domain.User.entity;
import com.ruben.sigebi.domain.User.enums.UserStates;
import com.ruben.sigebi.domain.User.valueObject.EmailAddress;
import com.ruben.sigebi.domain.User.valueObject.Password;
import com.ruben.sigebi.domain.User.valueObject.RoleID;
import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.common.enums.Status;
import com.ruben.sigebi.domain.common.exception.InvalidationException;
import com.ruben.sigebi.domain.common.objectValue.ActivatableAggregate;
import com.ruben.sigebi.domain.common.objectValue.FullName;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "users")
public class User extends ActivatableAggregate {
    @NotBlank
    @Embedded
    private final FullName fullName;

    @NotBlank
    @Embedded
    private final EmailAddress email;

    @NotBlank
    @Embedded
    private final Password password;


    private final Set<RoleID> rolesId;
    private final UserId userId;
    @Embedded
    private UserStates userStates;

    public User(UserId userId, FullName fullName, EmailAddress email, Password password, HashSet<RoleID> roleId, UserStates userStates) {
        this.fullName = Objects.requireNonNull(fullName);
        Objects.requireNonNull(userId);
        this.userId = userId;
        this.email = Objects.requireNonNull(email);
        this.password = Objects.requireNonNull(password);
        this.rolesId = new HashSet<>(Objects.requireNonNull(roleId));
        this.userStates = Objects.requireNonNull(userStates);
        activate();
    }


    public void markAsPenalize(){
        if (!isActive()){
            throw new InvalidationException("Cannot penalize if user is not active.");
        }
        if (this.userStates.equals(UserStates.PENALIZE)){
            throw new InvalidationException("Cannot mark as penalize if user state is: "+ this.getUserState());
        }
        this.userStates = UserStates.PENALIZE;
    }
    public void markAsEligible(){
        if (!isActive()){
            throw new InvalidationException("Cannot penalize if user is not active.");
        }
        if (this.userStates.equals(UserStates.ELIGIBLE)){
            throw new InvalidationException("Cannot mark as eligible if user state is: "+ this.getUserState());
        }
        this.userStates = UserStates.ELIGIBLE;
    }


    public Set<RoleID> getRoles() {
        return rolesId;
    }

    public boolean assignRoles(Set<RoleID> rolesId) {
        Objects.requireNonNull(rolesId);
        if (!isActive()){
            throw new InvalidationException("Cannot assign role if user is not active.");
        }
        boolean wasAdded  = false;
        for (var a : rolesId){
            wasAdded |= this.rolesId.add(a);
        }
        return wasAdded ;
    }

    public boolean assignRole(RoleID rolesId) {
        if (!isActive()){
            throw new InvalidationException("Cannot assign role if user is not active.");
        }
        return this.rolesId.add(Objects.requireNonNull(rolesId));
    }

    public boolean removeRoles(Set<RoleID> rolesId){
        Objects.requireNonNull(rolesId);
        if (!isActive()){
            throw new InvalidationException("Cannot remove role if user is not active.");
        }
        boolean wasAdded  = false;
        for (var a : rolesId){
            wasAdded |= this.rolesId.remove(a);
        }
        return wasAdded ;
    }

    public boolean removeRole(RoleID rolesId){
        Objects.requireNonNull(rolesId);
        if (!isActive()){
            throw new InvalidationException("Cannot remove role if user is not active.");
        }
        return this.rolesId.remove(rolesId);
    }


    //getters
    public EmailAddress getEmail() {
        return email;
    }

    public FullName getFullName() {
        return fullName;
    }

    public UserId getUserId() {
        return userId;
    }

    public Password getPassword() {
        return password;
    }

    public UserStates getUserState() {
        return userStates;
    }

}
