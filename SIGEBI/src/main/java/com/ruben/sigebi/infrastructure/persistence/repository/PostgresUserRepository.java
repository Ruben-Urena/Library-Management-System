package com.ruben.sigebi.infrastructure.persistence.repository;

import com.ruben.sigebi.domain.User.entity.User;
import com.ruben.sigebi.domain.User.enums.UserStates;
import com.ruben.sigebi.domain.User.repository.UserRepository;
import com.ruben.sigebi.domain.User.valueObject.EmailAddress;
import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.infrastructure.config.DatabaseConnection;
import com.ruben.sigebi.infrastructure.persistence.exceptions.DatabaseException;
import com.ruben.sigebi.infrastructure.persistence.mapper.UserMapper;
import java.util.List;
import java.util.Optional;
import java.sql.*;
import java.util.ArrayList;


public class PostgresUserRepository implements UserRepository {

    @Override
    public void save(User user) {

        String sql = "INSERT INTO users (id, first_name, last_name, email, password, status) " +
                "VALUES (?, ?, ?, ?, ?, ?::user_status) " +
                "ON CONFLICT (id) DO UPDATE SET " +
                "first_name = EXCLUDED.first_name, " +
                "last_name = EXCLUDED.last_name, " +
                "status = EXCLUDED.status::user_status";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, user.getUserId().value());
            stmt.setString(2, user.getFullName().name());
            stmt.setString(3, user.getFullName().lastName());
            stmt.setString(4, user.getEmail().email());
            stmt.setString(5, user.getPassword().value());
            stmt.setString(6, user.getUserState().name().toLowerCase()); // Enviará "active" en vez de "ACTIVE"

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Error saving user", e);
        }
    }
    @Override
    public Optional<User> findById(UserId id) {
        String sql = "SELECT * FROM users WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, id.value());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return Optional.of(UserMapper.toDomain(rs));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error finding user by ID", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> findByEmail(EmailAddress email) {
        String sql = "SELECT * FROM users WHERE email = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email.email());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return Optional.of(UserMapper.toDomain(rs));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error finding user by email", e);
        }
        return Optional.empty();
    }

    @Override
    public List<User> findByStatus(UserStates status) {
        String sql = "SELECT * FROM users WHERE status = ?";
        List<User> users = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, status.name());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    users.add(UserMapper.toDomain(rs));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Error finding users by status", e);
        }
        return users;
    }

    @Override
    public void delete(User user) {
        String sql = "DELETE FROM users WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, user.getUserId().value());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error deleting user", e);
        }
    }
}