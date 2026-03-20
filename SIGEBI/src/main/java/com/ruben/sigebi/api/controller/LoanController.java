package com.ruben.sigebi.api.controller;
import com.ruben.sigebi.api.dto.request.loan.LoanResourceRequest;
import com.ruben.sigebi.api.dto.response.resource.GetUserLoansResponse;
import com.ruben.sigebi.api.dto.response.resource.LoanResourceResponse;
import com.ruben.sigebi.api.dto.response.resource.ReturnLoanResponse;
import com.ruben.sigebi.api.mappers.LoanMapper;
import com.ruben.sigebi.application.commands.resource.LoanResourceCommand;
import com.ruben.sigebi.application.usecases.loan.RequestLoanUseCase;
import com.ruben.sigebi.application.usecases.loan.ReturnLoanUseCase;
import com.ruben.sigebi.application.usecases.loan.view.GetUserLoansUseCase;
import com.ruben.sigebi.domain.User.valueObject.UserId;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import java.util.UUID;

@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final RequestLoanUseCase requestLoanUseCase;
    private final ReturnLoanUseCase returnLoanUseCase;
    private final GetUserLoansUseCase getUserLoansUseCase;


    public LoanController(
            RequestLoanUseCase requestLoanUseCase,
            ReturnLoanUseCase returnLoanUseCase,
            GetUserLoansUseCase getUserLoansUseCase

    ) {
        this.requestLoanUseCase = requestLoanUseCase;
        this.returnLoanUseCase = returnLoanUseCase;
        this.getUserLoansUseCase = getUserLoansUseCase;

    }


    @GetMapping("/user/{userId}")
    public ResponseEntity<List<GetUserLoansResponse>> getUserLoans(
            @PathVariable UUID userId
    ) {
        return ResponseEntity.ok(getUserLoansUseCase.execute(userId));
    }



    @PostMapping("/request/{userId}")
    public ResponseEntity<LoanResourceResponse> requestLoan(
            @PathVariable UUID userId,
            @RequestBody LoanResourceRequest request
    ) {
        LoanResourceCommand command = LoanMapper.loanToCommand(request, new UserId(userId));
        return ResponseEntity.ok(requestLoanUseCase.execute(command));
    }


    @PutMapping("/{loanId}/return")
    public ResponseEntity<ReturnLoanResponse> returnLoan(
            @PathVariable UUID loanId
    ) {
        return ResponseEntity.ok(returnLoanUseCase.execute(loanId));
    }
}