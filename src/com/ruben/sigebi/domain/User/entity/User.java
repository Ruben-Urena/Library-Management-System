package com.ruben.sigebi.domain.User.entity;
import com.ruben.sigebi.domain.User.enums.UserStatus;
import com.ruben.sigebi.domain.User.valueObject.EmailAddress;
import com.ruben.sigebi.domain.User.valueObject.Password;
import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.common.exception.InvalidationException;
import com.ruben.sigebi.domain.common.objectValue.FullName;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class User{

    private final FullName fullName;
    private EmailAddress email;
    private Password password;
    private Set<Role> roles;
    private UserId userId;
    private UserStatus userStatus;

    public User(UserId userId, FullName fullName, EmailAddress email, Password password, HashSet<Role> role, UserStatus userStatus) {
        this.fullName = Objects.requireNonNull(fullName);
        Objects.requireNonNull(userId);
        this.userId = userId;
        this.email = Objects.requireNonNull(email);
        this.password = Objects.requireNonNull(password);
        this.roles = new HashSet<>(Objects.requireNonNull(role));
        this.userStatus = Objects.requireNonNull(userStatus);
    }

    private void changePassword(Password password){//Not finished yet, need strong hash and
        this.password = password;
    }

    private void changeEmail(EmailAddress email){
        if (this.getUserStatus().equals(UserStatus.INACTIVE)){
            throw new InvalidationException("Email cannot be changed if user is: "+ this.getUserStatus());
        }
        this.email = email;
    }

    private void changeStatus(UserStatus userStatus){
        this.userStatus = userStatus;
    }


    public Set<Role> getRoles() {
        return roles;
    }

    public boolean assignRoles(Set<Role> roles) {
        Objects.requireNonNull(roles);
        boolean wasAdded  = false;
        for (var a : roles){
            wasAdded |= this.roles.add(a);
        }
        return wasAdded ;
    }

    public boolean assignRole(Role roles) {
        return this.roles.add(Objects.requireNonNull(roles));
    }

    public boolean removeRoles(Set<Role> roles){
        Objects.requireNonNull(roles);
        boolean wasAdded  = false;
        for (var a : roles){
            wasAdded |= this.roles.remove(a);
        }
        return wasAdded ;
    }

    public boolean removeRole(Role roles){
        return this.roles.remove(Objects.requireNonNull(roles));
    }

    public boolean isActive(){
        return this.userStatus.equals(UserStatus.ACTIVE);
    }

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

    public UserStatus getUserStatus() {
        return userStatus;
    }

}
