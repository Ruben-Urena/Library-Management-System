package Sigebi.domain.notificacion;
import Sigebi.domain.user.entities.User;
import java.time.LocalDate;

public class Notification {
    private Long id;
    private User user;
    private String message;
    private LocalDate date;
}
