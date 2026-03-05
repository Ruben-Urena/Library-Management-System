package com.ruben.sigebi.domain.User.repository;

import com.ruben.sigebi.domain.User.entity.User;
import com.ruben.sigebi.domain.User.enums.UserStatus;
import com.ruben.sigebi.domain.User.valueObject.EmailAddress;
import com.ruben.sigebi.domain.User.valueObject.UserId;

import java.util.List;
import java.util.Optional;

public interface UserRepository {


    void save(User user);
    Optional<User> findById(UserId id);
    Optional<User> findByEmail(EmailAddress email);
    List<User> findByStatus(UserStatus status);
    void delete(User user);
}