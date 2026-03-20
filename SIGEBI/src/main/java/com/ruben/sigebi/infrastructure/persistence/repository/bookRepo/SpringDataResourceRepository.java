package com.ruben.sigebi.infrastructure.persistence.repository.bookRepo;

import com.ruben.sigebi.domain.bibliographyResource.enums.ResourceState;
import com.ruben.sigebi.infrastructure.persistence.entity.bibliographyResource.BibliographyResourceEntity;
import com.ruben.sigebi.infrastructure.persistence.entity.bibliographyResource.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface SpringDataResourceRepository
        extends JpaRepository<BibliographyResourceEntity, UUID> {

    // ✅ status es campo directo en BibliographyResourceEntity (herencia JOINED)
    // JPA sabe buscar en la tabla padre resources
    List<BookEntity> findByStatus(String status);

    // ✅ state es campo directo en PhysicalResourceEntity
    List<BookEntity> findByState(ResourceState state);

    // ❌ ANTES: findByAuthorId(String authorId)
    //    JPA no puede resolver "authorId" — el campo es authorsIds (Set<AuthorEntity>)
    //    y AuthorEntity tiene id como campo → necesita @Query con JOIN
    // ✅ AHORA:
    @Query("SELECT b FROM BookEntity b JOIN b.authorsIds a WHERE a.id = :authorId")
    List<BookEntity> findByAuthorId(@Param("authorId") UUID authorId);
}