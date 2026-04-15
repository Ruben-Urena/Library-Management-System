package com.ruben.sigebi.api.mappers;
import com.ruben.sigebi.api.dto.request.resource.*;
import com.ruben.sigebi.api.dto.response.resource.GetAllResourceResponse;
import com.ruben.sigebi.application.commands.resource.*;
import com.ruben.sigebi.api.dto.response.resource.AddResourceResponse;
import com.ruben.sigebi.api.dto.response.resource.StateChangeResourceResponse;
import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.bibliographyResource.entity.BibliographyResource;
import com.ruben.sigebi.domain.bibliographyResource.entity.Book;
import com.ruben.sigebi.domain.bibliographyResource.entity.PhysicalResource;
import com.ruben.sigebi.domain.bibliographyResource.enums.ResourceState;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.Language;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceMainData;
import com.ruben.sigebi.domain.common.objectValue.FullName;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class ResourceMapper {



    public static AddResourceCommand resourceToCommand(AddResourceRequest request) {
        Objects.requireNonNull(request);
        return new AddResourceCommand(
                new ResourceMainData(request.title(), request.subtitle()),
                new Language(request.language()),
                request.resourceType(),
                request.authors().stream()
                        .map(ResourceMapper::AddauthorToCommand)
                        .collect(Collectors.toSet()),
                request.isbn(),
                request.quantity()
        );
    }

    public static AddAuthorCommand AddauthorToCommand(AddAuthorRequest request) {
        return new AddAuthorCommand(
                new FullName(request.firstName(), request.lastName())
        );
    }
    public static GetAuthorCommand getAuthorToCommand(GetAuthorRequest request){
        return new GetAuthorCommand(
                new FullName(request.firstName(),request.lastName())
        );

    }

    public static GetOneResourceCommand getOneResourceToCommand(GetOneResourceRequest request) {
        return new GetOneResourceCommand(
                new ResourceMainData(request.title(), null),
                request.author().stream()
                        .map(ResourceMapper::getAuthorToCommand)
                        .collect(Collectors.toSet())
        );
    }

    public static StateChangeResourceCommand stateToCommand(StateChangeResourceRequest request, UserId userId) {
        return new StateChangeResourceCommand(
                request.id(),
                request.resourceState(),
                userId
        );
    }

    // ─── Domain → Response ───────────────────────────────────────────────────

    public static AddResourceResponse resourceToResponseAdd(BibliographyResource resource, int copiesCreated) {
        Objects.requireNonNull(resource);
        return AddResourceResponse.success(
                resource.getMainData(),
                resource.getResourceType(),
                resource.getId(),
                copiesCreated
        );
    }

    public static GetAllResourceResponse resourceToResponseGetAll(
            BibliographyResource resource,
            List<String> authorNames
    ) {
        String state = null;
        String format = null;
        String shelfLocation = null;
        String isbn = null;
        String status = null;

        if (resource instanceof PhysicalResource p && p.getPhysicalData() != null) {

            format = p.getPhysicalData().physicalFormat();
            shelfLocation = p.getPhysicalData().shelfLocation();
        }
        if (resource instanceof Book b) {
            isbn = b.getISBN().value();
        }

        String publicationDate = resource.getPublicationData() != null
                ? resource.getPublicationData().date().toString()
                : null;

        String description = resource.getContentData() != null
                ? resource.getContentData().description()
                : null;

        return new GetAllResourceResponse(
                resource.getId().value().toString(),
                resource.getLanguage().getLanguageName(),
                resource.getMainData().title(),
                resource.getMainData().subtitle(),
                description,
                resource.getResourceType(),
                resource.getEdition(),
                publicationDate,
                resource.getStatus().name(),
                format,
                shelfLocation,
                isbn,
                authorNames
        );
    }

    public static StateChangeResourceResponse stateToResponse(BibliographyResource resource, ResourceState state) {
        return new StateChangeResourceResponse(resource.getId(), state);
    }
}
