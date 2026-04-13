package com.ruben.sigebi.api.dto.response.resource;

import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceID;
import com.ruben.sigebi.domain.loan.valueObjects.LoanId;

import java.util.UUID;

public record LoanResourceResponse(
        UUID loanId,
        UUID copyId,       // ✅ es la copia específica prestada, no el recurso padre
        UUID userId,
        boolean success,
        String message,
        String dueDate
) {
    public static LoanResourceResponse success(UUID loanId, UUID copyId, UUID userId, String dueDate) {
        return new LoanResourceResponse(loanId, copyId, userId, true, "Loan created successfully.", dueDate);
    }

    public static LoanResourceResponse failure(String message) {
        return new LoanResourceResponse(null, null, null, false, message, null);
    }
}

