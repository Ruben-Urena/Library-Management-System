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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import java.util.UUID;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/loans")
public class LoanController {

    private final RequestLoanUseCase requestLoanUseCase;
    private final ReturnLoanUseCase returnLoanUseCase;
    private final GetUserLoansUseCase getUserLoansUseCase;

    public LoanController(RequestLoanUseCase requestLoanUseCase,
                          ReturnLoanUseCase returnLoanUseCase,
                          GetUserLoansUseCase getUserLoansUseCase) {
        this.requestLoanUseCase = requestLoanUseCase;
        this.returnLoanUseCase = returnLoanUseCase;
        this.getUserLoansUseCase = getUserLoansUseCase;
    }

    /**
     * GET /api/loans/user/{userId}
     * Devuelve todos los préstamos de un usuario.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<GetUserLoansResponse>> getUserLoans(@PathVariable UUID userId) {
        return ResponseEntity.ok(getUserLoansUseCase.execute(userId));
    }

    /**
     * POST /api/loans/user/{userId}
     * El usuario solicita un préstamo. El sistema asigna la primera copia disponible.
     *
     * Body: { "resourceID": "uuid-del-recurso" }
     */
    @PostMapping("/user/{userId}")
    public ResponseEntity<LoanResourceResponse> requestLoan(
            @PathVariable UUID userId,
            @RequestBody LoanResourceRequest request) {
        var command = LoanMapper.loanToCommand(request, new UserId(userId));
        LoanResourceResponse response = requestLoanUseCase.execute(command);
        HttpStatus status = response.success() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }

    /**
     * PUT /api/loans/{loanId}/return
     * Devuelve un préstamo activo y libera la copia.
     */
    @PutMapping("/{loanId}/return")
    public ResponseEntity<ReturnLoanResponse> returnLoan(@PathVariable UUID loanId) {
        ReturnLoanResponse response = returnLoanUseCase.execute(loanId);
        HttpStatus status = response.success() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }
}