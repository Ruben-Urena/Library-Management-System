package com.ruben.sigebi.api.dto.request.resource;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.AuthorId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.Language;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceMainData;
import org.hibernate.validator.constraints.ISBN;

import java.util.Set;

public record CreateResourceRequest(ResourceMainData mainData,
                                    Language language,
                                    String resourceType,
                                    Set<AuthorId> authorIdSet,
                                    @ISBN
                                    String ISBN,
                                    int quantity){

}
