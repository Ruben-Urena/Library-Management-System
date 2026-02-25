package Sigebi.domain.model;
import Sigebi.domain.valueObject.ActivityLog.ActivityLogId;
import java.time.LocalDate;
import java.util.UUID;

public class ActivityLog {
    private final ActivityLogId activityLogId;
    private final User user;
    private final LocalDate date;

    public ActivityLog( User user, LocalDate date) {
        this.activityLogId = new ActivityLogId(UUID.randomUUID());
        this.user = user;
        this.date = date;
    }

    public ActivityLogId getActivityLogId() {
        return activityLogId;
    }

    public User getUser() {
        return user;
    }

    public LocalDate getDate() {
        return date;
    }



}
