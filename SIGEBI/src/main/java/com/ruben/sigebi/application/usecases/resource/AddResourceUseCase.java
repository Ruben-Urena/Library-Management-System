package com.ruben.sigebi.application.usecases.resource;
import com.ruben.sigebi.application.commands.resource.AddResourceCommand;
import com.ruben.sigebi.api.dto.response.resource.AddResourceResponse;
import com.ruben.sigebi.application.interfaces.UseCase;
import com.ruben.sigebi.domain.User.entity.User;
import com.ruben.sigebi.domain.bibliographyResource.entity.BibliographyResource;
import com.ruben.sigebi.domain.common.exception.DomainException;
import com.ruben.sigebi.domain.common.exception.ElementNotFoundInTheDatabaseException;
import com.ruben.sigebi.domain.roles.repository.RoleRepository;
import com.ruben.sigebi.domain.User.repository.UserRepository;
import com.ruben.sigebi.domain.bibliographyResource.factory.ResourceFactory;
import com.ruben.sigebi.domain.bibliographyResource.repository.BibliographyRepository;
import com.ruben.sigebi.domain.service.ResourceAuthorizationService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AddResourceUseCase implements UseCase<AddResourceResponse, AddResourceCommand>{

    private final BibliographyRepository  bibliographyRepository;
    private final UserRepository userRepository;
    private final ResourceAuthorizationService resourceAuthorizationService;
    private final ResourceFactory resourceFactory;


    public AddResourceUseCase(BibliographyRepository bibliographyRepository, UserRepository userRepository, RoleRepository roleRepository, ResourceFactory resourceFactory) {
        this.bibliographyRepository = bibliographyRepository;
        this.userRepository = userRepository;
        this.resourceFactory = resourceFactory;
        resourceAuthorizationService = new ResourceAuthorizationService(userRepository, roleRepository,bibliographyRepository);
    }


    @Override
    public AddResourceResponse execute(AddResourceCommand commandRequest) {
        BibliographyResource _resource;

        Optional<User> user = userRepository.findById(commandRequest.userId());
        if(user.isEmpty()){
            throw new ElementNotFoundInTheDatabaseException("user not found");
        }
        try {
            resourceAuthorizationService.addResource(user.get().getUserId());

        }catch (NullPointerException|DomainException D){
            return AddResourceResponse.failure("Cannot add resource: "+D);
        }

        var listOfResources = resourceFactory.create(commandRequest);
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
