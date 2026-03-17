package com.ruben.sigebi.domain.bibliographyResource.factory;

import com.ruben.sigebi.application.commands.resource.AddResourceCommand;
import com.ruben.sigebi.domain.bibliographyResource.entity.BibliographyResource;

public interface ResourceCreator {
    BibliographyResource create(AddResourceCommand command);
    String type();
}
