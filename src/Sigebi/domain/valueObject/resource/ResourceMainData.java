package Sigebi.domain.valueObject.resource;
import Sigebi.domain.common.exception.InValidationException;
import Sigebi.domain.model.Author;

public record ResourceMainData(String title, String subtitle, Author author, String edition, ContentData contentData) {
    public ResourceMainData {
        if(title == null || title.isBlank()){
            throw new InValidationException("Title cannot be null or blank.");
        }
        if(author == null ){
            throw new InValidationException("Author cannot be null or blank.");
        }
        if (edition.isBlank()){
            throw new InValidationException("edition cannot be blank.");
        }

    }

}
