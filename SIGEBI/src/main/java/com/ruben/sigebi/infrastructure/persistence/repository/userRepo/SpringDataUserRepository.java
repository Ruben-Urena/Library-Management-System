package com.ruben.sigebi.infrastructure.persistence.repository.userRepo;

import com.ruben.sigebi.infrastructure.persistence.entity.user.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataUserRepository
        extends JpaRepository<UserEntity, UUID> {

    Optional<UserEntity> findByEmailEmail(String email);

    boolean existsByEmailEmail(String email);

}