package com.ruben.sigebi.api.dto.response.penalty;

import java.util.UUID;

public record PenaltyResponse(
        boolean success,
        String message,
        UUID targetUserId
) {
    public static PenaltyResponse success(UUID userId, String message) {
        return new PenaltyResponse(true, message, userId);
    }
    public static PenaltyResponse failure(String message) {
        return new PenaltyResponse(false, message, null);
    }
}
