package com.ruben.sigebi.application.dto.response.resource;

import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceID;

public record RemoveResourceResponse(ResourceID resourceID, Boolean success){

}
