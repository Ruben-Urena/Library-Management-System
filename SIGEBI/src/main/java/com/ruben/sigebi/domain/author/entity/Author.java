package com.ruben.sigebi.domain.author.entity;
import com.ruben.sigebi.domain.bibliographyResource.entity.BibliographyResource;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceID;
import com.ruben.sigebi.domain.common.exception.InvalidFieldException;
import com.ruben.sigebi.domain.common.objectValue.ActivatableAggregate;
import com.ruben.sigebi.domain.common.objectValue.FullName;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.AuthorId;
import jakarta.persistence.Entity;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Author extends ActivatableAggregate {
    private FullName fullName;
    private final AuthorId authorId;
    private Set<ResourceID> resourceIds;
    public Author(AuthorId authorId,FullName fullName) {
        notFullNameNull(fullName);
        this.fullName = fullName;
        this.authorId = Objects.requireNonNull(authorId);
    }
    public void addResourceToAuthor(Set<ResourceID> resourceIds) {
        Objects.requireNonNull(resourceIds);
        this.resourceIds = new HashSet<>(Objects.requireNonNull(resourceIds));
    }

    public Set<ResourceID> getResourceIds() {
        return Set.copyOf(resourceIds);
    }

    private void notFullNameNull(FullName fullName){
        if (fullName == null){
            throw new InvalidFieldException("Full name cannot be null.");
        }
    }
    public FullName getFullName() {
        return fullName;
    }
    public void changeName(FullName newFullname){
        notFullNameNull(fullName);
        this.fullName = newFullname;
    }
    public AuthorId getAuthorId() {
        return authorId;
    }

    public boolean isAuthorOf(BibliographyResource resource){
        Objects.requireNonNull(resource, "Resource cannot be null.");
        return resource.getCreditsData().authorsIds().contains(getAuthorId());
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Author author = (Author) o;
        return getAuthorId().equals(author.getAuthorId());
    }

    @Override
    public int hashCode() {
        return getAuthorId().hashCode();
    }
}
