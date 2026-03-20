package com.ruben.sigebi.api.mappers;
import com.ruben.sigebi.api.dto.request.loan.GetUserLoansRequest;
import com.ruben.sigebi.api.dto.response.resource.GetUserLoansResponse;
import com.ruben.sigebi.api.dto.response.resource.ReturnLoanResponse;
import com.ruben.sigebi.application.commands.resource.LoanResourceCommand;
import com.ruben.sigebi.api.dto.request.loan.LoanResourceRequest;
import com.ruben.sigebi.api.dto.response.resource.LoanResourceResponse;
import com.ruben.sigebi.application.usecases.loan.view.LoanQuery;
import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceID;
import com.ruben.sigebi.domain.common.enums.Status;
import com.ruben.sigebi.domain.loan.entity.Loan;
import com.ruben.sigebi.domain.loan.enums.PendingState;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class LoanMapper {

    public static LoanResourceCommand loanToCommand(LoanResourceRequest loanResourceRequest, UserId userId) {
        Objects.requireNonNull(loanResourceRequest);
        return new LoanResourceCommand(
                new ResourceID(loanResourceRequest.resourceID()),
                userId
        );
    }

    public static LoanResourceResponse loanToResponse(Loan loan) {
        Objects.requireNonNull(loan);
        return LoanResourceResponse.susses(
                loan.getLoanID().loanID(),
                loan.getResourceId().value(),
                loan.getUserId().value(),
                loan.getDueDate().toString()
        );
    }

    public static ReturnLoanResponse returnLoanToResponse(Loan loan, Instant returnedAt) {
        Objects.requireNonNull(loan);
        return ReturnLoanResponse.success(
                loan.getLoanID().loanID(),
                loan.getResourceId().value(),
                loan.getUserId().value(),
                returnedAt
        );
    }
}
