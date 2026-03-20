package com.ruben.sigebi.api.dto.response.resource;

import com.ruben.sigebi.domain.common.enums.Status;
import com.ruben.sigebi.domain.loan.enums.PendingState;

import java.time.Instant;
import java.util.UUID;

public record GetUserLoansResponse(
        UUID loanId,
        String resourceTitle,
        String resourceIsbn,
        String startDate,
        String dueDate,
        PendingState pendingState,
        Status status
) {}