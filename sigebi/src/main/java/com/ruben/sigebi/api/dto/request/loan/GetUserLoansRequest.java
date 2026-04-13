package com.ruben.sigebi.api.dto.request.loan;

import java.util.UUID;

public record GetUserLoansRequest(
        UUID userId,
        String pendingState,
        String status
){
}
