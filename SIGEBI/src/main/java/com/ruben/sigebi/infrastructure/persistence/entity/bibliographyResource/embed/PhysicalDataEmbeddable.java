package com.ruben.sigebi.infrastructure.persistence.entity.bibliographyResource.embed;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public class PhysicalDataEmbeddable {

    @Column(name = "physical_format")
    private String physicalFormat;

    @Column(name = "shelf_location")
    private String shelfLocation;

    protected PhysicalDataEmbeddable() {
    }

    public PhysicalDataEmbeddable(String physicalFormat, String shelfLocation) {
        this.physicalFormat = physicalFormat;
        this.shelfLocation = shelfLocation;
    }

    public String getPhysicalFormat() {
        return physicalFormat;
    }

    public String getShelfLocation() {
        return shelfLocation;
    }

    public void setPhysicalFormat(String physicalFormat) {
        this.physicalFormat = physicalFormat;
    }

    public void setShelfLocation(String shelfLocation) {
        this.shelfLocation = shelfLocation;
    }
}