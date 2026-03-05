package com.ruben.sigebi.domain.bibliographyResource.valueObject;
import com.ruben.sigebi.domain.common.exception.InvalidationException;
import com.ruben.sigebi.domain.author.entity.Author;

public record ResourceMainData(String title, String subtitle, Author author, String edition) {
    public ResourceMainData {
        if(title == null || title.isBlank()){
            throw new InvalidationException("Title cannot be null or blank.");
        }
        if(author == null ){
            throw new InvalidationException("Author cannot be null or blank.");
        }
        if (edition != null && edition.isBlank()){
            throw new InvalidationException("edition cannot be blank.");
        }

    }

}
