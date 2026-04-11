package com.ruben.sigebi.domain.bibliographyResource.repository;

import com.ruben.sigebi.domain.bibliographyResource.entity.ResourceCopy;
import com.ruben.sigebi.domain.bibliographyResource.enums.ResourceState;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceCopyId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceID;
import com.ruben.sigebi.domain.common.enums.Status;

import java.util.List;
import java.util.Optional;

public interface ResourceCopyRepository {
    void save(ResourceCopy copy);

    Optional<ResourceCopy> findById(ResourceCopyId id);

    List<ResourceCopy> findByResourceId(ResourceID physicalResourceId);

    List<ResourceCopy> findByResourceIdAndStatus(ResourceID physicalResourceId, Status status);

    List<ResourceCopy> findByResourceIdStatusAndState(ResourceID physicalResourceId, Status status, ResourceState state);

    Optional<ResourceCopy> findFirstAvailable(ResourceID physicalResourceId);

    long countAvailable(ResourceID physicalResourceId);

    long countByState(ResourceID physicalResourceId, ResourceState state);

    boolean isAvailable(ResourceCopyId copyId);
}
