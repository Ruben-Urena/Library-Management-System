package com.ruben.sigebi.infrastructure.persistence.entity.author;

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
    private String id;

    public AuthorEntity() {
    }

    @Embedded
    private FullNameEmbeddable fullName;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public FullNameEmbeddable getFullName() {
        return fullName;
    }

    public void setFullName(FullNameEmbeddable fullName) {
        this.fullName = fullName;
    }

}