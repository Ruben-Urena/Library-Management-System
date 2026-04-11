package com.ruben.sigebi.domain.bibliographyResource.interfaces;

import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceCopyId;

public interface Copiable {
     void addCopies(Integer value);
     Integer getQuantity();
     void eliminateCopies(Integer  value);

}
