package com.ruben.sigebi.domain.bibliographyResource.valueObject;
import com.ruben.sigebi.domain.common.exception.InvalidationException;


public record ResourceMainData(String title, String subtitle) {
    public ResourceMainData {
        if(title == null || title.isBlank()){
            throw new InvalidationException("Title cannot be null or blank.");
        }

        if(subtitle != null && subtitle.isBlank()){
            throw new InvalidationException("Subtitle cannot be blank.");
        }
    }

}
