package com.ruben.sigebi.infrastructure.persistence.mapper;

import com.ruben.sigebi.domain.User.entity.User;
import com.ruben.sigebi.domain.User.enums.UserStates;
import com.ruben.sigebi.domain.User.valueObject.EmailAddress;
import com.ruben.sigebi.domain.User.valueObject.Password;
import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.common.objectValue.FullName;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.UUID;


public class UserMapper {

    public static User toDomain(ResultSet rs) throws SQLException {
        return new User(
                new UserId(rs.getObject("id", UUID.class)),
                new FullName(
                        rs.getString("first_name"),
                        rs.getString("last_name")
                ),
                new EmailAddress(rs.getString("email")),
                new Password(rs.getString("password")),
                new HashSet<>(),
                UserStates.valueOf(rs.getString("status").toUpperCase())
        );
    }
}