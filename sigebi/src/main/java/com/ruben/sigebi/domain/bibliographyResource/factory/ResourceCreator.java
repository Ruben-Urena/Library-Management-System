package com.ruben.sigebi.domain.bibliographyResource.factory;

import com.ruben.sigebi.application.commands.resource.AddResourceCommand;
import com.ruben.sigebi.domain.bibliographyResource.entity.BibliographyResource;
import com.ruben.sigebi.domain.bibliographyResource.entity.ResourceCopy;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.AuthorId;
import java.util.List;
import java.util.Set;

public interface ResourceCreator {
    BibliographyResource create(AddResourceCommand command, Set<AuthorId> authorIdSet);
    List<ResourceCopy> createCopies(BibliographyResource resource, int quantity);
    String type();

}
