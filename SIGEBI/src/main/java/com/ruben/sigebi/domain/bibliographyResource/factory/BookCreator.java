package com.ruben.sigebi.domain.bibliographyResource.factory;

import com.ruben.sigebi.application.commands.resource.AddResourceCommand;
import com.ruben.sigebi.domain.bibliographyResource.entity.BibliographyResource;
import com.ruben.sigebi.domain.bibliographyResource.entity.Book;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.AuthorId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ISBN;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class BookCreator implements ResourceCreator {
    @Override
    public BibliographyResource create(AddResourceCommand command, Set<AuthorId> authorIdSet) {
        return new Book(
                command.mainData(),
                command.language(),
                command.resourceType().toUpperCase(),
                authorIdSet,
                new ISBN(command.isbn()),
                command.quantity()
        );
    }

    @Override
    public String type() {
        return "BOOK";
    }

}
