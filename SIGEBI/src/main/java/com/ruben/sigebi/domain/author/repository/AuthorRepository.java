package com.ruben.sigebi.domain.author.repository;

import com.ruben.sigebi.domain.author.entity.Author;
import com.ruben.sigebi.domain.author.valueObjects.AuthorId;
import com.ruben.sigebi.domain.common.objectValue.FullName;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface AuthorRepository {
    Optional<Author> findById(AuthorId id);
    void save(Author author);
    List<Author> findByFullName(FullName fullName);
}