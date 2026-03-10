package com.ruben.sigebi.domain.bibliographyResource.valueObject;
import com.ruben.sigebi.domain.author.entity.Author;
import com.ruben.sigebi.domain.common.objectValue.FullName;

import java.util.List;
import java.util.Set;

public record CreditsData(Set<AuthorId> authorsIds, List<FullName> contributors, List<String> publisher){

}
