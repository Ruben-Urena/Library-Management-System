package com.ruben.sigebi.infrastructure.persistence.entity.bibliographyResource;

import com.ruben.sigebi.domain.bibliographyResource.enums.ResourceState;
import com.ruben.sigebi.infrastructure.persistence.entity.bibliographyResource.embed.PhysicalDataEmbeddable;
import jakarta.persistence.*;

@Entity
@Table(name = "physical_resources")
public abstract class PhysicalResourceEntity extends BibliographyResourceEntity {

    @Enumerated(EnumType.STRING)
    private ResourceState state;

    @Embedded
    private PhysicalDataEmbeddable physicalData;

    public PhysicalResourceEntity() {
    }

    public ResourceState getState() {
        return state;
    }

    public void setState(ResourceState state) {
        this.state = state;
    }

    public PhysicalDataEmbeddable getPhysicalData() {
        return physicalData;
    }

    public void setPhysicalData(PhysicalDataEmbeddable physicalData) {
        this.physicalData = physicalData;
    }
}