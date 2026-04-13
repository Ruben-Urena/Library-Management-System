package com.ruben.sigebi.domain.roles.valueObjects;
import com.ruben.sigebi.domain.User.exeption.InvalidSpecialNameException;

public record SpecialName(String special){
    public SpecialName {
        if (special == null || special.isBlank()) {
            throw new InvalidSpecialNameException(" cannot be null or blank");
        }

        if (!special.matches("^[A-Za-z0-9_-]+$")){
            throw new InvalidSpecialNameException(" may only contain letters, numbers, '_' and '-'.");
        }
    }
}
