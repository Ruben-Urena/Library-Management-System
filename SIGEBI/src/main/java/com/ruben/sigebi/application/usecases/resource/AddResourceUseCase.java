package com.ruben.sigebi.application.usecases.resource;
import com.ruben.sigebi.application.commands.resource.AddAuthorCommand;
import com.ruben.sigebi.application.commands.resource.AddResourceCommand;
import com.ruben.sigebi.api.dto.response.resource.AddResourceResponse;
import com.ruben.sigebi.application.interfaces.UseCase;
import com.ruben.sigebi.domain.author.entity.Author;
import com.ruben.sigebi.domain.author.repository.AuthorRepository;
import com.ruben.sigebi.domain.bibliographyResource.entity.BibliographyResource;
import com.ruben.sigebi.domain.bibliographyResource.repository.ResourceCopyRepository;
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
public class AddResourceUseCase implements UseCase<AddResourceResponse, AddResourceCommand> {

    private final BibliographyRepository bibliographyRepository;
    private final ResourceCopyRepository resourceCopyRepository;
    private final ResourceAuthorizationService resourceAuthorizationService;
    private final ResourceFactory resourceFactory;
    private final AuthorRepository authorRepository;

    public AddResourceUseCase(BibliographyRepository bibliographyRepository,
                              ResourceCopyRepository resourceCopyRepository,
                              UserRepository userRepository,
                              RoleRepository roleRepository,
                              ResourceFactory resourceFactory,
                              AuthorRepository authorRepository) {
        this.bibliographyRepository = bibliographyRepository;
        this.resourceCopyRepository = resourceCopyRepository;
        this.resourceFactory = resourceFactory;
        this.authorRepository = authorRepository;
        this.resourceAuthorizationService = new ResourceAuthorizationService(
                userRepository, roleRepository, bibliographyRepository);
    }

    @Override
    public AddResourceResponse execute(AddResourceCommand command) {


        Set<AuthorId> authorIdSet = new HashSet<>();
        for (AddAuthorCommand authorCmd : command.authors()) {
            var existing = authorRepository.findByFullName(authorCmd.fullName());
            if (existing.isPresent()) {
                authorIdSet.add(existing.get().getAuthorId());
            } else {
                var newAuthor = new Author(
                        new AuthorId(UUID.randomUUID()),
                        new FullName(authorCmd.fullName().name(), authorCmd.fullName().lastName())
                );
                authorRepository.save(newAuthor);
                authorIdSet.add(newAuthor.getAuthorId());
            }
        }


        var result = resourceFactory.create(command, authorIdSet);
        BibliographyResource resource = result.resource();


        bibliographyRepository.save(resource);


        result.copies().forEach(resourceCopyRepository::save);

        return AddResourceResponse.success(
                resource.getMainData(),
                resource.getResourceType(),
                resource.getId(),
                result.copies().size()
        );
    }
}
