package com.ruben.sigebi.api.dto.response.resource;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.AuthorId;

import java.util.List;
import java.util.UUID;

public record GetAllResourceResponse(
        String resourceId,
        String language,
        String title,
        String subtitle,
        String description,
        String resourceType,
        String edition,
        String publicationDate,
        String status,
        String state,
        String physicalFormat,
        String ShelfLocation,
        String isbn,
        List<String> authors
){
}
