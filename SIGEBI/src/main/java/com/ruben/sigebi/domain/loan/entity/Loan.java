package com.ruben.sigebi.domain.loan.entity;
import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.LoanableResourceId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceID;
import com.ruben.sigebi.domain.common.enums.Status;
import com.ruben.sigebi.domain.common.objectValue.ActivatableAggregate;
import com.ruben.sigebi.domain.common.objectValue.AggregateRoot;
import com.ruben.sigebi.domain.loan.enums.PendingState;
import com.ruben.sigebi.domain.loan.events.LoanCreated;
import com.ruben.sigebi.domain.loan.events.LoanOverdue;
import com.ruben.sigebi.domain.loan.events.LoanReturned;
import com.ruben.sigebi.domain.loan.exception.InvalidLoanException;
import com.ruben.sigebi.domain.loan.valueObjects.LoanId;

import java.time.Duration;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Loan extends ActivatableAggregate {

    private final LoanId loanID;
    private final UserId userId;
    private final ResourceID sourceID;
    private final Instant startDate;
    private Instant dueDate;
    private PendingState  pendingState;

    public Loan(UserId userId, UUID loanId, ResourceID resourceId, Instant endDate) {

        this.userId = Objects.requireNonNull(userId);
        this.sourceID = Objects.requireNonNull(resourceId);
        this.startDate = Instant.now();
        this.dueDate = Objects.requireNonNull(endDate);
        Objects.requireNonNull(loanId);
        this.loanID = new LoanId(loanId);
        activate();
        this.pendingState = PendingState.ON_TIME;
        addDomainEvent(new LoanCreated(getResourceId(),getLoanID(),getUserId(),this.startDate));
    }

    public Loan(LoanId loanID, UserId userId, ResourceID sourceID,
                Instant startDate, Instant dueDate) {
        this.loanID = Objects.requireNonNull(loanID);
        this.userId = Objects.requireNonNull(userId);
        this.sourceID = Objects.requireNonNull(sourceID);
        this.startDate = Objects.requireNonNull(startDate);
        this.dueDate = Objects.requireNonNull(dueDate);
        activate();
    }


    public void extendDueDate(int days) {
        if (days < 1) {
            throw new InvalidLoanException("cannot put negative or zero days");
        }

        if (!isActive() ) {
            throw new InvalidLoanException("Loan cannot be extended while inactive");
        }

        if (this.dueDate.isBefore(Instant.now())) {
            throw new InvalidLoanException("Loan cannot be extended after it is overdue");
        }

        this.dueDate = this.dueDate.plus(Duration.ofDays(days));
    }



    public boolean isOverdue(){
        return  this.pendingState.equals(PendingState.OVERDUE);
    }

    public void returnLoan(Instant returnedAt){
        Objects.requireNonNull(returnedAt);
        if (!(isActive())){
            throw new InvalidLoanException("Loan already closed");
        }
        deactivate();
        addDomainEvent(new LoanReturned(getResourceId(), getLoanID(),getUserId(),returnedAt));
    }

    public void markAsOverdue(){
        if (!Instant.now().isAfter(this.dueDate)) {
            throw new InvalidLoanException("Cannot mark as overdue before the due date. ");
        }
        if(isOverdue()){
            throw new InvalidLoanException("Loan is already marked as overdue");
        }
        if (!isActive()){
            throw new InvalidLoanException("Loan is inactive");
        }
        this.pendingState =  PendingState.OVERDUE;
        addDomainEvent(new LoanOverdue(getResourceId(),getLoanID(),getUserId(),Instant.now()));
    }


    public LoanId getLoanID() {
        return loanID;
    }

    public UserId getUserId() {
        return userId;
    }

    public ResourceID getResourceId() {
        return sourceID;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public Instant getDueDate() {
        return dueDate;
    }

}
