package com.ruben.sigebi.application.usecases.loan.view;

import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.common.enums.Status;
import com.ruben.sigebi.domain.loan.enums.PendingState;

public record LoanQuery(UserId userId, PendingState pendingState, Status status){
}
