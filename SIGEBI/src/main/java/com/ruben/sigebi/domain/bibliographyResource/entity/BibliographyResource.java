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


    public BibliographyResource(ResourceMainData mainData, Language language, String resourceType, Set<AuthorId> authorId){
        this.creditsData = new CreditsData(authorId, null,null);
        this.resourceID = new ResourceID(UUID.randomUUID());

        this.language = Objects.requireNonNull(language);
        this.mainData = Objects.requireNonNull(mainData);
        Objects.requireNonNull(resourceType);
        if (resourceType.isBlank()){
            throw new InvalidationException("resource type cannot be blank");
        }
        this.resourceType = resourceType;

    }
    public BibliographyResource(ResourceMainData mainData, Language language, String resourceType, Set<AuthorId> authorId, ResourceID resourceID){
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




    public ContentData getContentData() {
        return contentData;
    }

    // En BibliographyRepository o PhysicalResource
    public BibliographyResource(
            ResourceID resourceID,
            Language language,
            ResourceMainData mainData,
            String resourceType,
            CreditsData creditsData,
            PublicationData publicationData
    ) {

        this.resourceID = resourceID;
        this.language = language;
        this.mainData = mainData;
        this.resourceType = resourceType;
        this.creditsData = creditsData;
        this.publicationData = publicationData;

    }
    public abstract String universalIdentifier();

    public void setContentData(ContentData contentData) {
        Objects.requireNonNull(contentData);
        if (this.contentData != null){
            throw new BusinessRuleViolationException("Credits data is already set");
        }
        this.contentData = contentData;
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

    public void setCreditsData(Set<FullName> contributors, Set<String> publisher) {
//        Objects.requireNonNull(creditsData,"Credits data cannot be null");
        if (this.creditsData.publisher() != null){
            throw new BusinessRuleViolationException("publisher data is already set");
        }
        if (this.creditsData.contributors() != null){
            throw new BusinessRuleViolationException("contributors data is already set");
        }
        this.creditsData = new CreditsData(
                this.creditsData.authorsIds(),
                contributors,
                publisher
        );
//        addDomainEvent(new ResourceUpdated(getId(), userId, Instant.now()));
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
