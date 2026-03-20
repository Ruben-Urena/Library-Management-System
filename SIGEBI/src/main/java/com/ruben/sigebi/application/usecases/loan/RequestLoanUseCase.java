package com.ruben.sigebi.application.usecases.loan;

import com.ruben.sigebi.application.commands.resource.LoanResourceCommand;
import com.ruben.sigebi.api.dto.response.resource.LoanResourceResponse;
import com.ruben.sigebi.application.interfaces.UseCase;
import com.ruben.sigebi.api.mappers.LoanMapper;
import com.ruben.sigebi.domain.bibliographyResource.entity.BibliographyResource;
import com.ruben.sigebi.domain.bibliographyResource.repository.BibliographyRepository;
import com.ruben.sigebi.domain.common.exception.DomainException;
import com.ruben.sigebi.domain.common.exception.ElementNotFoundInTheDatabaseException;
import com.ruben.sigebi.domain.loan.entity.Loan;
import com.ruben.sigebi.domain.loan.repository.LoanRepository;
import com.ruben.sigebi.application.service.LoanService;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Service
public class RequestLoanUseCase implements UseCase<LoanResourceResponse, LoanResourceCommand> {

    private final LoanRepository loanRepository;
    private final LoanService loanService;
    private final BibliographyRepository bibliographyRepository;

    public RequestLoanUseCase(LoanRepository loanRepository, LoanService loanService, BibliographyRepository bibliographyRepository) {
        this.loanRepository = loanRepository;
        this.loanService = loanService;
        this.bibliographyRepository = bibliographyRepository;
    }

    @Override
    public LoanResourceResponse execute(LoanResourceCommand commandRequest) {
        //Only two variable here, the request resource to be loaned, and the due date of the loan.
        Optional<BibliographyResource> loanableResource = bibliographyRepository.findById(commandRequest.resourceID());
        Instant endDate;

        //throws an exception if resource is not found in the database.
        if (loanableResource.isEmpty()) {
            throw  new ElementNotFoundInTheDatabaseException("Resource not found: "+commandRequest.resourceID());
        }

        //If Loan service validate user and resource then it gives the end date and mark the resource as loaned.
        try {
            endDate = loanService.LoanResource(commandRequest.resourceID(), commandRequest.userId());
        }catch (NullPointerException | DomainException e){
            return LoanResourceResponse.failure(e.getMessage());
        }

        //create the loan to save in the database.
        var loan = new Loan(
                commandRequest.userId(),
                UUID.randomUUID(),
                commandRequest.resourceID(),
                endDate
        );
        loanRepository.save(loan);
        
        return LoanMapper.loanToResponse(loan);
    }
}
