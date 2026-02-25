package Sigebi.domain.valueObject.resource;

import Sigebi.domain.common.exception.InValidationException;

public record ContentData(String description, SubjectsList subjectsList){
    public ContentData{
        if (description.isBlank()){
            throw new InValidationException("description cannot be blank.");
        }
    }
}
