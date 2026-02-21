package Sigebi.domain.audit;
import Sigebi.domain.user.entities.User;
import java.time.LocalDate;

public class ActivityLog {
    private Long id;
    private String action;
    private User user;
    private LocalDate date;
}
