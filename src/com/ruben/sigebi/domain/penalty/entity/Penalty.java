package com.ruben.sigebi.domain.penalty.entity;
import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.PenaltyId;
import com.ruben.sigebi.domain.common.objectValue.AggregateRoot;
import com.ruben.sigebi.domain.penalty.events.PenaltyApplied;
import com.ruben.sigebi.domain.penalty.events.PenaltyForgiven;
import com.ruben.sigebi.domain.penalty.exception.InvalidPenaltyException;
import com.ruben.sigebi.domain.penalty.enums.PenaltyState;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Penalty extends AggregateRoot {

    private final PenaltyId penaltyId;
    private String description;
    private final UserId userId;
    private final Instant startDate;
    private Instant endDate;
    private PenaltyState state;


    public Penalty(PenaltyId penaltyId, UserId userId,  int daysOfLoan) {
        if(daysOfLoan<1){
            throw  new InvalidPenaltyException("cannot put negative or zero days of penalty");
        }
        Objects.requireNonNull(penaltyId);
        this.penaltyId = penaltyId;
        this.userId = Objects.requireNonNull(userId);
        this.startDate = Instant.now();
        this.endDate = startDate.plus(Duration.ofDays(daysOfLoan));
        this.state = PenaltyState.ACTIVE;
        addDomainEvent(new PenaltyApplied(getPenaltyId(),getUser(),this.startDate));
    }

    public Penalty(PenaltyId penaltyId, String description, UserId userId,
                   Instant startDate, Instant endDate, PenaltyState state) {
        this.penaltyId = Objects.requireNonNull(penaltyId);
        this.description = Objects.requireNonNull(description);
        this.userId = Objects.requireNonNull(userId);
        this.startDate = Objects.requireNonNull(startDate);
        this.endDate = Objects.requireNonNull(endDate);
        this.state = Objects.requireNonNull(state);
    }

    public void setDescription(String description) {
        Objects.requireNonNull(description,"");

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

    public boolean isActive(){
        return this.state == PenaltyState.ACTIVE && !isExpired();
    }

    public PenaltyState getState() {
        return state;
    }

    private void inactivePenalty() {
        this.state = PenaltyState.INACTIVE;
    }

    public void endPenalty(){
        if (!(isActive())) {
            throw  new InvalidPenaltyException("Penalty cannot be removed while inactive");
        }
        if (Instant.now().isBefore(endDate)){//today vs today
            throw  new InvalidPenaltyException("Penalty cannot be removed before expiration");
        }
        inactivePenalty();
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
