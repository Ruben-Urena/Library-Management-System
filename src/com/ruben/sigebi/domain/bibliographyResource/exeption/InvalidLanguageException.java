package com.ruben.sigebi.domain.bibliographyResource.exeption;
import com.ruben.sigebi.domain.common.exception.InvalidationException;

public class InvalidLanguageException extends InvalidationException {
    public InvalidLanguageException(String message) {
        super(message);
    }

}
