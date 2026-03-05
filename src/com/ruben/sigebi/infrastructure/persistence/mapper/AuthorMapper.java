package com.ruben.sigebi.infrastructure.persistence.mapper;

import com.ruben.sigebi.domain.author.entity.Author;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.AuthorId;
import com.ruben.sigebi.domain.common.objectValue.FullName;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class AuthorMapper {
    public static Author toDomain(ResultSet rs) throws SQLException {
        UUID id = rs.getObject("id", UUID.class);
        String rawName = rs.getString("name");
        String bio = rs.getString("biography");

        String[] parts = rawName.split(" ", 2);
        String firstName = parts[0];
        String lastName = (parts.length > 1) ? parts[1] : "";

        return new Author(new AuthorId(id), new FullName(firstName, lastName));
    }
}