package com.ruben.sigebi.infrastructure.persistence.repository;

import com.ruben.sigebi.domain.loan.entity.Loan;
import com.ruben.sigebi.domain.loan.repository.LoanRepository;
import com.ruben.sigebi.domain.loan.valueObjects.LoanId;
import com.ruben.sigebi.infrastructure.config.DatabaseConnection;
import com.ruben.sigebi.infrastructure.persistence.exceptions.DatabaseException;
import com.ruben.sigebi.infrastructure.persistence.mapper.LoanMapper;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class PostgresLoanRepository implements LoanRepository {

    @Override
    public void save(Loan loan) {
        String sql = "INSERT INTO loans (id, user_id, resource_id, loan_date, due_date, status) " +
                "VALUES (?, ?, ?, ?, ?, ?::loan_status) " +
                "ON CONFLICT (id) DO UPDATE SET status = EXCLUDED.status::loan_status, " +
                "return_date = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setObject(1, loan.getLoanID().loanID());
            stmt.setObject(2, loan.getUserId().value());
            stmt.setObject(3, loan.getResourceId().value());
            stmt.setTimestamp(4, Timestamp.from(loan.getStartDate()));
            stmt.setTimestamp(5, Timestamp.from(loan.getDueDate()));
            stmt.setString(6, loan.getLoanState().name().toLowerCase());
            stmt.setNull(7, java.sql.Types.TIMESTAMP);

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DatabaseException("Error en la persistencia de Loans", e);
        }
    }

    @Override
    public Optional<Loan> findById(LoanId id) {
        String sql = "SELECT * FROM loans WHERE id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, id.loanID());
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) return Optional.of(LoanMapper.toDomain(rs));
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to persist loans.", e);
        }
        return Optional.empty();
    }

    @Override
    public List<Loan> findActiveLoansByUserId(String userId) {
        String sql = "SELECT * FROM loans WHERE user_id = ? AND status = 'active'::loan_status";
        List<Loan> list = new ArrayList<>();
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setObject(1, UUID.fromString(userId));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    list.add(LoanMapper.toDomain(rs));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException("Failed to list active loans.", e);
        }
        return list;
    }
}