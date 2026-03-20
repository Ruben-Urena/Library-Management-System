package com.ruben.sigebi.infrastructure.persistence.entity.user;

import com.ruben.sigebi.domain.User.enums.UserStates;
import com.ruben.sigebi.domain.common.enums.Status;
import com.ruben.sigebi.infrastructure.persistence.entity.role.RoleEntity;
import com.ruben.sigebi.infrastructure.persistence.entity.user.embed.EmailEmbeddable;
import com.ruben.sigebi.infrastructure.persistence.entity.user.embed.FullNameEmbeddable;
import com.ruben.sigebi.infrastructure.persistence.entity.user.embed.PasswordEmbeddable;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    private UUID id;

    @Embedded
    private FullNameEmbeddable fullName;

    @Embedded
    private EmailEmbeddable email;

    @Embedded
    private PasswordEmbeddable password;

    @Enumerated(EnumType.STRING)
    @Column(name = "user_state")
    private UserStates userState;

    @Enumerated(EnumType.STRING)
    private Status status;

    public UserEntity() {
    }

    @ManyToMany
    @JoinTable(
            name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<RoleEntity> roles = new HashSet<>();

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public FullNameEmbeddable getFullName() {
        return fullName;
    }

    public void setFullName(FullNameEmbeddable fullName) {
        this.fullName = fullName;
    }

    public EmailEmbeddable getEmail() {
        return email;
    }

    public void setEmail(EmailEmbeddable email) {
        this.email = email;
    }

    public PasswordEmbeddable getPassword() {
        return password;
    }

    public void setPassword(PasswordEmbeddable password) {
        this.password = password;
    }

    public UserStates getUserState() {
        return userState;
    }

    public void setUserState(UserStates userState) {
        this.userState = userState;
    }

    public Set<RoleEntity> getRoles() {
        return roles;
    }

    public void setRoles(Set<RoleEntity> roles) {
        this.roles = roles;
    }
}