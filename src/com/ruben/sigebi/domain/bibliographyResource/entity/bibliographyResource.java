package com.ruben.sigebi.domain.bibliographyResource.entity;

import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.bibliographyResource.events.ResourceUpdated;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.*;
import com.ruben.sigebi.domain.common.exception.BusinessRuleViolationException;
import com.ruben.sigebi.domain.common.exception.InvalidationException;
import com.ruben.sigebi.domain.common.objectValue.AggregateRoot;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public abstract class bibliographyResource extends AggregateRoot {


    private final ResourceID resourceID;
    private final Language language;
    private CreditsData creditsData;
    private final ResourceMainData mainData;
    private PublicationData publicationData;
    private final String resourceType;
    private final LoanableResourceId loanableResourceId;

    public bibliographyResource(ResourceMainData mainData, Language language, String resourceType) {
        this.resourceID = new ResourceID(UUID.randomUUID());
        this.language = Objects.requireNonNull(language);
        this.mainData = Objects.requireNonNull(mainData);
        loanableResourceId = new LoanableResourceId(resourceID);
        Objects.requireNonNull(resourceType);
        if (resourceType.isBlank()){
            throw new InvalidationException("resource type cannot be blank");
        }
        this.resourceType = resourceType;
        this.creditsData = null;
        this.publicationData = null;
    }

    public LoanableResourceId getLoanableResourceId(){
        return loanableResourceId;
    }

    public ResourceID getBaseResourceID() {
        return resourceID;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setPublicationData(PublicationData publicationData, UserId userId) {
        Objects.requireNonNull(publicationData,"Publication data cannot be null");
        if (this.publicationData != null) {
            throw new BusinessRuleViolationException("Publication data is already set");
        }
        this.publicationData = publicationData;
        addDomainEvent(new ResourceUpdated(getBaseResourceID(), userId, Instant.now()));
    }

    public void setCreditsData(CreditsData creditsData, UserId userId) {
        Objects.requireNonNull(creditsData,"Credits data cannot be null");
        if (this.creditsData != null){
            throw new BusinessRuleViolationException("Credits data is already set");
        }
        this.creditsData = creditsData;
        addDomainEvent(new ResourceUpdated(getBaseResourceID(), userId, Instant.now()));
    }

    public Language getLanguage() {
        return language;
    }

    public CreditsData getCreditsData() {
        return creditsData;
    }

    public ResourceMainData getMainData() {
        return mainData;
    }

    public PublicationData getPublicationData() {
        return publicationData;
    }

}
