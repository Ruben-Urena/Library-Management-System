package Sigebi.domain.model;
import Sigebi.domain.valueObject.resource.PenaltyId;

import java.time.LocalDate;
import java.util.UUID;

public class Penalty {
    private final PenaltyId penaltyId;
    private final User user;
    private final LocalDate startDate;
    private final LocalDate endDate;
    private boolean active;

    public Penalty( User user, LocalDate startDate, LocalDate endDate) {
        this.penaltyId = new PenaltyId(UUID.randomUUID());
        this.user = user;
        this.startDate = startDate;
        this.endDate = endDate;
        this.active = true;
    }


    public PenaltyId getPenaltyId() {
        return penaltyId;
    }

    public User getUser() {
        return user;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
