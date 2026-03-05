package com.ruben.sigebi.domain.bibliographyResource.repository;

import com.ruben.sigebi.domain.bibliographyResource.entity.NormalPhysicalBibliographyResource;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.AuthorId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceID;
import java.util.List;
import java.util.Optional;


public interface BibliographyRepository {
    void save(NormalPhysicalBibliographyResource resource);
    Optional<NormalPhysicalBibliographyResource> findById(ResourceID id);

    List<NormalPhysicalBibliographyResource> findByAuthorId(AuthorId authorId);
}