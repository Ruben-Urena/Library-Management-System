package com.ruben.sigebi.infrastructure.persistence.repository;

import com.ruben.sigebi.domain.bibliographyResource.entity.PhysicalResource;
import com.ruben.sigebi.domain.bibliographyResource.repository.BibliographyRepository;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.AuthorId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceID;
import com.ruben.sigebi.infrastructure.config.DatabaseConnection;
import com.ruben.sigebi.infrastructure.persistence.exceptions.DatabaseException;
import com.ruben.sigebi.infrastructure.persistence.mapper.BibliographyMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PostgresBibliographyRepository implements BibliographyRepository {

    @Override
    public void save(PhysicalResource resource) {
        String sql = "INSERT INTO bibliography_resources (id, title, author_id, language, resource_type, stock_total, stock_available) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?) " +
                "ON CONFLICT (id) DO UPDATE SET title = EXCLUDED.title, stock_available = EXCLUDED.stock_available";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, resource.getId().value());
            stmt.setString(2, resource.getMainData().title());
            stmt.setObject(3, resource.getMainData().authorId().value());
            stmt.setString(4, resource.getLanguage().toString());
            stmt.setString(5, resource.getResourceType());
            stmt.setInt(6, 1);
            stmt.setInt(7, 1);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Failed to save physical resource." , e);
        }
    }

    @Override
    public Optional<PhysicalResource> findById(ResourceID id) {
        String sql = "SELECT * FROM bibliography_resources WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, id.value());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return Optional.of(BibliographyMapper.toDomain(rs));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to find by ID.", e);
        }
        return Optional.empty();
    }

    @Override
    public List<PhysicalResource> findByAuthorId(AuthorId authorId) {
        String sql = "SELECT * FROM bibliography_resources WHERE author_id = ?";
        List<PhysicalResource> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, authorId.value());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(BibliographyMapper.toDomain(rs));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to filter by author.", e);
        }
        return list;
    }
}