package com.ruben.sigebi.api.dto.request.penalty;

import java.util.UUID;

public record RemovePenaltyRequest(
        UUID penaltyId,
        UUID adminId
) {}