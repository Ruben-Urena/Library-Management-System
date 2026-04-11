package com.ruben.sigebi.infrastructure.persistence.mapper;

import com.ruben.sigebi.domain.bibliographyResource.entity.ResourceCopy;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceCopyId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceID;
import com.ruben.sigebi.infrastructure.persistence.entity.bibliographyResource.PhysicalResourceEntity;
import com.ruben.sigebi.infrastructure.persistence.entity.bibliographyResource.ResourceCopyEntity;

public class ResourceCopyMapper {
    public static ResourceCopyEntity toEntity(ResourceCopy domain, PhysicalResourceEntity physicalResourceEntity) {
       var entity =  new ResourceCopyEntity();
       entity.setId(domain.getId().value());
       entity.setStatus(domain.getStatus());
       entity.setState(domain.getState());
       entity.setAcquisitionDate(domain.getAcquisitionDate());
       entity.setPhysicalResource(physicalResourceEntity);
       return entity;
    }


    public static ResourceCopy toDomain(ResourceCopyEntity entity){
        return new ResourceCopy(
                new ResourceCopyId(entity.getId()),
                entity.getState(),
                new ResourceID(entity.getId()),
                entity.getAcquisitionDate()
                );
    }
}
