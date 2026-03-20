package com.ruben.sigebi.api.dto.request.penalty;
import java.util.UUID;

public record ApplyPenaltyRequest(
        UUID borrowerId,
        UUID adminId
) {}
