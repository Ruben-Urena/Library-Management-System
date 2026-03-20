package com.ruben.sigebi.domain.User.entity;
import com.ruben.sigebi.domain.User.enums.UserStates;
import com.ruben.sigebi.domain.User.valueObject.EmailAddress;
import com.ruben.sigebi.domain.User.valueObject.Password;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.PenaltyId;
import com.ruben.sigebi.domain.roles.valueObjects.RoleID;
import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.common.exception.InvalidationException;
import com.ruben.sigebi.domain.common.objectValue.ActivatableAggregate;
import com.ruben.sigebi.domain.common.objectValue.FullName;

import java.util.*;

public class User extends ActivatableAggregate {

    private final FullName fullName;

    private final EmailAddress email;

    private final Password password;

    private final Set<RoleID> rolesId;


    private final UserId userId;

    private UserStates userStates;


    public User(UserId userId, FullName fullName, EmailAddress email, Password password, HashSet<RoleID> roleId, UserStates userStates) {
        this.fullName = Objects.requireNonNull(fullName);
        Objects.requireNonNull(userId);
        this.userId = userId;
        this.email = Objects.requireNonNull(email);
        this.password = Objects.requireNonNull(password);
        this.rolesId = new HashSet<>(Objects.requireNonNull(roleId));
        this.userStates = Objects.requireNonNull(userStates);
    }



    public void markAsPenalize(){
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
        return Set.copyOf(rolesId);
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;
        return getUserId().equals(user.getUserId());
    }

    @Override
    public int hashCode() {
        return getUserId().hashCode();
    }
}
