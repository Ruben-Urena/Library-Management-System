package com.ruben.sigebi.infrastructure.persistence.repository.authorRepo;
import com.ruben.sigebi.infrastructure.persistence.entity.author.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataAuthorRepository
        extends JpaRepository<AuthorEntity, UUID> {

}
