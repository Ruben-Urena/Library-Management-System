package com.ruben.sigebi.domain.bibliographyResource.repository;

import com.ruben.sigebi.domain.bibliographyResource.entity.BibliographyResource;
import com.ruben.sigebi.domain.bibliographyResource.entity.PhysicalResource;
import com.ruben.sigebi.domain.bibliographyResource.enums.ResourceState;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.AuthorId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceID;
import com.ruben.sigebi.domain.common.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BibliographyRepository   {
    List<BibliographyResource> findByAuthorId(AuthorId authorId);
    void save(BibliographyResource resource);
    Optional<BibliographyResource> findById(ResourceID id);
    List<BibliographyResource> findAll();
    List<BibliographyResource>findByStatus(Status status);
    List<BibliographyResource>findByState(ResourceState state);
}
