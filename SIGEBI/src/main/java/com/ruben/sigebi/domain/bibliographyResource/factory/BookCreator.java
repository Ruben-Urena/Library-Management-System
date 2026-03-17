package com.ruben.sigebi.domain.bibliographyResource.factory;

import com.ruben.sigebi.application.commands.resource.AddResourceCommand;
import com.ruben.sigebi.domain.bibliographyResource.entity.BibliographyResource;
import com.ruben.sigebi.domain.bibliographyResource.entity.Book;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ISBN;
import org.springframework.stereotype.Component;

@Component
public class BookCreator implements ResourceCreator {
    @Override
    public BibliographyResource create(AddResourceCommand command) {
        return new Book(
                command.mainData(),
                command.language(),
                command.resourceType().toUpperCase(),
                command.authorIdSet(),
                command.userId(),
                new ISBN(command.isbn())
        );
    }

    @Override
    public String type() {
        return "BOOK";
    }

}
