package com.ruben.sigebi.infrastructure.persistence.mapper;

import com.ruben.sigebi.domain.bibliographyResource.entity.PhysicalResource;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.AuthorId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.Language;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceID;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceMainData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class BibliographyMapper {
    public static PhysicalResource toDomain(ResultSet rs) throws SQLException {

        UUID rawAuthorId = (UUID) rs.getObject("author_id");
        AuthorId authorId = new AuthorId(rawAuthorId);

        ResourceID resourceID = new ResourceID((UUID) rs.getObject("id"));

        ResourceMainData mainData = new ResourceMainData(
                rs.getString("title"),
                rs.getString("subtitle"),
                new AuthorId((UUID) rs.getObject("author_id")),
                rs.getString("edition")
        );


        return new PhysicalResource(
                resourceID,
                new Language(rs.getString("language")),
                mainData,
                rs.getString("resource_type"),
                null,
                null
        );
    }
}