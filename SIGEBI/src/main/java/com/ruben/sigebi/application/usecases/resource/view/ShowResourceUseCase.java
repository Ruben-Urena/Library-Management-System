package com.ruben.sigebi.application.usecases.resource.view;

import com.ruben.sigebi.api.dto.request.resource.GetAllResourceRequest;
import com.ruben.sigebi.api.dto.response.resource.GetAllResourceResponse;
import com.ruben.sigebi.api.mappers.ResourceMapper;
import com.ruben.sigebi.application.interfaces.UseCase;
import com.ruben.sigebi.domain.author.repository.AuthorRepository;
import com.ruben.sigebi.domain.bibliographyResource.entity.BibliographyResource;
import com.ruben.sigebi.domain.bibliographyResource.entity.Book;
import com.ruben.sigebi.domain.bibliographyResource.entity.PhysicalResource;
import com.ruben.sigebi.domain.bibliographyResource.repository.BibliographyRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ShowResourceUseCase {

    private final BibliographyRepository bibliographyRepository;
    private final AuthorRepository authorRepository;

    public ShowResourceUseCase(
            BibliographyRepository bibliographyRepository,
            AuthorRepository authorRepository
    ) {
        this.bibliographyRepository = bibliographyRepository;
        this.authorRepository = authorRepository;
    }

    public List<GetAllResourceResponse> execute() {
        return bibliographyRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private GetAllResourceResponse toResponse(BibliographyResource bibliographyResource) {
        List<String> authorNames = bibliographyResource.getCreditsData().authorsIds()
                .stream()
                .map(authorId -> authorRepository.findById(authorId)
                        .map(a -> a.getFullName().name() + " " + a.getFullName().lastName())
                        .orElse("Unknown")
                )
                .toList();

        String physicalResourceState = null;
        String physicalResourceFormat = null;
        String physicalResourceShelfLocation= null;

        if (bibliographyResource instanceof PhysicalResource a) {
            physicalResourceState = a.getState().name();
            physicalResourceFormat = a.getPhysicalData().physicalFormat();
            physicalResourceShelfLocation  = a.getPhysicalData().shelfLocation();
        }
        String isbn = null;

        if (bibliographyResource instanceof Book b){
            isbn = b.getISBN().value();
        }
        return new GetAllResourceResponse(
                bibliographyResource.getId().value().toString(),
                bibliographyResource.getLanguage().getLanguageName(),
                bibliographyResource.getMainData().title(),
                bibliographyResource.getMainData().subtitle(),
                bibliographyResource.getContentData().description(),
                bibliographyResource.getResourceType(),
                bibliographyResource.getEdition(),
                bibliographyResource.getPublicationData().date().toString(),
                bibliographyResource.getStatus().name(),
                physicalResourceState,
                physicalResourceFormat,
                physicalResourceShelfLocation,
                isbn,
                authorNames);
    }
}