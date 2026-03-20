package com.ruben.sigebi.domain.common.objectValue;

import com.ruben.sigebi.domain.common.enums.Status;
import com.ruben.sigebi.domain.common.exception.InvalidationException;

public abstract class ActivatableAggregate extends AggregateRoot {
    protected Status status;

    public void activate(){
        if (status == Status.ACTIVE){
            throw  new InvalidationException("Status is already active.");
        }
        this.status = Status.ACTIVE;
    }
    public void deactivate(){
        if (!isActive()){
            throw new InvalidationException("Status is already inactive.");
        }
        this.status = Status.INACTIVE;
    }
    public boolean canBeDeleted() {
        return status == Status.ACTIVE;
    }

    public Status getStatus() {
        return status;
    }
    public boolean isActive(){
        return status == Status.ACTIVE;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
