package com.ruben.sigebi.domain.author.repository;

import com.ruben.sigebi.domain.author.entity.Author;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.AuthorId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceID;
import com.ruben.sigebi.domain.common.interfaces.DomainRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AuthorRepository {
    Optional<Author> findById(AuthorId id);
    void save(Author author);
    Set<Author> findAll();
}