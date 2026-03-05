package com.ruben.sigebi.infrastructure.persistence.repository;

import com.ruben.sigebi.domain.author.entity.Author;
import com.ruben.sigebi.domain.author.repository.AuthorRepository;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.AuthorId;
import com.ruben.sigebi.infrastructure.config.DatabaseConnection;
import com.ruben.sigebi.infrastructure.persistence.exceptions.DatabaseException;
import com.ruben.sigebi.infrastructure.persistence.mapper.AuthorMapper;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class PostgresAuthorRepository implements AuthorRepository {
    @Override
    public void save(Author author) {
        // SQL limpio sin comillas
        String sql = "INSERT INTO authors (id, name, last_name, biography) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, author.getAuthorId().value());     // id
            stmt.setString(2, author.getFullName().name());      // name
            stmt.setString(3, author.getFullName().lastName());  // last_name
            stmt.setString(4, "Biografía de ejemplo");           // biography

            stmt.executeUpdate();
            System.out.println("✅ Autor guardado correctamente.");
        } catch (SQLException e) {
            System.err.println("Error específico de SQL: " + e.getSQLState());
            throw new DatabaseException("Fallo en la persistencia de autores: " + e.getMessage(), e);
        }
    }

    @Override
    public Optional<Author> findById(AuthorId id) {
        String sql = "SELECT * FROM authors WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, id.value());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return Optional.of(AuthorMapper.toDomain(rs));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to find author.", e);
        }
        return Optional.empty();
    }
}