package com.ruben.sigebi.domain.bibliographyResource.repository;

import com.ruben.sigebi.domain.bibliographyResource.entity.BibliographyResource;
import com.ruben.sigebi.domain.bibliographyResource.entity.PhysicalResource;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.AuthorId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceID;
import java.util.List;
import java.util.Optional;

public interface BibliographyRepository {
    void save(BibliographyResource resource);
    Optional<BibliographyRepository> findById(ResourceID id);
    List<BibliographyRepository> findByAuthorId(AuthorId authorId);
}