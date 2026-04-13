package com.ruben.sigebi.infrastructure.persistence.repository.bookRepo.adapter;

import com.ruben.sigebi.domain.bibliographyResource.entity.BibliographyResource;
import com.ruben.sigebi.domain.bibliographyResource.entity.Book;
import com.ruben.sigebi.domain.bibliographyResource.enums.ResourceState;
import com.ruben.sigebi.domain.bibliographyResource.repository.BibliographyRepository;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.AuthorId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceID;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceMainData;
import com.ruben.sigebi.domain.common.enums.Status;
import com.ruben.sigebi.infrastructure.persistence.entity.author.AuthorEntity;
import com.ruben.sigebi.infrastructure.persistence.entity.bibliographyResource.BookEntity;
import com.ruben.sigebi.infrastructure.persistence.mapper.BookMapper;
import com.ruben.sigebi.infrastructure.persistence.repository.bookRepo.SpringDataResourceRepository;
import com.ruben.sigebi.infrastructure.persistence.repository.authorRepo.SpringDataAuthorRepository;
import org.springframework.stereotype.Repository;

import java.util.*;
import java.util.stream.Collectors;

@Repository
public class JpaBookRepository implements BibliographyRepository {

    private final SpringDataResourceRepository repository;
    private final SpringDataAuthorRepository  authorRepository;
    private final BookMapper bookMapper;


    public JpaBookRepository(SpringDataResourceRepository repository, SpringDataAuthorRepository authorRepository1, BookMapper mapper) {
        this.repository = repository;
        this.authorRepository = authorRepository1;


        this.bookMapper = mapper;
    }

    @Override
    public void save(BibliographyResource resource) {
        if (!(resource instanceof Book book)) {
            throw new IllegalArgumentException("Only Book resources are supported.");
        }

        Set<AuthorEntity> authors = new HashSet<>(
                authorRepository.findAllById(
                        book.getCreditsData().authorsIds()
                                .stream()
                                .map(id -> UUID.fromString(id.value().toString()))
                                .toList()
                )
        );

        BookEntity entity = bookMapper.toEntity(book, authors);
        repository.save(entity);
    }

    @Override
    public Optional<BibliographyResource> findById(ResourceID id) {
        return repository.findById(id.value())
                .map(entity -> bookMapper.toDomain((BookEntity) entity));
    }

    @Override
    public List<BibliographyResource> findAll() {
        return repository.findAll()
                .stream()
                .map(entity -> (BibliographyResource) bookMapper.toDomain((BookEntity) entity))
                .toList();
    }

    @Override
    public List<BibliographyResource> findByStatus(Status status) {
        return repository.findByStatus(status.name())
                .stream()
                .map(entity -> (BibliographyResource) bookMapper.toDomain(entity))
                .toList();
    }



    @Override
    public List<BibliographyResource> findByAuthorId(AuthorId authorId) {
        return repository.findByAuthorId(authorId.value())
                .stream()
                .map(entity -> (BibliographyResource) bookMapper.toDomain(entity))
                .collect(Collectors.toList());
    }

    @Override
    public Optional<BibliographyResource> findByTitleAndAuthorId(ResourceMainData resourceMainData, Set<AuthorId> authorIdSet) {
        BookMapper a = new BookMapper();
        var listToUUID =  authorIdSet
                .stream()
                .map(AuthorId::value)
                .collect(Collectors.toSet());

        return repository.findByTitleAndAuthorIds(resourceMainData.title(), listToUUID)
                .map(a::toDomain);
    }


}
