package com.ruben.sigebi.domain.author.repository;

import com.ruben.sigebi.domain.author.entity.Author;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.AuthorId;
import java.util.Optional;

public interface AuthorRepository {
    void save(Author author);
    Optional<Author> findById(AuthorId id);
}