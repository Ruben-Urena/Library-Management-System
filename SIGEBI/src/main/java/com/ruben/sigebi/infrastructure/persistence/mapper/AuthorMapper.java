package com.ruben.sigebi.infrastructure.persistence.mapper;

import com.ruben.sigebi.domain.author.entity.Author;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.AuthorId;
import com.ruben.sigebi.domain.common.objectValue.FullName;
import com.ruben.sigebi.infrastructure.persistence.entity.author.AuthorEntity;
import com.ruben.sigebi.infrastructure.persistence.entity.user.embed.FullNameEmbeddable;

import java.util.UUID;


public class AuthorMapper {

    private AuthorMapper(){}

    public static AuthorEntity toEntity(Author author){
        AuthorEntity authorEntity = new AuthorEntity();

        authorEntity.setId(author.getAuthorId().value());
        authorEntity.setStatus(author.getStatus());
        authorEntity.setFullName(new FullNameEmbeddable(author.getFullName().name(), author.getFullName().lastName()));

        return authorEntity;
    }

    public static Author toDomain(AuthorEntity entity){
        var a = new Author( new AuthorId(UUID.fromString(entity.getId().toString())),
                new FullName(entity.getFullName().getName(),entity.getFullName().getLastname())
        );
        a.setStatus(entity.getStatus());
        return a;
    }
}