package com.ruben.sigebi.domain.bibliographyResource.valueObject;

import com.ruben.sigebi.domain.common.exception.InvalidationException;

import java.util.List;

public record ContentData(String description, List<String> subjectsList){
    public ContentData{
        if (description.isBlank()){
            throw new InvalidationException("description cannot be blank.");
        }
    }
}
