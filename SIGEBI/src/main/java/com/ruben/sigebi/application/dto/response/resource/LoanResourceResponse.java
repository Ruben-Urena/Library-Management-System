package com.ruben.sigebi.application.dto.response.resource;

import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceID;
import com.ruben.sigebi.domain.loan.valueObjects.LoanId;

public record LoanResourceResponse(LoanId loanId, ResourceID resourceID, UserId userId) {
}
