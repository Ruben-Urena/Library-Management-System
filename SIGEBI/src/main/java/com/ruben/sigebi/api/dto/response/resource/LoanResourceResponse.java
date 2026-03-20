package com.ruben.sigebi.api.dto.response.resource;

import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceID;
import com.ruben.sigebi.domain.loan.valueObjects.LoanId;

import java.util.UUID;

public record LoanResourceResponse(
        UUID loanId,
        UUID resourceID,
        UUID userId,
        boolean susses,
        String message,
        String returnDate
) {
    public static LoanResourceResponse susses(UUID loanId, UUID resourceID, UUID userId, String returnDate){
        return new LoanResourceResponse(
                loanId,
                resourceID,
                userId,
                true,
                "Loan created",
                returnDate
        );
    }
    public static LoanResourceResponse failure(String message){
        return new LoanResourceResponse(
                null,
                null,
                null,
                false,
                message,
                null);

    }
}
