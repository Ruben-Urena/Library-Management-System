package com.ruben.sigebi.infrastructure.persistence.mapper;
import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.LoanableResourceId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceID;
import com.ruben.sigebi.domain.common.enums.Status;
import com.ruben.sigebi.domain.loan.entity.Loan;
import com.ruben.sigebi.domain.loan.valueObjects.LoanId;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.util.UUID;

public class LoanMapper {
    public static Loan toDomain(ResultSet rs) throws SQLException {

        LoanId loanId = new LoanId(rs.getObject("id", UUID.class));
        UserId userId = new UserId(rs.getObject("user_id", UUID.class));
        LoanableResourceId resourceId = new LoanableResourceId(
                new ResourceID(rs.getObject("resource_id", UUID.class))
        );


        Instant startDate = rs.getTimestamp("loan_date").toInstant();
        Instant dueDate = rs.getTimestamp("due_date").toInstant();

        Status state = Status.valueOf(rs.getString("status").toUpperCase());


        return new Loan(loanId, userId, resourceId, startDate, dueDate, state);
    }
}