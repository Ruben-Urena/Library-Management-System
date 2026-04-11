package com.ruben.sigebi.domain.penalty.entity;
import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.PenaltyId;
import com.ruben.sigebi.domain.common.objectValue.ActivatableAggregate;
import com.ruben.sigebi.domain.loan.valueObjects.LoanId;
import com.ruben.sigebi.domain.penalty.events.PenaltyApplied;
import com.ruben.sigebi.domain.penalty.events.PenaltyForgiven;
import com.ruben.sigebi.domain.penalty.exception.InvalidPenaltyException;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;


public class Penalty extends ActivatableAggregate {

    private final PenaltyId penaltyId;
    private String description;
    private final UserId userId;
    private final Instant startDate;
    private Instant endDate;
    private final LoanId loanId;


    public Penalty(PenaltyId penaltyId, UserId userId, LoanId loanId, Instant endDate) {
        this.penaltyId = Objects.requireNonNull(penaltyId);
        this.userId = Objects.requireNonNull(userId);
        this.loanId = Objects.requireNonNull(loanId);
        this.startDate = Instant.now();
        this.endDate = Objects.requireNonNull(endDate);
        activate();
        addDomainEvent(new PenaltyApplied(getPenaltyId(), getUserId(), this.startDate));
    }


    public Penalty(PenaltyId penaltyId, UserId userId, LoanId loanId,
                   String description, Instant startDate, Instant endDate) {
        this.penaltyId = Objects.requireNonNull(penaltyId);
        this.userId = Objects.requireNonNull(userId);
        this.loanId = Objects.requireNonNull(loanId);
        this.description = description;
        this.startDate = Objects.requireNonNull(startDate);
        this.endDate = Objects.requireNonNull(endDate);
    }

    public void setDescription(String description) {
        Objects.requireNonNull(description, "description cannot be null");
        this.description = description;
    }

    public void extendDays(int days) {
        if (!isActive()) {
            throw new InvalidPenaltyException("Penalty cannot be extended while inactive");
        }
        endDate = endDate.plus(Duration.ofDays(days));
    }

    public boolean isExpired() {
        return Instant.now().isAfter(endDate);
    }

    public void endPenalty() {
        if (!isActive()) {
            throw new InvalidPenaltyException("Penalty cannot be removed while inactive");
        }
        if (Instant.now().isBefore(endDate)) {
            throw new InvalidPenaltyException("Penalty cannot be removed before expiration");
        }
        deactivate();
        addDomainEvent(new PenaltyForgiven(getPenaltyId(), getUserId(), Instant.now()));
    }

    public PenaltyId getPenaltyId() { return penaltyId; }
    public UserId getUserId() { return userId; }
    public LoanId getLoanId() { return loanId; }
    public Instant getStartDate() { return startDate; }
    public Instant getEndDate() { return endDate; }
    public String getDescription() { return description; }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Penalty penalty = (Penalty) o;
        return getPenaltyId().equals(penalty.getPenaltyId());
    }

    @Override
    public int hashCode() {
        return getPenaltyId().hashCode();
    }
}