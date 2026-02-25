package Sigebi.domain.valueObject.resource;
import Sigebi.domain.model.Author;
import java.util.List;

public record CreditsData(List<Author> authors, List<String> contributors, List<String> publisher){
}
