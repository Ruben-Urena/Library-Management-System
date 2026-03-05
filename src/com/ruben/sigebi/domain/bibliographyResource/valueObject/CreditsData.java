package com.ruben.sigebi.domain.bibliographyResource.valueObject;
import com.ruben.sigebi.domain.author.entity.Author;
import java.util.List;

public record CreditsData(List<Author> authors, List<String> contributors, List<String> publisher){
}
