package com.ruben.sigebi.domain.loan.entity;
import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.bibliographyResource.interfaces.Loanable;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.LoanableResourceId;
import com.ruben.sigebi.domain.common.enums.EntityState;
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

public class Loan extends AggregateRoot {

    private final LoanId loanID;
    private final UserId userId;
    private final LoanableResourceId sourceID;
    private final Instant startDate;
    private Instant dueDate;
    private EntityState loanState;
    private PendingState  pendingState;

    public Loan(UserId userId, UUID loanId, LoanableResourceId resourceId, int days) {
        if(days < 1){
            throw new InvalidLoanException("cannot put negative or zero days");
        }
        this.userId = Objects.requireNonNull(userId);
        this.sourceID = Objects.requireNonNull(resourceId);
        this.startDate = Instant.now();
        this.dueDate = startDate.plus(Duration.ofDays(days));
        Objects.requireNonNull(loanId);
        this.loanID = new LoanId(loanId);
        this.loanState = EntityState.ACTIVE;
        this.pendingState = PendingState.ON_TIME;
        addDomainEvent(new LoanCreated(getResourceId(),getLoanID(),getUserId(),this.startDate));
    }

    public Loan(LoanId loanID, UserId userId, LoanableResourceId sourceID,
                Instant startDate, Instant dueDate, EntityState loanState) {
        this.loanID = Objects.requireNonNull(loanID);
        this.userId = Objects.requireNonNull(userId);
        this.sourceID = Objects.requireNonNull(sourceID);
        this.startDate = Objects.requireNonNull(startDate);
        this.dueDate = Objects.requireNonNull(dueDate);
        this.loanState = Objects.requireNonNull(loanState);
    }

    public EntityState getLoanState() {
        return loanState;
    }

    public void extendDueDate(int days) {
        if (days < 1) {
            throw new InvalidLoanException("cannot put negative or zero days");
        }

        if (!isActive() && this.pendingState == PendingState.OVERDUE) {
            throw new InvalidLoanException("Loan cannot be extended while inactive or overdue");
        }

        if (this.dueDate.isBefore(Instant.now())) {
            throw new InvalidLoanException("Loan cannot be extended after it is overdue");
        }

        this.dueDate = this.dueDate.plus(Duration.ofDays(days));
    }

    public boolean isActive(){
        return this.loanState == EntityState.ACTIVE ;
    }

    public boolean isOverdue(){
        return isActive() && this.dueDate.isBefore(Instant.now()) && this.pendingState == PendingState.OVERDUE;
    }

    public void returnLoan(Instant returnedAt){

        Objects.requireNonNull(returnedAt);
        if (!(isActive())){
            throw new InvalidLoanException("Loan already closed");
        }
        this.loanState = EntityState.INACTIVE;
        addDomainEvent(new LoanReturned(getResourceId(), getLoanID(),getUserId(),returnedAt));
    }

    public void markAsOverdue(){
        if(isOverdue()){
            throw new InvalidLoanException("Loan is already marked as overdue");
        }
        if (!isActive()){
            throw new InvalidLoanException("Loan already closed");
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

    public LoanableResourceId getResourceId() {
        return sourceID;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public Instant getDueDate() {
        return dueDate;
    }

}
