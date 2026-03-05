package com.ruben.sigebi.infrastructure.persistence.mapper;
import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.PenaltyId;
import com.ruben.sigebi.domain.penalty.entity.Penalty;
import com.ruben.sigebi.domain.penalty.enums.PenaltyState;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.UUID;

public class PenaltyMapper {
    public static Penalty toDomain(ResultSet rs) throws SQLException {
        return new Penalty(
                new PenaltyId((UUID) rs.getObject("id")),
                rs.getString("description"),
                new UserId((UUID) rs.getObject("user_id")),
                rs.getTimestamp("start_date").toInstant(),
                rs.getTimestamp("end_date").toInstant(),
                PenaltyState.valueOf(rs.getString("state"))
        );
    }
}