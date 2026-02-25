package Sigebi.domain.model;
import java.time.LocalDate;

public class Loan {
    private Long id;
    private User user;
    private PhysicalResource resource;
    private LocalDate startDate;
    private LocalDate dueDate;
    private LocalDate returnDate;
}
