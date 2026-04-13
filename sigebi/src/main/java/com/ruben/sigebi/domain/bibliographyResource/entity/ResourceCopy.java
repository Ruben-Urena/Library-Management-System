package com.ruben.sigebi.domain.bibliographyResource.entity;
import com.ruben.sigebi.domain.bibliographyResource.enums.ResourceState;
import com.ruben.sigebi.domain.bibliographyResource.interfaces.Loanable;
import com.ruben.sigebi.domain.bibliographyResource.interfaces.Reservable;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceCopyId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceID;
import com.ruben.sigebi.domain.common.enums.Status;
import com.ruben.sigebi.domain.common.exception.BusinessRuleViolationException;
import com.ruben.sigebi.domain.common.objectValue.ActivatableAggregate;
import java.time.Instant;
import java.util.Objects;


public class ResourceCopy extends ActivatableAggregate implements Loanable, Reservable {
    private final ResourceCopyId id;
    private ResourceState state;
    private final ResourceID physicalResourceId;
    private final Instant acquisitionDate;

    public ResourceCopy(ResourceCopyId id, ResourceState state, ResourceID physicalResourceId, Instant acquisitionDate) {
        this.id = Objects.requireNonNull(id," ResourceCopyId cannot be null");
        this.state = Objects.requireNonNull(state," ResourceState Cannot be null");
        this.physicalResourceId = Objects.requireNonNull(physicalResourceId, " PhysicalResourceId cannot be null");
        this.acquisitionDate = Objects.requireNonNull(acquisitionDate," AcquisitionDate cannot be null");
    }



    @Override
    public void markAsLoaned(){
        if (getStatus() == Status.INACTIVE) throw new BusinessRuleViolationException(this.id+" cannot mark as loaned while status is : "+getStatus());
        if (state == ResourceState.RESERVED || state == ResourceState.AVAILABLE){
            state = ResourceState.LOANED;
        }else{
            throw new BusinessRuleViolationException(this.id+ " cannot mark as loaned while state is : "+this.state);
        }
    }

    @Override
    public boolean isLoaned(){
        return state == ResourceState.LOANED;
    }

    @Override
    public void returnLoan(){
        if (!(state == ResourceState.LOANED)){
            throw new BusinessRuleViolationException(this.id+ "cannot return loan while state is : "+this.state);
        }else {
            state = ResourceState.AVAILABLE;
        }
    }

    @Override
    public void markAsReserved(){
        if (getStatus() == Status.INACTIVE) throw new BusinessRuleViolationException(this.id+" cannot mark as reserved  while state is : "+this.state);
        if (state == ResourceState.AVAILABLE){
            state = ResourceState.RESERVED;
        }else {
            throw new BusinessRuleViolationException(this.id+" cannot mark as reserved  while state is : "+this.state);
        }
    }

    @Override
    public boolean isReserved() {
        return state == ResourceState.RESERVED;
    }

    @Override
    public void cancelReservation(){
        if(state == ResourceState.RESERVED){
            state = ResourceState.AVAILABLE;
        }else {
            throw new BusinessRuleViolationException(this.id+" cannot cancel reservation if state is : "+this.state);
        }
    }


    //getters
    public ResourceCopyId getId() {
        return id;
    }

    public ResourceState getState() {
        return state;
    }

    public ResourceID getPhysicalResourceId() {
        return physicalResourceId;
    }

    public Instant getAcquisitionDate() {
        return acquisitionDate;
    }

}
