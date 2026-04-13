package com.ruben.sigebi.api.dto.request.loan;

import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceID;

import java.util.UUID;

public record LoanResourceRequest(UUID resourceID) {
}
