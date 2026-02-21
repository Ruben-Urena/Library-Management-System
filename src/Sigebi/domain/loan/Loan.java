package Sigebi.domain.loan;
import Sigebi.domain.resource.entities.PhysicalCopy;
import Sigebi.domain.user.entities.User;
import java.time.LocalDate;

public class Loan {
    private Long id;
    private User user;
    private PhysicalCopy resource;
    private LocalDate startDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
}
