package com.ruben.sigebi.application.usecases.resource;
import com.ruben.sigebi.application.commands.resource.StateChangeResourceCommand;
import com.ruben.sigebi.application.dto.response.resource.StateChangeResourceResponse;
import com.ruben.sigebi.application.interfaces.UseCase;
import com.ruben.sigebi.application.mappers.ResourceMapper;
import com.ruben.sigebi.domain.User.repository.RoleRepository;
import com.ruben.sigebi.domain.User.repository.UserRepository;
import com.ruben.sigebi.domain.bibliographyResource.entity.PhysicalResource;
import com.ruben.sigebi.domain.bibliographyResource.repository.BibliographyRepository;
import com.ruben.sigebi.domain.common.exception.DomainException;
import com.ruben.sigebi.domain.service.ResourceAuthorizationService;

import java.util.Optional;

public class PublishResourceUseCase implements UseCase<StateChangeResourceResponse, StateChangeResourceCommand> {
    private final BibliographyRepository bibliographyRepository;
    private final ResourceAuthorizationService resourceAuthorizationService;

    public PublishResourceUseCase(BibliographyRepository bibliographyRepository, UserRepository userRepository, RoleRepository roleRepository) {
        this.bibliographyRepository = bibliographyRepository;
        resourceAuthorizationService = new ResourceAuthorizationService(userRepository,roleRepository,bibliographyRepository);
    }

    @Override
    public StateChangeResourceResponse execute(StateChangeResourceCommand commandRequest) {

        Optional<BibliographyRepository> bibliographyResource = bibliographyRepository.findById(commandRequest.id());
        if (bibliographyResource.isEmpty()) {
            throw new RuntimeException();
        }
        resourceAuthorizationService.editResource(commandRequest.userId());
        if (!(bibliographyResource.get() instanceof PhysicalResource a)) {
            throw new DomainException("Resource cannot be published.");
        }
        a.publish(commandRequest.userId());

        bibliographyRepository.save(a);

        return ResourceMapper.stateToResponse(a, a.getState());
    }
}