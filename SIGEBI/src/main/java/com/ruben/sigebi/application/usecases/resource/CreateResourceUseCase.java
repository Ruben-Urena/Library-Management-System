package com.ruben.sigebi.application.usecases.resource;
import com.ruben.sigebi.application.commands.resource.CreateResourceCommand;
import com.ruben.sigebi.application.dto.response.resource.CreateResourceResponse;
import com.ruben.sigebi.application.interfaces.UseCase;
import com.ruben.sigebi.application.mappers.ResourceMapper;
import com.ruben.sigebi.domain.User.entity.User;
import com.ruben.sigebi.domain.User.repository.RoleRepository;
import com.ruben.sigebi.domain.User.repository.UserRepository;
import com.ruben.sigebi.domain.bibliographyResource.entity.BibliographyResource;
import com.ruben.sigebi.domain.bibliographyResource.entity.PhysicalResource;
import com.ruben.sigebi.domain.bibliographyResource.factory.ResourceFactory;
import com.ruben.sigebi.domain.bibliographyResource.repository.BibliographyRepository;
import com.ruben.sigebi.domain.service.ResourceAuthorizationService;
import java.util.Optional;

public class CreateResourceUseCase implements UseCase<CreateResourceResponse, CreateResourceCommand>{

    private final BibliographyRepository  bibliographyRepository;
    private final UserRepository userRepository;
    private final ResourceAuthorizationService resourceAuthorizationService;
    private final ResourceFactory resourceFactory;


    public CreateResourceUseCase(BibliographyRepository bibliographyRepository, UserRepository userRepository, RoleRepository roleRepository, ResourceFactory resourceFactory) {
        this.bibliographyRepository = bibliographyRepository;
        this.userRepository = userRepository;
        this.resourceFactory = resourceFactory;
        resourceAuthorizationService = new ResourceAuthorizationService(userRepository, roleRepository,bibliographyRepository);
    }


    @Override
    public CreateResourceResponse execute(CreateResourceCommand commandRequest) {
        Optional<User> user = userRepository.findById(commandRequest.userId());
        if(user.isEmpty()){
            throw new RuntimeException("user not found");
        }

        resourceAuthorizationService.addResource(user.get().getUserId());
        BibliographyResource bibliographyResource = resourceFactory.create(commandRequest);
        bibliographyRepository.save(bibliographyResource);
        return ResourceMapper.resourceToResponse(bibliographyResource);

    }
}
