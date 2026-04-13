package com.ruben.sigebi.api.dto.response.resource;

import com.ruben.sigebi.domain.common.enums.Status;

import java.time.Instant;
import java.util.UUID;

public record GetUserPenaltiesResponse(
        UUID penaltyId,
        UUID userId,
        UUID loanId,
        String description,
        Instant startDate,
        Instant endDate,
        Status status
) {}

