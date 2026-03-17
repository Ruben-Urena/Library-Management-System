package com.ruben.sigebi.infrastructure.persistence.mapper;

import com.ruben.sigebi.domain.User.entity.User;
import com.ruben.sigebi.domain.User.valueObject.EmailAddress;
import com.ruben.sigebi.domain.User.valueObject.Password;
import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.common.objectValue.FullName;
import com.ruben.sigebi.domain.roles.valueObjects.RoleID;
import com.ruben.sigebi.infrastructure.persistence.entity.role.RoleEntity;
import com.ruben.sigebi.infrastructure.persistence.entity.user.UserEntity;
import com.ruben.sigebi.infrastructure.persistence.entity.user.embed.EmailEmbeddable;
import com.ruben.sigebi.infrastructure.persistence.entity.user.embed.FullNameEmbeddable;
import com.ruben.sigebi.infrastructure.persistence.entity.user.embed.PasswordEmbeddable;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class UserMapper {

    private UserMapper(){}

    public static UserEntity toEntity(User user, Set<RoleEntity> roles) {

        UserEntity entity = new UserEntity();

        entity.setId(user.getUserId().value().toString());

        entity.setFullName(
                new FullNameEmbeddable(
                        user.getFullName().name(),
                        user.getFullName().lastName()
                )
        );

        entity.setEmail(
                new EmailEmbeddable(
                        user.getEmail().email()
                )
        );

        entity.setPassword(
                new PasswordEmbeddable(
                        user.getPassword().value()
                )
        );

        entity.setUserState(user.getUserState());

        entity.setRoles(roles);

        return entity;
    }


    public static User toDomain(UserEntity entity) {

        Set<RoleID> roles =
                entity.getRoles()
                        .stream()
                        .map(r -> new RoleID(UUID.fromString(r.getId())))
                        .collect(Collectors.toSet());

        return new User(
                new UserId(UUID.fromString(entity.getId())),
                new FullName(
                        entity.getFullName().getName(),
                        entity.getFullName().getLastname()
                ),
                new EmailAddress(entity.getEmail().getEmail()),
                new Password(entity.getPassword().getValue()),
                new HashSet<>(roles),
                entity.getUserState()
        );
    }
}