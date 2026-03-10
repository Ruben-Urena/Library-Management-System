package com.ruben.sigebi.application.dto.response.resource;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.AuthorId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.Language;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceID;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceMainData;

public record CreateResourceResponse(ResourceMainData mainData, String resourceType, ResourceID resourceID){

}
