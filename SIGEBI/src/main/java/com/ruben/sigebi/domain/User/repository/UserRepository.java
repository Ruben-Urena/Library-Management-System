package com.ruben.sigebi.domain.User.repository;

import com.ruben.sigebi.domain.User.entity.User;
import com.ruben.sigebi.domain.User.enums.UserStates;
import com.ruben.sigebi.domain.User.valueObject.EmailAddress;
import com.ruben.sigebi.domain.User.valueObject.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

@Repository
public interface UserRepository {
    void save(User user);
    Optional<User> findById(UserId userId);
    Set<User> findAll();
    boolean existsByEmail(EmailAddress email);
    Optional<User> findByEmail(EmailAddress email);
}
