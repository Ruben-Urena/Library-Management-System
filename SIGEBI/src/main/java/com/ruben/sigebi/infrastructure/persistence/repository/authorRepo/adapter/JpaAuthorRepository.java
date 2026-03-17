package com.ruben.sigebi.infrastructure.persistence.repository.authorRepo.adapter;

import com.ruben.sigebi.domain.author.entity.Author;
import com.ruben.sigebi.domain.author.repository.AuthorRepository;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.AuthorId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceID;
import com.ruben.sigebi.infrastructure.persistence.entity.author.AuthorEntity;
import com.ruben.sigebi.infrastructure.persistence.mapper.AuthorMapper;
import com.ruben.sigebi.infrastructure.persistence.mapper.BookMapper;
import com.ruben.sigebi.infrastructure.persistence.repository.authorRepo.SpringDataAuthorRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Repository
public class JpaAuthorRepository implements AuthorRepository {

    private final SpringDataAuthorRepository repository;


    public JpaAuthorRepository(SpringDataAuthorRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Author author) {

        AuthorEntity entity = AuthorMapper.toEntity(author);
        repository.save(entity);
    }

    @Override
    public Optional<Author> findById(AuthorId authorId) {

        return repository.findById(authorId.value())
                .map(AuthorMapper::toDomain);
    }

    @Override
    public Optional<Author> resource(ResourceID id) {
        return repository.resource(id.value().toString())
                .map(AuthorMapper::toDomain);
    }

    @Override
    public Set<Author> findAll() {

        return repository.findAll()
                .stream()
                .map(AuthorMapper::toDomain)
                .collect(Collectors.toSet());
    }
}