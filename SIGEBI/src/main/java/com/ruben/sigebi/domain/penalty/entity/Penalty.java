package com.ruben.sigebi.domain.penalty.entity;
import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.PenaltyId;
import com.ruben.sigebi.domain.common.objectValue.ActivatableAggregate;
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


    public Penalty(PenaltyId penaltyId, UserId userId) {

        activate();
        Objects.requireNonNull(penaltyId);
        this.penaltyId = Objects.requireNonNull(penaltyId);
        this.userId = Objects.requireNonNull(userId);
        this.startDate = Instant.now();
        this.endDate = startDate.plus(Duration.ofDays(7));
        addDomainEvent(new PenaltyApplied(getPenaltyId(),getUser(),this.startDate));
    }

    public Penalty(PenaltyId penaltyId, String description, UserId userId,
                   Instant startDate, Instant endDate) {
        this.penaltyId = Objects.requireNonNull(penaltyId);
        this.description = Objects.requireNonNull(description);
        this.userId = Objects.requireNonNull(userId);
        this.startDate = Objects.requireNonNull(startDate);
        this.endDate = Objects.requireNonNull(endDate);
    }

    public void setDescription(String description) {
        Objects.requireNonNull(description,"description cannot be null");
    }

    public void extendDays(int days){//
        if (!isActive()){
            throw  new InvalidPenaltyException("Penalty cannot be extended while inactive");
        }
        endDate = endDate.plus(Duration.ofDays(days));
    }

    public boolean isExpired() {
        return Instant.now().isAfter(endDate);
    }


    public void endPenalty(){
        if (!(isActive())) {
            throw  new InvalidPenaltyException("Penalty cannot be removed while inactive");
        }
        if (Instant.now().isBefore(endDate)){
            throw  new InvalidPenaltyException("Penalty cannot be removed before expiration");
        }
        deactivate();
        addDomainEvent(new PenaltyForgiven(getPenaltyId(),getUser(),Instant.now()));
    }
    public PenaltyId getPenaltyId() {
        return penaltyId;
    }

    public UserId getUser() {
        return userId;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public Instant getEndDate() {
        return endDate;
    }

    public String getDescription() {
        return description;
    }

    public UserId getUserId() {
        return userId;
    }
}
