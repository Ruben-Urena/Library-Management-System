package com.ruben.sigebi.infrastructure.persistence.repository.authorRepo;
import com.ruben.sigebi.domain.author.entity.Author;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceID;
import com.ruben.sigebi.infrastructure.persistence.entity.author.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SpringDataAuthorRepository
        extends JpaRepository<AuthorEntity, UUID> {

    Optional<AuthorEntity> resource(String id);
}