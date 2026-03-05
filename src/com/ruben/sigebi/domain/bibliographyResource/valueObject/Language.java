package com.ruben.sigebi.domain.bibliographyResource.valueObject;
import com.ruben.sigebi.domain.bibliographyResource.exeption.InvalidLanguageException;
import java.util.Arrays;
import java.util.Locale;

public record Language(String input) {
    public static String validate(String input) throws InvalidLanguageException {
        if (!(Arrays.asList(Locale.getISOLanguages())
                .contains(input.toLowerCase().trim())) ){
            throw new InvalidLanguageException("Invalid ISO language code: "+ input);
        }
        return input;
    }
}
