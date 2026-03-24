package com.ruben.sigebi.application.usecases.resource;
import com.ruben.sigebi.application.commands.resource.AddAuthorCommand;
import com.ruben.sigebi.application.commands.resource.AddResourceCommand;
import com.ruben.sigebi.api.dto.response.resource.AddResourceResponse;
import com.ruben.sigebi.application.interfaces.UseCase;
import com.ruben.sigebi.domain.author.entity.Author;
import com.ruben.sigebi.domain.author.repository.AuthorRepository;
import com.ruben.sigebi.domain.bibliographyResource.entity.BibliographyResource;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.AuthorId;
import com.ruben.sigebi.domain.common.objectValue.FullName;
import com.ruben.sigebi.domain.roles.repository.RoleRepository;
import com.ruben.sigebi.domain.User.repository.UserRepository;
import com.ruben.sigebi.domain.bibliographyResource.factory.ResourceFactory;
import com.ruben.sigebi.domain.bibliographyResource.repository.BibliographyRepository;
import com.ruben.sigebi.application.service.ResourceAuthorizationService;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Service
public class AddResourceUseCase implements UseCase<AddResourceResponse, AddResourceCommand>{

    private final BibliographyRepository  bibliographyRepository;
    private final UserRepository userRepository;
    private final ResourceAuthorizationService resourceAuthorizationService;
    private final ResourceFactory resourceFactory;
    private final AuthorRepository authorRepository;

    public AddResourceUseCase(BibliographyRepository bibliographyRepository, UserRepository userRepository, RoleRepository roleRepository, ResourceFactory resourceFactory, AuthorRepository authorRepository) {
        this.bibliographyRepository = bibliographyRepository;
        this.userRepository = userRepository;
        this.resourceFactory = resourceFactory;
        this.authorRepository = authorRepository;
        resourceAuthorizationService = new ResourceAuthorizationService(userRepository, roleRepository,bibliographyRepository);
    }


    @Override
    public AddResourceResponse execute(AddResourceCommand commandRequest){
        BibliographyResource _resource;
        Set<AuthorId> authorIdSet = new HashSet<>();
        Set<AddAuthorCommand> authors = new HashSet<>(commandRequest.authors());
        for (var x : authors){
            var authorOptionals = authorRepository.findByFullName(x.fullName());
            if (authorOptionals.isPresent()) {
                authorIdSet.add(authorOptionals.get().getAuthorId());
            }else {
                var newAuthor = new Author(new AuthorId(UUID.randomUUID()), new FullName(x.fullName().name(),x.fullName().lastName()));
                authorIdSet.add(newAuthor.getAuthorId());
                authorRepository.save(newAuthor);
            }
        }

        var listOfResources = resourceFactory.create(commandRequest,authorIdSet);
        _resource = listOfResources.getFirst();

        for (var  resource : listOfResources) {
            bibliographyRepository.save(resource);
        }

        return AddResourceResponse.succes(

                _resource.getMainData(),
                _resource.getResourceType(),
                _resource.getId()
        );
    }
}
