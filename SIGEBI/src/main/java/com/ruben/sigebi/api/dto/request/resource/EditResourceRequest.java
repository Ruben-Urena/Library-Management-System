package com.ruben.sigebi.api.dto.request.resource;

import com.ruben.sigebi.domain.bibliographyResource.valueObject.CreditsData;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.PhysicalData;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.PublicationData;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceID;

public record EditResourceRequest(ResourceID resourceID, PublicationData publicationData, CreditsData creditsData, PhysicalData physicalData, String shelfLocation) {
}
