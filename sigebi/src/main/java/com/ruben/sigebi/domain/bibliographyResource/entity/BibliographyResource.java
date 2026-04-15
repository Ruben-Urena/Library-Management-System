package com.ruben.sigebi.domain.bibliographyResource.entity;

import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.bibliographyResource.events.ResourceUpdated;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.*;
import com.ruben.sigebi.domain.common.exception.BusinessRuleViolationException;
import com.ruben.sigebi.domain.common.exception.InvalidationException;
import com.ruben.sigebi.domain.common.objectValue.ActivatableAggregate;
import com.ruben.sigebi.domain.common.objectValue.FullName;
import java.time.Instant;
import java.util.Arrays;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public abstract class BibliographyResource extends ActivatableAggregate {
    private final ResourceID resourceID;
    private final Language language;
    private CreditsData creditsData;
    private final ResourceMainData mainData;
    private PublicationData publicationData;
    private final String resourceType;
    private String edition;
    private ContentData contentData;



    public BibliographyResource(
            ResourceMainData mainData,
            Language language,
            String resourceType,
            Set<AuthorId> authorId,
            ResourceID resourceID){

        this.creditsData = new CreditsData(authorId, null,null);
        this.resourceID = Objects.requireNonNull(resourceID);
        this.language = Objects.requireNonNull(language);
        this.mainData = Objects.requireNonNull(mainData);
        Objects.requireNonNull(resourceType);
        if (resourceType.isBlank()){
            throw new InvalidationException("resource type cannot be blank");
        }
        this.resourceType = resourceType;
    }

    public BibliographyResource(
            ResourceID resourceID,
            Language language,
            CreditsData creditsData,
            ResourceMainData mainData,
            PublicationData publicationData,
            String resourceType,
            String edition,
            ContentData contentData){

        this.resourceID = resourceID;
        this.language = language;
        this.creditsData = creditsData;
        this.mainData = mainData;
        this.publicationData = publicationData;
        this.resourceType = resourceType;
        this.edition = edition;
        this.contentData = contentData;

    }

    public abstract String universalIdentifier();
    public void setContentData(ContentData contentData) {
        Objects.requireNonNull(contentData);
        if (this.contentData != null){
            throw new BusinessRuleViolationException("Credits data is already set");
        }
        this.contentData = contentData;
    }

    public ContentData getContentData() {
        return contentData;
    }


    public void changeEdition(String edition){
        Objects.requireNonNull(edition);
        if (edition.isBlank()){
            throw new InvalidationException("edition cannot be blank");
        }
        this.edition = edition;
    }

    public ResourceID getId() {
        return resourceID;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setPublicationData(PublicationData publicationData) {
        Objects.requireNonNull(publicationData,"Publication data cannot be null");
        if (this.publicationData != null) {
            throw new BusinessRuleViolationException("Publication data is already set");
        }
        this.publicationData = publicationData;
//        addDomainEvent(new ResourceUpdated(getId(), userId, Instant.now()));
    }

    public void setCreditsData(CreditsData creditsData) {
        this.creditsData = creditsData;
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

    public String getEdition() {
        return edition;
    }
}
