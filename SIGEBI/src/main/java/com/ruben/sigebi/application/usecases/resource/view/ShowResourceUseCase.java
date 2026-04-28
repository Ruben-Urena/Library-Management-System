package com.ruben.sigebi.application.usecases.resource.view;

import com.ruben.sigebi.api.dto.request.resource.GetAllResourceRequest;
import com.ruben.sigebi.api.dto.response.resource.GetAllResourceResponse;
import com.ruben.sigebi.api.dto.response.resource.GetOneResourceResponse;
import com.ruben.sigebi.api.mappers.ResourceMapper;
import com.ruben.sigebi.application.commands.resource.GetOneResourceCommand;
import com.ruben.sigebi.application.exceptions.ForbiddenException;
import com.ruben.sigebi.application.interfaces.UseCase;
import com.ruben.sigebi.domain.author.entity.Author;
import com.ruben.sigebi.domain.author.repository.AuthorRepository;
import com.ruben.sigebi.domain.bibliographyResource.entity.BibliographyResource;
import com.ruben.sigebi.domain.bibliographyResource.entity.Book;
import com.ruben.sigebi.domain.bibliographyResource.entity.PhysicalResource;
import com.ruben.sigebi.domain.bibliographyResource.repository.BibliographyRepository;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.AuthorId;
import jakarta.validation.ValidationException;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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

    public GetOneResourceResponse execute(GetOneResourceCommand command){
        var authorCommand = command.author();
        Set<AuthorId> authorIdSet = new HashSet<>();
        Set<Author> authors = new HashSet<>();
        //if ot is the last item of author command apply the Forbidden Exception.
        for(var x : authorCommand){
            var authorOptional = authorRepository.findByFullName(x.fullName());

            if (authorOptional.isEmpty() && !(authorCommand.iterator().hasNext()) ){
                return GetOneResourceResponse.fail("Resource not found: author does not exist");
            }
            if (authorOptional.isPresent()){
                authorIdSet.add(authorOptional.get().getAuthorId());
                authors.add(authorOptional.get());
            }
        }
        var resource = bibliographyRepository.findByTitleAndAuthorId(command.resourceMainData(), authorIdSet);

        if (resource.isEmpty()){
            return GetOneResourceResponse.fail("Resource not found: Title does not exist");
        }

        return GetOneResourceResponse.susses(
                resource.get().getMainData().title(),
                authors
                        .stream()
                        .map( a -> a.getFullName().name()+a.getFullName().lastName())
                        .collect(Collectors.toSet())
        );
    }

    private GetAllResourceResponse toResponse(BibliographyResource bibliographyResource) {
        List<String> authorNames = bibliographyResource.getCreditsData().authorsIds()
                .stream()
                .map(authorId -> authorRepository.findById(authorId)
                        .map(a -> a.getFullName().name() + " " + a.getFullName().lastName())
                        .orElse("Unknown")
                )
                .toList();


        String physicalResourceFormat = null;
        String physicalResourceShelfLocation= null;

        if (bibliographyResource instanceof PhysicalResource a) {

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
                physicalResourceFormat,
                physicalResourceShelfLocation,
                isbn,
                authorNames);
    }
}