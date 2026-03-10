package com.ruben.sigebi.application.mappers;
import com.ruben.sigebi.application.commands.resource.LoanResourceCommand;
import com.ruben.sigebi.application.dto.request.resource.LoanResourceRequest;
import com.ruben.sigebi.application.dto.response.resource.LoanResourceResponse;
import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.loan.entity.Loan;

import java.util.Objects;

public class LoanMapper {
    public static LoanResourceCommand loanToCommand(LoanResourceRequest loanResourceRequest, UserId userId) {
        Objects.requireNonNull(loanResourceRequest);
        return new LoanResourceCommand(
                loanResourceRequest.resourceID(),
                userId
        );
    }

    public static LoanResourceResponse loanToResponse(Loan loan) {
        Objects.requireNonNull(loan);
        return new LoanResourceResponse(
                loan.getLoanID(),
                loan.getResourceId(),
                loan.getUserId()
        );
    }
}
