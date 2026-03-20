package com.ruben.sigebi.api.dto.response.resource;

import java.time.Instant;
import java.util.UUID;

public record ReturnLoanResponse(
        UUID loanId,
        UUID resourceId,
        UUID userId,
        Instant returnedAt,
        boolean success,
        String message
) {
    public static ReturnLoanResponse success(UUID loanId, UUID resourceId, UUID userId, Instant returnedAt) {
        return new ReturnLoanResponse(loanId, resourceId, userId, returnedAt, true, "Loan returned successfully.");
    }

    public static ReturnLoanResponse failure(UUID loanId, String message) {
        return new ReturnLoanResponse(loanId, null, null, null, false, message);
    }
}