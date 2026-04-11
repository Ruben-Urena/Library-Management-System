package com.ruben.sigebi.domain.bibliographyResource.factory;

import com.ruben.sigebi.application.commands.resource.AddResourceCommand;
import com.ruben.sigebi.domain.bibliographyResource.entity.BibliographyResource;
import com.ruben.sigebi.domain.bibliographyResource.entity.Book;
import com.ruben.sigebi.domain.bibliographyResource.entity.ResourceCopy;
import com.ruben.sigebi.domain.bibliographyResource.enums.ResourceState;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.AuthorId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ISBN;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceCopyId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceID;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Component
public class BookCreator implements ResourceCreator {
    @Override
    public BibliographyResource create(AddResourceCommand command, Set<AuthorId> authorIdSet) {

        return new Book(
                command.mainData(),
                command.language(),
                command.resourceType().toUpperCase(),
                authorIdSet,
                new ResourceID(UUID.randomUUID()),
                null,
                new ISBN(command.isbn())
        );
    }

    @Override
    public List<ResourceCopy> createCopies(BibliographyResource resource, int quantity) {
        List<ResourceCopy> copies = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            ResourceCopy copy = new ResourceCopy(
                    new ResourceCopyId(UUID.randomUUID()),
                    ResourceState.AVAILABLE,
                    resource.getId(),
                    Instant.now()
            );
            copy.activate();
            copies.add(copy);
        }
        return copies;
    }

    @Override
    public String type() {
        return "BOOK";
    }

}
