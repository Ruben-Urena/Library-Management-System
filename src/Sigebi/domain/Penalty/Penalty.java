package Sigebi.domain.Penalty;
import Sigebi.domain.user.User;
import java.time.LocalDate;

public class Penalty {
    private Long id;
    private User user;
    private LocalDate startDate;
    private LocalDate endDate;
    private boolean active;
}
