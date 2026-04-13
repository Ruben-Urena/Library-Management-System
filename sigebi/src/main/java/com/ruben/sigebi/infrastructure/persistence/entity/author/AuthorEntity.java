package com.ruben.sigebi.infrastructure.persistence.entity.author;

import com.ruben.sigebi.domain.common.enums.Status;
import com.ruben.sigebi.infrastructure.persistence.entity.bibliographyResource.BibliographyResourceEntity;
import com.ruben.sigebi.infrastructure.persistence.entity.user.embed.FullNameEmbeddable;
import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "authors")
public class AuthorEntity {

    @Id
    private UUID id;
    @Embedded
    private FullNameEmbeddable fullName;

    @Enumerated(EnumType.STRING)
    private Status status;

    @ManyToMany(mappedBy = "authorsIds")
    private Set<BibliographyResourceEntity> resources;


    public AuthorEntity() {
    }


    public Set<BibliographyResourceEntity> getResources() {
        return resources;
    }

    public void setResources(Set<BibliographyResourceEntity> resources) {
        this.resources = resources;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public FullNameEmbeddable getFullName() {
        return fullName;
    }

    public void setFullName(FullNameEmbeddable fullName) {
        this.fullName = fullName;
    }

}