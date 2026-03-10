package com.ruben.sigebi.domain.bibliographyResource.factory;

import com.ruben.sigebi.application.commands.resource.CreateResourceCommand;
import com.ruben.sigebi.domain.bibliographyResource.entity.BibliographyResource;
import com.ruben.sigebi.domain.bibliographyResource.entity.Book;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ISBN;

public class BookCreator implements ResourceCreator {
    @Override
    public BibliographyResource create(CreateResourceCommand command) {
        return new Book(
                command.mainData(),
                command.language(),
                command.resourceType(),
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
