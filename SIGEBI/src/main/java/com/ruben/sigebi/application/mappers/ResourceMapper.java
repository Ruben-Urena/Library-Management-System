package com.ruben.sigebi.application.mappers;
import com.ruben.sigebi.application.commands.resource.CreateResourceCommand;
import com.ruben.sigebi.application.commands.resource.LoanResourceCommand;
import com.ruben.sigebi.application.commands.resource.StateChangeResourceCommand;
import com.ruben.sigebi.application.dto.request.resource.CreateResourceRequest;
import com.ruben.sigebi.application.dto.request.resource.LoanResourceRequest;
import com.ruben.sigebi.application.dto.request.resource.StateChangeResourceRequest;
import com.ruben.sigebi.application.dto.response.resource.CreateResourceResponse;
import com.ruben.sigebi.application.dto.response.resource.LoanResourceResponse;
import com.ruben.sigebi.application.dto.response.resource.StateChangeResourceResponse;
import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.bibliographyResource.entity.BibliographyResource;
import com.ruben.sigebi.domain.bibliographyResource.enums.ResourceState;
import com.ruben.sigebi.domain.loan.entity.Loan;

import java.util.Objects;

public class ResourceMapper {
    public static CreateResourceCommand resourceToCommand(CreateResourceRequest createResourceRequest, UserId userId) {
        Objects.requireNonNull(createResourceRequest);
        return  new CreateResourceCommand(
                createResourceRequest.mainData(),
                createResourceRequest.language(),
                createResourceRequest.resourceType(),
                createResourceRequest.authorIdSet(),
                userId,
                createResourceRequest.ISBN()
        );
    }
    public static CreateResourceResponse resourceToResponse(BibliographyResource bibliographyResource){
        Objects.requireNonNull(bibliographyResource);
        return new CreateResourceResponse(
                bibliographyResource.getMainData(),
                bibliographyResource.getResourceType(),
                bibliographyResource.getId()
        );
    }
    public static LoanResourceCommand resourceToCommand(LoanResourceRequest loanResourceRequest, UserId userId) {
        Objects.requireNonNull(loanResourceRequest);
        return  new LoanResourceCommand(
                loanResourceRequest.resourceID(),
                userId
        );
    }
    public static LoanResourceResponse resourceToResponse(Loan loan) {
        Objects.requireNonNull(loan);
        return  new LoanResourceResponse(
                loan.getLoanID(),
                loan.getResourceId(),
                loan.getUserId()
        );
    }
    public static StateChangeResourceCommand stateToCommand(StateChangeResourceRequest stateChangeResourceRequest, UserId userId) {

        return new StateChangeResourceCommand(
                stateChangeResourceRequest.id(),
                stateChangeResourceRequest.resourceState(),
                userId
        );
    }

    public static StateChangeResourceResponse stateToResponse(BibliographyResource bibliographyResource, ResourceState resourceState){
        return new StateChangeResourceResponse(
                bibliographyResource.getId(),
                resourceState
        );
    }


}
