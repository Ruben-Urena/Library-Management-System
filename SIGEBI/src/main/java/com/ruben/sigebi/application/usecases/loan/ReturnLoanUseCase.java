package com.ruben.sigebi.application.usecases.loan;

import com.ruben.sigebi.api.dto.response.resource.ReturnLoanResponse;
import com.ruben.sigebi.api.mappers.LoanMapper;
import com.ruben.sigebi.application.service.LoanService;
import com.ruben.sigebi.domain.common.exception.DomainException;
import com.ruben.sigebi.domain.common.exception.ElementNotFoundInTheDatabaseException;
import com.ruben.sigebi.domain.loan.entity.Loan;
import com.ruben.sigebi.domain.loan.repository.LoanRepository;
import com.ruben.sigebi.domain.loan.valueObjects.LoanId;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
public class ReturnLoanUseCase {

    private final LoanService loanService;
    private final LoanRepository loanRepository;

    public ReturnLoanUseCase(LoanService loanService, LoanRepository loanRepository) {
        this.loanService = loanService;
        this.loanRepository = loanRepository;
    }

    public ReturnLoanResponse execute(UUID loanId) {
        var loanIdVO = new LoanId(loanId);
        Instant returnedAt = Instant.now();

        try {
            // endLoan retorna el loan ya mutado (INACTIVE)
            Loan loan = loanService.endLoan(loanIdVO, returnedAt);

            // persiste el loan mutado
            loanRepository.save(loan);

            return LoanMapper.returnLoanToResponse(loan, returnedAt);

        } catch (DomainException e) {
            return ReturnLoanResponse.failure(loanId, e.getMessage());
        }
    }
}