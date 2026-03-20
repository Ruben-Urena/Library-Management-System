package com.ruben.sigebi.infrastructure.persistence.entity.bibliographyResource;

import com.ruben.sigebi.domain.common.enums.Status;
import com.ruben.sigebi.infrastructure.persistence.entity.author.AuthorEntity;
import com.ruben.sigebi.infrastructure.persistence.entity.bibliographyResource.embed.*;
import com.ruben.sigebi.infrastructure.persistence.entity.contributors.Contributors;
import com.ruben.sigebi.infrastructure.persistence.entity.publisher.Publisher;
import com.ruben.sigebi.infrastructure.persistence.entity.subject.Subject;
import jakarta.persistence.*;

import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Entity
@Table(name = "resources")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class BibliographyResourceEntity {

    @Id
    private UUID id;

    @Column(name = "publication_date")
    private Instant publicationDate;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Embedded
    private LanguageEmbeddable language;

    @Embedded
    private ResourceMainDataEmbeddable mainData;

    @ManyToMany
    @JoinTable(
            name = "resource_authors",
            joinColumns = @JoinColumn(name = "resource_id"),
            inverseJoinColumns = @JoinColumn(name = "author_id")
    )
    private Set<AuthorEntity> authorsIds = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "resource_contributors",
            joinColumns = @JoinColumn(name = "resource_id"),
            inverseJoinColumns = @JoinColumn(name = "contributor_id")
    )
    private Set<Contributors> contributors = new HashSet<>();


    @ManyToMany
    @JoinTable(
            name = "resource_publishers",
            joinColumns = @JoinColumn(name = "resource_id"),
            inverseJoinColumns = @JoinColumn(name = "publisher_id")
    )
    private Set<Publisher> publishers = new HashSet<>();

    @Column(name = "resource_type")
    private String resourceType;

    @Column(name = "edition")
    private String edition;

    @Column(name = "description")
    private String description;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Set<AuthorEntity> getAuthorsIds() {
        return authorsIds;
    }

    @ManyToMany
    @JoinTable(
            name = "resource_subjects",
            joinColumns = @JoinColumn(name = "resource_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id")
    )
    private Set<Subject> subjectsList;

    protected BibliographyResourceEntity() {}


    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Subject> getSubjectsList() {
        return subjectsList;
    }

    public void setSubjectsList(Set<Subject> subjectsList) {
        this.subjectsList = subjectsList;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LanguageEmbeddable getLanguage() {
        return language;
    }

    public void setLanguage(LanguageEmbeddable language) {
        this.language = language;
    }

    public ResourceMainDataEmbeddable getMainData() {
        return mainData;
    }

    public void setMainData(ResourceMainDataEmbeddable mainData) {
        this.mainData = mainData;
    }

    public Set<AuthorEntity> getAuthors() {
        return authorsIds;
    }

    public void setAuthorsIds(Set<AuthorEntity> authorsIds) {
        this.authorsIds = authorsIds;
    }

    public Set<Contributors> getContributors() {
        return contributors;
    }

    public void setContributors(Set<Contributors> contributors) {
        this.contributors = contributors;
    }

    public Set<Publisher> getPublishers() {
        return publishers;
    }

    public void setPublishers(Set<Publisher> publishers) {
        this.publishers = publishers;
    }

    public Instant getPublicationDate() {
        return publicationDate;
    }

    public void setPublicationDate(Instant publicationDate) {
        this.publicationDate = publicationDate;
    }

    public String getResourceType() {
        return resourceType;
    }

    public void setResourceType(String resourceType) {
        this.resourceType = resourceType;
    }

    public String getEdition() {
        return edition;
    }

    public void setEdition(String edition) {
        this.edition = edition;
    }
}