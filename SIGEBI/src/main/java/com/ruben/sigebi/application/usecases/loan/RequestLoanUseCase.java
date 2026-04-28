package com.ruben.sigebi.application.usecases.loan;

import com.ruben.sigebi.application.commands.resource.LoanResourceCommand;
import com.ruben.sigebi.api.dto.response.resource.LoanResourceResponse;
import com.ruben.sigebi.application.interfaces.UseCase;
import com.ruben.sigebi.api.mappers.LoanMapper;
import com.ruben.sigebi.domain.common.exception.DomainException;
import com.ruben.sigebi.domain.loan.entity.Loan;
import com.ruben.sigebi.domain.loan.repository.LoanRepository;
import com.ruben.sigebi.application.service.LoanService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RequestLoanUseCase implements UseCase<LoanResourceResponse, LoanResourceCommand> {

    private final LoanRepository loanRepository;
    private final LoanService loanService;

    public RequestLoanUseCase(LoanRepository loanRepository, LoanService loanService) {
        this.loanRepository = loanRepository;
        this.loanService = loanService;
    }


    @Override
    public LoanResourceResponse execute(LoanResourceCommand command) {
        try {

            LoanService.LoanResult result = loanService.loanResource(
                    command.resourceID(),
                    command.userId()
            );


            Loan loan = new Loan(
                    command.userId(),
                    UUID.randomUUID(),
                    result.copyId(),
                    result.dueDate()
            );

            loanRepository.save(loan);

            return LoanMapper.loanToResponse(loan);

        } catch (DomainException e) {
            return LoanResourceResponse.failure(e.getMessage());
        }
    }
    
}
