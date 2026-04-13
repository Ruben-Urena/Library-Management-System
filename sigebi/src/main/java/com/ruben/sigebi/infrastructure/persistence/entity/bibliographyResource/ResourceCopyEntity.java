package com.ruben.sigebi.infrastructure.persistence.entity.bibliographyResource;
import com.ruben.sigebi.domain.bibliographyResource.enums.ResourceState;
import com.ruben.sigebi.domain.common.enums.Status;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "resource_copies")
public class ResourceCopyEntity {

    @Id
    private UUID id;

    @Enumerated(EnumType.STRING)
    private ResourceState state;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "physical_resource_id", nullable = false)
    private PhysicalResourceEntity physicalResource;

    @Column(name = "acquisition_date")
    private Instant acquisitionDate;


    @Enumerated(EnumType.STRING)
    private Status status;

    public ResourceCopyEntity() {
    }

    public ResourceCopyEntity(UUID id, ResourceState state, PhysicalResourceEntity physicalResource, Instant acquisitionDate, Status status) {
        this.id = id;
        this.state = state;
        this.physicalResource = physicalResource;
        this.acquisitionDate = acquisitionDate;
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public ResourceState getState() {
        return state;
    }

    public void setState(ResourceState state) {
        this.state = state;
    }

    public PhysicalResourceEntity getPhysicalResource() {
        return physicalResource;
    }

    public void setPhysicalResource(PhysicalResourceEntity physicalResource) {
        this.physicalResource = physicalResource;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Instant getAcquisitionDate() {
        return acquisitionDate;
    }

    public void setAcquisitionDate(Instant acquisitionDate) {
        this.acquisitionDate = acquisitionDate;
    }
}
