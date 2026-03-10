package com.ruben.sigebi.domain.User.repository;

import com.ruben.sigebi.domain.User.entity.Role;
import com.ruben.sigebi.domain.User.valueObject.RoleID;
import com.ruben.sigebi.domain.User.valueObject.UserId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;

import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID>{
    Optional<Role> findByUser(UserId userid);
}
