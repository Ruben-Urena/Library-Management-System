package com.ruben.sigebi.domain.bibliographyResource.valueObject;

import com.ruben.sigebi.domain.common.exception.InvalidationException;

public record ContentData(String description, SubjectsList subjectsList){
    public ContentData{
        if (description.isBlank()){
            throw new InvalidationException("description cannot be blank.");
        }
    }
}
