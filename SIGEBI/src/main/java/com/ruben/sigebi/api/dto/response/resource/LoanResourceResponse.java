package com.ruben.sigebi.api.dto.response.resource;

import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceID;
import com.ruben.sigebi.domain.loan.valueObjects.LoanId;

public record LoanResourceResponse(LoanId loanId, ResourceID resourceID, UserId userId, boolean susses, String message) {
    public static LoanResourceResponse susses(LoanId loanId, ResourceID resourceID, UserId userId){
        return new LoanResourceResponse(loanId,resourceID,userId,true,"Loan created");
    }
    public static LoanResourceResponse failure(String message){
        return new LoanResourceResponse(null,null,null,false,message);
    }
}
