package com.ruben.sigebi.api.dto.request.resource;

import com.ruben.sigebi.domain.bibliographyResource.entity.BibliographyResource;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceID;

public record LoanResourceRequest(ResourceID resourceID) {
}
