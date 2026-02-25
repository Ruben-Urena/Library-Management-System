package Sigebi.domain.common.objectValue;

import Sigebi.domain.common.exception.InvalidFullNameException;

public record FullName(String name, String lastName){
        public  FullName{
        if(name == null  || name.isBlank()){
           throw new InvalidFullNameException("name cannot be null or blank");
        }
        if(lastName == null || lastName.isBlank()){
            throw new InvalidFullNameException("Last name cannot be null or blank");
        }
        if(name.length() > 50){
            throw new InvalidFullNameException("Name cannot exceed 50 characters.");
        }
    }

}


