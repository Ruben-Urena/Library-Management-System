package com.ruben.sigebi.domain.bibliographyResource.factory;

import com.ruben.sigebi.application.commands.resource.CreateResourceCommand;
import com.ruben.sigebi.domain.bibliographyResource.entity.BibliographyResource;

public interface ResourceCreator {
    BibliographyResource create(CreateResourceCommand command);
    String type();
}
