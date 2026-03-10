package com.ruben.sigebi.domain.author.repository;

import com.ruben.sigebi.domain.author.entity.Author;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.AuthorId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceID;
import com.ruben.sigebi.domain.common.interfaces.DomainRepository;

import java.util.List;
import java.util.Optional;

public interface AuthorRepository extends DomainRepository<Author, AuthorId> {
    @Override
    void save(Author author);

    @Override
    Optional<Author> findById(AuthorId id);

    Optional<Author> findBySourceId(ResourceID is);
    List<Author> findAllBySourceId(ResourceID is);

}