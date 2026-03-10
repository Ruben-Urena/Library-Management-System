package com.ruben.sigebi.domain.bibliographyResource.factory;

import com.ruben.sigebi.application.commands.resource.CreateResourceCommand;
import com.ruben.sigebi.domain.bibliographyResource.entity.BibliographyResource;
import java.util.Map;

public class ResourceFactory {

    private Map<String, ResourceCreator> creators;

    public ResourceFactory(Map<String, ResourceCreator> creators) {
        this.creators = creators;
    }

    public BibliographyResource create(CreateResourceCommand command) {

        ResourceCreator creator = creators.get(command.resourceType());

        if (creator == null) {
            throw new IllegalArgumentException("Invalid resource type");
        }

        return creator.create(command);
    }

}