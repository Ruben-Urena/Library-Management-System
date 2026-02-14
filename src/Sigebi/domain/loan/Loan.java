package Sigebi.domain.loan;
import Sigebi.domain.resource.BibliographicResource;
import Sigebi.domain.user.User;
import java.time.LocalDate;

public class Loan {
    private Long id;
    private User user;
    private BibliographicResource resource;
    private LocalDate startDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
}
