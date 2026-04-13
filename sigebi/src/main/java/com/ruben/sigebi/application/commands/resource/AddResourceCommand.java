package com.ruben.sigebi.application.commands.resource;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.Language;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceMainData;
import java.util.Set;


import org.hibernate.validator.constraints.ISBN;

public record AddResourceCommand(
        ResourceMainData mainData,
        Language language,
        String resourceType,
        Set<AddAuthorCommand> authors,
        @ISBN
        String isbn,
        int quantity){


}
