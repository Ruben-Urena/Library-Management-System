package com.ruben.sigebi.infrastructure.persistence.repository.userRepo.adapter;

import com.ruben.sigebi.domain.User.entity.User;
import com.ruben.sigebi.domain.User.repository.UserRepository;
import com.ruben.sigebi.domain.User.valueObject.EmailAddress;
import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.infrastructure.persistence.entity.user.UserEntity;
import com.ruben.sigebi.infrastructure.persistence.mapper.UserMapper;
import com.ruben.sigebi.infrastructure.persistence.repository.userRepo.SpringDataUserRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class JpaUserRepository implements UserRepository {

    private final SpringDataUserRepository repository;

    public JpaUserRepository(SpringDataUserRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(User user) {

        UserEntity entity = UserMapper.toEntity(user, Set.of());
        repository.save(entity);
    }

    @Override
    public Optional<User> findById(UserId userId) {

        return repository.findById(userId.value())
                .map(UserMapper::toDomain);
    }

    @Override
    public Optional<User> findByEmail(EmailAddress email) {
        return repository
                .findByEmailEmail(email.email())
                .map(UserMapper::toDomain);
    }

    @Override
    public Set<User> findAll() {
        return repository.findAll()
                .stream()
                .map(UserMapper::toDomain)
                .collect(Collectors.toSet());
    }

    @Override
    public boolean existsByEmail(EmailAddress email) {
        return repository.existsByEmailEmail(email.email());
    }
}