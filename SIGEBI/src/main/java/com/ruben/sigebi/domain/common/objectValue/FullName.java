package com.ruben.sigebi.domain.common.objectValue;

import com.ruben.sigebi.domain.common.exception.InvalidFieldException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

@Embeddable
public record FullName(
        @Column(name = "first_name")
        String name, String lastName){
    public static void main(String[] args) {
        var a = new FullName("5a","dd#");
        System.out.println(a);
    }
        public  FullName{
        if(name == null  || name.isBlank()){
           throw new InvalidFieldException("firstName cannot be null or blank");
        }
        if(lastName == null || lastName.isBlank()){
            throw new InvalidFieldException("Last firstName cannot be null or blank");
        }
        if(name.length() > 50){
            throw new InvalidFieldException("Name cannot exceed 50 characters.");
        }
    }

}


