package com.ruben.sigebi.application.dto.request.resource;
import com.ruben.sigebi.domain.bibliographyResource.enums.ResourceState;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceID;

public record StateChangeResourceRequest(ResourceID id, ResourceState resourceState){

}
