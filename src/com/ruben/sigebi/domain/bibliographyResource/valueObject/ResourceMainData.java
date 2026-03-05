package com.ruben.sigebi.domain.bibliographyResource.valueObject;
import com.ruben.sigebi.domain.common.exception.InvalidationException;
import com.ruben.sigebi.domain.author.entity.Author;

public record ResourceMainData(String title, String subtitle,AuthorId authorId, String edition) {
    public ResourceMainData {
        if(title == null || title.isBlank()){
            throw new InvalidationException("Title cannot be null or blank.");
        }
        if(authorId == null ){
            throw new InvalidationException("Author ID cannot be null.");
        }

    }

}
