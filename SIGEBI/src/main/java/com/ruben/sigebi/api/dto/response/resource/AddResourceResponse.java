package com.ruben.sigebi.api.dto.response.resource;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceID;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceMainData;

public record AddResourceResponse(
        ResourceMainData mainData,
        String resourceType,
        ResourceID resourceID,
        int copiesCreated,
        boolean success,
        String message
) {
    public static AddResourceResponse success(ResourceMainData mainData, String resourceType,
                                              ResourceID resourceID, int copiesCreated) {
        return new AddResourceResponse(
                mainData, resourceType, resourceID, copiesCreated,
                true, copiesCreated + " cop(ies) of the resource created successfully."
        );
    }

    public static AddResourceResponse failure(String message) {
        return new AddResourceResponse(null, null, null, 0, false, message);
    }
}
