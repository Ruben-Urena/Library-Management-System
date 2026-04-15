package com.ruben.sigebi.application.usecases.loan.view;
import com.ruben.sigebi.api.dto.response.resource.GetUserLoansResponse;
import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.bibliographyResource.entity.Book;
import com.ruben.sigebi.domain.bibliographyResource.repository.BibliographyRepository;
import com.ruben.sigebi.domain.bibliographyResource.repository.ResourceCopyRepository;
import com.ruben.sigebi.domain.common.enums.Status;
import com.ruben.sigebi.domain.loan.entity.Loan;
import com.ruben.sigebi.domain.loan.repository.LoanRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.UUID;

@Service
public class GetUserLoansUseCase {

    private final LoanRepository loanRepository;
    private final ResourceCopyRepository resourceCopyRepository;
    private final BibliographyRepository bibliographyRepository;

    public GetUserLoansUseCase(LoanRepository loanRepository,
                               ResourceCopyRepository resourceCopyRepository,
                               BibliographyRepository bibliographyRepository) {
        this.loanRepository = loanRepository;
        this.resourceCopyRepository = resourceCopyRepository;
        this.bibliographyRepository = bibliographyRepository;
    }

    public List<GetUserLoansResponse> execute(UUID userId, Status status) {

        return loanRepository
                .findByStatusAndUserId(status, new UserId(userId))
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private GetUserLoansResponse toResponse(Loan loan) {

        var copy = resourceCopyRepository.findById(loan.getCopyId());


        String title = "Unknown";
        String isbn = null;

        if (copy.isPresent()) {

            var resource = bibliographyRepository.findById(copy.get().getPhysicalResourceId());
            if (resource.isPresent()) {
                title = resource.get().getMainData().title();
                if (resource.get() instanceof Book book) {
                    isbn = book.getISBN().value();
                }
            }
        }

        String startDate = loan.getStartDate().toString().split("T")[0];
        String dueDate = loan.getDueDate().toString().split("T")[0];

        return new GetUserLoansResponse(
                loan.getLoanID().loanID(),
                title,
                isbn,
                startDate,
                dueDate,
                loan.getPendingState(),
                loan.getStatus()
        );
    }
}
