package com.ruben.sigebi.domain.bibliographyResource.entity;
import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.*;
import com.ruben.sigebi.domain.common.exception.*;

import java.util.Objects;
import java.util.Set;

public abstract class PhysicalResource extends BibliographyResource{

    private PhysicalData physicalData;

    public PhysicalResource(ResourceMainData mainData, Language language, String resourceType, Set<AuthorId> authorId, ResourceID resourceID, PhysicalData physicalData) {
        super(mainData, language, resourceType, authorId, resourceID);
        this.physicalData = physicalData;
    }

    public PhysicalResource(ResourceID resourceID, Language language, CreditsData creditsData, ResourceMainData mainData, PublicationData publicationData, String resourceType, String edition, ContentData contentData, PhysicalData physicalData) {
        super(resourceID, language, creditsData, mainData, publicationData, resourceType, edition, contentData);
        this.physicalData = physicalData;
    }

    public PhysicalData getPhysicalData() {
        return physicalData;
    }

    public void setPhysicalData(PhysicalData physicalData) {
        Objects.requireNonNull(physicalData);
        if (this.physicalData != null){
            throw  new BusinessRuleViolationException("Physical data is already set");
        }
        this.physicalData = physicalData;
//        addDomainEvent(new NormalPhysicalResourceUpdate(this.getId(),userId, Instant.now()));
    }

    public void changeShelfLocation(String newShelfLocation, UserId userId) {
        if (this.physicalData != null){
            this.physicalData = new PhysicalData(physicalData.physicalFormat(), newShelfLocation);
        }
        this.physicalData = new PhysicalData(null, newShelfLocation);
//        addDomainEvent(new NormalPhysicalResourceUpdate(this.getId(),userId, Instant.now()));
    }


}

