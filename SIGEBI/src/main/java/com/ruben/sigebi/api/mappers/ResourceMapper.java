package com.ruben.sigebi.api.mappers;
import com.ruben.sigebi.application.commands.resource.AddResourceCommand;
import com.ruben.sigebi.application.commands.resource.LoanResourceCommand;
import com.ruben.sigebi.application.commands.resource.StateChangeResourceCommand;
import com.ruben.sigebi.api.dto.request.resource.CreateResourceRequest;
import com.ruben.sigebi.api.dto.request.resource.LoanResourceRequest;
import com.ruben.sigebi.api.dto.request.resource.StateChangeResourceRequest;
import com.ruben.sigebi.api.dto.response.resource.AddResourceResponse;
import com.ruben.sigebi.api.dto.response.resource.LoanResourceResponse;
import com.ruben.sigebi.api.dto.response.resource.StateChangeResourceResponse;
import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.bibliographyResource.entity.BibliographyResource;
import com.ruben.sigebi.domain.bibliographyResource.enums.ResourceState;
import com.ruben.sigebi.domain.loan.entity.Loan;

import java.util.Objects;

public class ResourceMapper {
    public static AddResourceCommand resourceToCommand(CreateResourceRequest createResourceRequest, UserId userId) {
        Objects.requireNonNull(createResourceRequest);
        return  new AddResourceCommand(
                createResourceRequest.mainData(),
                createResourceRequest.language(),
                createResourceRequest.resourceType(),
                createResourceRequest.authorIdSet(),
                userId,
                createResourceRequest.ISBN(),
                createResourceRequest.quantity()
        );
    }
    public static AddResourceResponse resourceToResponse(BibliographyResource bibliographyResource){
        Objects.requireNonNull(bibliographyResource);
        return  AddResourceResponse.succes(
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
        return  LoanResourceResponse.susses(
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
