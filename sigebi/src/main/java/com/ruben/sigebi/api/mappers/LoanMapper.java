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

    public static LoanResourceCommand loanToCommand(LoanResourceRequest request, UserId userId) {
        Objects.requireNonNull(request);
        return new LoanResourceCommand(
                new ResourceID(request.resourceID()),
                userId
        );
    }

    public static LoanResourceResponse loanToResponse(Loan loan) {
        Objects.requireNonNull(loan);
        return LoanResourceResponse.success(
                loan.getLoanID().loanID(),
                loan.getCopyId().value(),   // ✅ getCopyId(), no getResourceId()
                loan.getUserId().value(),
                loan.getDueDate().toString()
        );
    }

    public static ReturnLoanResponse returnLoanToResponse(Loan loan, Instant returnedAt) {
        Objects.requireNonNull(loan);
        return ReturnLoanResponse.success(
                loan.getLoanID().loanID(),
                loan.getCopyId().value(),   // ✅ getCopyId(), no getResourceId()
                loan.getUserId().value(),
                returnedAt
        );
    }
}
