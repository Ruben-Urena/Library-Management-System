package com.ruben.sigebi.infrastructure.persistence.repository.bookRepo;

import com.ruben.sigebi.domain.bibliographyResource.enums.ResourceState;
import com.ruben.sigebi.infrastructure.persistence.entity.bibliographyResource.BibliographyResourceEntity;
import com.ruben.sigebi.infrastructure.persistence.entity.bibliographyResource.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface SpringDataResourceRepository
        extends JpaRepository<BibliographyResourceEntity, UUID> {

    List<BookEntity> findByStatus(String status);



    @Query("SELECT b FROM BookEntity b JOIN b.authorsIds a WHERE b.mainData.title = :title AND a.id IN :authorIds")
    Optional<BookEntity> findByTitleAndAuthorIds(@Param("title") String title, @Param("authorIds") Set<UUID> authorIds);

    @Query("SELECT b FROM BookEntity b JOIN b.authorsIds a WHERE a.id = :authorId")
    List<BookEntity> findByAuthorId(@Param("authorId") UUID authorId);

}