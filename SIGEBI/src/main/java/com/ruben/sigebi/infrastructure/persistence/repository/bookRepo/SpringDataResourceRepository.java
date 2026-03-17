package com.ruben.sigebi.infrastructure.persistence.repository.bookRepo;

import com.ruben.sigebi.infrastructure.persistence.entity.bibliographyResource.BibliographyResourceEntity;
import com.ruben.sigebi.infrastructure.persistence.entity.bibliographyResource.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SpringDataResourceRepository
        extends JpaRepository<BookEntity, UUID> {
    List<BibliographyResourceEntity> findByStatus(String status);
    List<BibliographyResourceEntity>findByState(String state);
    List<BibliographyResourceEntity> findByAuthorId(String authorId);
}