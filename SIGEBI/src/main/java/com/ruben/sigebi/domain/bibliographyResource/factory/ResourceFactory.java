package com.ruben.sigebi.domain.bibliographyResource.factory;

import com.ruben.sigebi.application.commands.resource.AddResourceCommand;
import com.ruben.sigebi.domain.bibliographyResource.entity.BibliographyResource;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.AuthorId;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class ResourceFactory {

    private Map<String, ResourceCreator> creators ;

    public ResourceFactory(List<ResourceCreator> creators) {
        this.creators = creators.stream()
                .collect(Collectors.toMap(
                        ResourceCreator::type,
                        Function.identity()
                ));
    }

    public List<BibliographyResource> create(AddResourceCommand command, Set<AuthorId> authorIdSet) {

        ResourceCreator creator = creators.get(command.resourceType());

        var listOfResources = new ArrayList<BibliographyResource>();

        if (creator == null) {
            throw new IllegalArgumentException("Invalid resource type");
        }

        listOfResources.add(creator.create(command, authorIdSet));

        return listOfResources;
    }

}
