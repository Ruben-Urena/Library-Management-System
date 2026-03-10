package com.ruben.sigebi.application.dto.response.resource;
import com.ruben.sigebi.domain.bibliographyResource.enums.ResourceState;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceID;

public record StateChangeResourceResponse(ResourceID id, ResourceState resourceState) {

}
