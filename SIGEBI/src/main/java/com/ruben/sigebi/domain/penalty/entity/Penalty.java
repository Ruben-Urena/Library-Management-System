package com.ruben.sigebi.domain.penalty.entity;
import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.PenaltyId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceID;
import com.ruben.sigebi.domain.common.objectValue.ActivatableAggregate;
import com.ruben.sigebi.domain.loan.entity.Loan;
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
    private LoanId loanId;



    public Penalty(PenaltyId penaltyId, UserId userId, LoanId loanId, Instant endDate) {
        activate();
        Objects.requireNonNull(penaltyId);
        this.loanId = Objects.requireNonNull(loanId);
        this.penaltyId = Objects.requireNonNull(penaltyId);
        this.userId = Objects.requireNonNull(userId);
        this.startDate = Instant.now();
        this.endDate = Objects.requireNonNull(endDate);
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

    public LoanId getLoanId() {
        return loanId;
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
    public LoanId getResourceID() {
        return loanId;
    }

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
