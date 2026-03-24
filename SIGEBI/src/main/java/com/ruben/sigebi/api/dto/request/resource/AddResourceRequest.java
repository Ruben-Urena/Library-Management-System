package com.ruben.sigebi.api.dto.request.resource;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.AuthorId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.Language;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceMainData;
import org.hibernate.validator.constraints.ISBN;

import java.util.Set;

public record AddResourceRequest(
        String title,
        String subtitle,
        String language,
        String resourceType,
        @JsonProperty("authors")
        Set<AddAuthorRequest> authors,
        @ISBN
        String isbn,
        int quantity){
}