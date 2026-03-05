package com.ruben.sigebi.domain.author.entity;
import com.ruben.sigebi.domain.common.exception.InvalidFullNameException;
import com.ruben.sigebi.domain.common.objectValue.FullName;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.AuthorId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceMainData;

import java.util.Objects;
import java.util.UUID;

public class Author {
    private FullName fullName;
    private final AuthorId authorId;
    public Author(FullName fullName) {
        notFullNameNull(fullName);
        this.fullName = fullName;
        this.authorId = new AuthorId(UUID.randomUUID());
    }
    private void notFullNameNull(FullName fullName){
        if (fullName == null){
            throw new InvalidFullNameException("Full name cannot be null.");
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

    public boolean isAuthorOf(ResourceMainData resourceMainData){
        Objects.requireNonNull(resourceMainData, "Resource cannot be null.");
        return resourceMainData.author().authorId.value().equals(this.authorId.value());
    }

}
