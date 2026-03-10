package com.ruben.sigebi.application.usecases.resource;

import com.ruben.sigebi.application.commands.resource.LoanResourceCommand;
import com.ruben.sigebi.application.dto.response.resource.LoanResourceResponse;
import com.ruben.sigebi.application.interfaces.UseCase;
import com.ruben.sigebi.application.mappers.LoanMapper;
import com.ruben.sigebi.domain.bibliographyResource.entity.PhysicalResource;
import com.ruben.sigebi.domain.bibliographyResource.repository.BibliographyRepository;
import com.ruben.sigebi.domain.loan.entity.Loan;
import com.ruben.sigebi.domain.loan.repository.LoanRepository;
import com.ruben.sigebi.domain.service.LoanService;

import java.util.Optional;
import java.util.UUID;

public class LoanResourceUseCase implements UseCase<LoanResourceResponse, LoanResourceCommand> {

    private final LoanRepository loanRepository;
    private final LoanService loanService;
    private final BibliographyRepository bibliographyRepository;

    public LoanResourceUseCase(LoanRepository loanRepository, LoanService loanService, BibliographyRepository bibliographyRepository) {
        this.loanRepository = loanRepository;
        this.loanService = loanService;
        this.bibliographyRepository = bibliographyRepository;
    }

    @Override
    public LoanResourceResponse execute(LoanResourceCommand commandRequest) {

        Optional<BibliographyRepository> physicalResource = bibliographyRepository.findById(commandRequest.resourceID());
        if (physicalResource.isEmpty()) {
            throw  new RuntimeException();
        }
        var days = loanService.LoanResource(commandRequest.resourceID(), commandRequest.userId());

        var loan = new Loan(
                commandRequest.userId(),
                UUID.randomUUID(),
                commandRequest.resourceID(),
                days
        );
        loanRepository.save(loan);

        return LoanMapper.loanToResponse(loan);
    }
}
