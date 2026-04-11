package com.ruben.sigebi.domain.bibliographyResource.interfaces;

import com.ruben.sigebi.domain.bibliographyResource.entity.BibliographyResource;

public interface Prototype<T extends BibliographyResource> {
    public T copy();
}
