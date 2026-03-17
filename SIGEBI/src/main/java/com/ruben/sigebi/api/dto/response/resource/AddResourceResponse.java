package com.ruben.sigebi.api.dto.response.resource;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceID;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceMainData;

public record AddResourceResponse(ResourceMainData mainData, String resourceType, ResourceID resourceID, boolean susses, String message){
    public static AddResourceResponse succes(
            ResourceMainData mainData,
            String resourceType,
            ResourceID resourceID
    ){
        return new AddResourceResponse(
                mainData,
                resourceType,
                resourceID,
                true,
                "Resource Added Successfully!"
        );

    }
    public static AddResourceResponse failure(String message){
        return new AddResourceResponse(
                null,
                null,
                null,
                false,
                message
        );
    }

}
