package com.ruben.sigebi.domain.bibliographyResource.entity;
import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.bibliographyResource.enums.ResourceStatus;
import com.ruben.sigebi.domain.bibliographyResource.events.NormalPhysicalResourceUpdate;
import com.ruben.sigebi.domain.bibliographyResource.events.ResourceCreated;
import com.ruben.sigebi.domain.bibliographyResource.interfaces.Accessible;
import com.ruben.sigebi.domain.bibliographyResource.interfaces.Reparable;
import com.ruben.sigebi.domain.bibliographyResource.interfaces.Reservable;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.*;
import com.ruben.sigebi.domain.common.exception.BusinessRuleViolationException;
import com.ruben.sigebi.domain.common.exception.InvalidStateException;
import com.ruben.sigebi.domain.bibliographyResource.interfaces.Loanable;

import java.time.Instant;
import java.util.Objects;

public class NormalPhysicalBibliographyResource extends bibliographyResource implements Loanable, Reservable, Accessible, Reparable {


    private ResourceStatus status;
    private PhysicalData physicalData;

    public NormalPhysicalBibliographyResource(ResourceMainData mainData, Language language, String resourceType, UserId userId){
        super(mainData,language,resourceType);
        Objects.requireNonNull(userId);
        this.status = ResourceStatus.INACTIVE;
        addDomainEvent(new ResourceCreated(getBaseResourceID(),userId, Instant.now()));
    }



    @Override
    public void publish(UserId userId){
        if (this.status.equals(ResourceStatus.AVAILABLE) || this.status.equals(ResourceStatus.LOANED )){
            throw new InvalidStateException("Cannot publish a resource that is "+ this.status);
        }
        this.status =ResourceStatus.AVAILABLE;
        addDomainEvent(new NormalPhysicalResourceUpdate(this.getId(),userId, Instant.now()));
    }

    @Override
    public void conceal(UserId userId){
        if (this.status.equals(ResourceStatus.LOANED)){
            throw new InvalidStateException("Cannot conceal a resource that is "+ this.status);
        }
        this.status = ResourceStatus.INACTIVE;
        addDomainEvent(new NormalPhysicalResourceUpdate(this.getId(),userId, Instant.now()));
    }

    @Override
    public void repair(UserId userId){
        if (this.status.equals(ResourceStatus.AVAILABLE) ||this.status.equals(ResourceStatus.INACTIVE)){
            this.status = ResourceStatus.UNDER_REPAIR;
            addDomainEvent(new NormalPhysicalResourceUpdate(this.getId(),userId, Instant.now()));
        }
        throw new InvalidStateException("Cannot repair a resource that is "+ this.status);
    }


    @Override
    public ResourceID getId() {
        return getBaseResourceID();

    }

    @Override
    public void markAsLoaned(UserId userId){
        if (this.status.equals(ResourceStatus.UNDER_REPAIR)||
                this.status.equals( ResourceStatus.INACTIVE) ||
                this.status.equals( ResourceStatus.LOST) ||
                this.status.equals(ResourceStatus.WITHDRAWN)){
            throw new InvalidStateException("Cannot loan a resource that is "+ this.status);
        }
        this.status = ResourceStatus.LOANED;
    }

    @Override
    public void markAsReserved(UserId userId){
        if (!this.status.equals(ResourceStatus.AVAILABLE)){
            throw new InvalidStateException("Cannot reserve a resource that is "+ this.status);
        }
        this.status = ResourceStatus.RESERVED;
        addDomainEvent(new NormalPhysicalResourceUpdate(this.getId(),userId, Instant.now()));
    }

    public void reportLost(UserId userId){
        this.status = ResourceStatus.LOST;
        addDomainEvent(new NormalPhysicalResourceUpdate(this.getId(),userId, Instant.now()));
    }

    public void withdrawn(UserId  userId){
        if (this.status.equals(ResourceStatus.AVAILABLE)){
            throw new InvalidStateException("Cannot withdraw a resource that is "+ this.status);
        }
        this.status = ResourceStatus.WITHDRAWN;
        addDomainEvent(new NormalPhysicalResourceUpdate(this.getId(),userId, Instant.now()));
    }

    @Override
    public boolean canBeLoaned() {
        return !this.status.equals(ResourceStatus.LOANED) &&
                !this.status.equals(ResourceStatus.LOST) &&
                !this.status.equals(ResourceStatus.INACTIVE) &&
                !this.status.equals(ResourceStatus.RESERVED) &&
                !this.status.equals(ResourceStatus.WITHDRAWN) &&
                !this.status.equals(ResourceStatus.UNDER_REPAIR);
    }


    public ResourceStatus getStatus() {
        return status;
    }


    public PhysicalData getPhysicalData() {
        return physicalData;
    }


    public void setPhysicalData(PhysicalData physicalData,UserId userId) {
        Objects.requireNonNull(physicalData);
        if (this.physicalData != null){
            throw  new BusinessRuleViolationException("Physical data is already set");
        }
        this.physicalData = physicalData;
        addDomainEvent(new NormalPhysicalResourceUpdate(this.getId(),userId, Instant.now()));
    }

    public void changeShelfLocation(String newShelfLocation, UserId userId) {
        if (this.physicalData != null){
            this.physicalData = new PhysicalData(physicalData.physicalFormat(), newShelfLocation);
        }
        this.physicalData = new PhysicalData(null, newShelfLocation);
        addDomainEvent(new NormalPhysicalResourceUpdate(this.getId(),userId, Instant.now()));
    }

}
