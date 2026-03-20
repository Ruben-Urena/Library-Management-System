package com.ruben.sigebi.application.usecases.loan.view;
import com.ruben.sigebi.api.dto.request.loan.GetUserLoansRequest;
import com.ruben.sigebi.api.dto.response.resource.GetUserLoansResponse;
import com.ruben.sigebi.api.mappers.LoanMapper;
import com.ruben.sigebi.application.interfaces.UseCase;
import com.ruben.sigebi.domain.User.repository.UserRepository;
import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.bibliographyResource.entity.Book;
import com.ruben.sigebi.domain.bibliographyResource.repository.BibliographyRepository;
import com.ruben.sigebi.domain.loan.entity.Loan;
import com.ruben.sigebi.domain.loan.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

import com.ruben.sigebi.api.dto.response.resource.GetUserLoansResponse;
import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.loan.entity.Loan;
import com.ruben.sigebi.domain.loan.repository.LoanRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GetUserLoansUseCase {

    private final LoanRepository loanRepository;
    private final BibliographyRepository bibliographyRepository;
    private final UserRepository userRepository;

    public GetUserLoansUseCase(LoanRepository loanRepository, BibliographyRepository bibliographyRepository, UserRepository userRepository) {
        this.loanRepository = loanRepository;
        this.bibliographyRepository = bibliographyRepository;
        this.userRepository = userRepository;
    }

    public List<GetUserLoansResponse> execute(UUID userId) {
        return loanRepository
                .findByUser(new UserId(userId))
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private GetUserLoansResponse toResponse(Loan loan) {
        var resource = bibliographyRepository.findById(loan.getResourceId());
        String isbn = null;
        if (resource.get() instanceof Book a){
            isbn = a.getISBN().value();
        }
        var fullStartDate = loan.getStartDate().toString();
        var fullDueDate = loan.getDueDate().toString();
        var startDate = fullStartDate.split("T")[0];
        var dueDate = fullDueDate.split("T")[0];

        return new GetUserLoansResponse(
                loan.getLoanID().loanID(),
                resource.get().getMainData().title(),
                isbn,
                startDate,
                dueDate,
                loan.getPendingState(),
                loan.getStatus()
        );
    }
}
