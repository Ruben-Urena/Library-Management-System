package com.ruben.sigebi.api.mappers;
import com.ruben.sigebi.api.dto.response.resource.GetAllResourceResponse;
import com.ruben.sigebi.application.commands.resource.AddResourceCommand;
import com.ruben.sigebi.application.commands.resource.LoanResourceCommand;
import com.ruben.sigebi.application.commands.resource.StateChangeResourceCommand;
import com.ruben.sigebi.api.dto.request.resource.CreateResourceRequest;
import com.ruben.sigebi.api.dto.request.loan.LoanResourceRequest;
import com.ruben.sigebi.api.dto.request.resource.StateChangeResourceRequest;
import com.ruben.sigebi.api.dto.response.resource.AddResourceResponse;
import com.ruben.sigebi.api.dto.response.resource.LoanResourceResponse;
import com.ruben.sigebi.api.dto.response.resource.StateChangeResourceResponse;
import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.bibliographyResource.entity.BibliographyResource;
import com.ruben.sigebi.domain.bibliographyResource.entity.Book;
import com.ruben.sigebi.domain.bibliographyResource.entity.PhysicalResource;
import com.ruben.sigebi.domain.bibliographyResource.enums.ResourceState;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.AuthorId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceID;
import com.ruben.sigebi.domain.loan.entity.Loan;

import java.util.List;
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
    public static AddResourceResponse resourceToResponseAdd(BibliographyResource bibliographyResource){
        Objects.requireNonNull(bibliographyResource);
        return  AddResourceResponse.succes(
                bibliographyResource.getMainData(),
                bibliographyResource.getResourceType(),
                bibliographyResource.getId()
        );
    }
    public static GetAllResourceResponse resourceToResponseGetAll(
            BibliographyResource bibliographyResource,
            List<String> authorNames   // ← recibe los nombres ya resueltos
    ) {
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
                authorNames
        );
    }

    public static LoanResourceCommand resourceToCommand(LoanResourceRequest loanResourceRequest, UserId userId) {
        Objects.requireNonNull(loanResourceRequest);
        return  new LoanResourceCommand(
                new ResourceID(loanResourceRequest.resourceID()),
                userId
        );
    }
    public static LoanResourceResponse resourceToResponse(Loan loan) {
        Objects.requireNonNull(loan);
        return  LoanResourceResponse.susses(
                loan.getLoanID().loanID(),
                loan.getResourceId().value(),
                loan.getUserId().value(),
                loan.getDueDate().toString()
        );
        //UUID loanId, UUID resourceID, UUID userId, String returnDate
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
