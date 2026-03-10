package com.ruben.sigebi.infrastructure.persistence.repository;

import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.penalty.entity.Penalty;
import com.ruben.sigebi.domain.penalty.repository.PenaltyRepository;
import com.ruben.sigebi.infrastructure.config.DatabaseConnection;
import com.ruben.sigebi.infrastructure.persistence.exceptions.DatabaseException;
import com.ruben.sigebi.infrastructure.persistence.mapper.PenaltyMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class PostgresPenaltyRepository implements PenaltyRepository {

    @Override
    public void save(Penalty penalty) {
        String sql = "INSERT INTO penalties (id, user_id, description, start_date, end_date, state) VALUES (?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, penalty.getPenaltyId().value());
            stmt.setObject(2, penalty.getUserId().value());
            stmt.setString(3, penalty.getDescription()); // Asegúrate de tener este getter
            stmt.setTimestamp(4, Timestamp.from(penalty.getStartDate()));
            stmt.setTimestamp(5, Timestamp.from(penalty.getEndDate()));
            stmt.setString(6, penalty.getState().name());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error al guardar multa: " + e.getMessage(), e);
        }
    }
    @Override
    public List<Penalty> findUnpaidByUserId(UserId userId) {

        String sql = "SELECT * FROM penalties WHERE user_id = ? AND status = 'active'::penalty_state";
        List<Penalty> penalties = new ArrayList<>();

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, userId.value());
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    penalties.add(PenaltyMapper.toDomain(rs));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to find active penalties.", e);
        }
        return penalties;
    }
}