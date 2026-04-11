package com.ruben.sigebi.application.usecases.resource;
import com.ruben.sigebi.application.commands.resource.StateChangeResourceCommand;
import com.ruben.sigebi.api.dto.response.resource.StateChangeResourceResponse;
import com.ruben.sigebi.application.interfaces.UseCase;
import com.ruben.sigebi.api.mappers.ResourceMapper;
import com.ruben.sigebi.domain.bibliographyResource.entity.BibliographyResource;
import com.ruben.sigebi.domain.bibliographyResource.enums.ResourceState;
import com.ruben.sigebi.domain.common.exception.ElementNotFoundInTheDatabaseException;
import com.ruben.sigebi.domain.common.exception.InvalidStateException;
import com.ruben.sigebi.domain.roles.repository.RoleRepository;
import com.ruben.sigebi.domain.User.repository.UserRepository;
import com.ruben.sigebi.domain.bibliographyResource.entity.PhysicalResource;
import com.ruben.sigebi.domain.bibliographyResource.repository.BibliographyRepository;
import com.ruben.sigebi.domain.common.exception.DomainException;
import com.ruben.sigebi.application.service.ResourceAuthorizationService;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class ReactivatingResourceUseCase implements UseCase<StateChangeResourceResponse, StateChangeResourceCommand> {

    private final BibliographyRepository bibliographyRepository;
    private final ResourceAuthorizationService resourceAuthorizationService;

    public ReactivatingResourceUseCase(BibliographyRepository bibliographyRepository,
                                       UserRepository userRepository,
                                       RoleRepository roleRepository) {
        this.bibliographyRepository = bibliographyRepository;
        this.resourceAuthorizationService = new ResourceAuthorizationService(
                userRepository, roleRepository, bibliographyRepository);
    }

    @Override
    public StateChangeResourceResponse execute(StateChangeResourceCommand command) {
        BibliographyResource resource = bibliographyRepository.findById(command.id())
                .orElseThrow(() -> new ElementNotFoundInTheDatabaseException(
                        "Resource not found: " + command.id()));

        resourceAuthorizationService.editResource(command.userId());

        if (resource.isActive()) {
            throw new InvalidStateException("Resource is already active: " + command.id());
        }


        resource.activate();
        bibliographyRepository.save(resource);

        return new StateChangeResourceResponse(resource.getId(), ResourceState.AVAILABLE);
    }
}