package com.ruben.sigebi.domain.bibliographyResource.entity;
import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.bibliographyResource.enums.ResourceState;
import com.ruben.sigebi.domain.bibliographyResource.events.NormalPhysicalResourceUpdate;
import com.ruben.sigebi.domain.bibliographyResource.events.ResourceCreated;
import com.ruben.sigebi.domain.bibliographyResource.interfaces.Accessible;
import com.ruben.sigebi.domain.bibliographyResource.interfaces.Reservable;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.*;
import com.ruben.sigebi.domain.common.enums.Status;
import com.ruben.sigebi.domain.common.exception.BusinessRuleViolationException;
import com.ruben.sigebi.domain.common.exception.DomainException;
import com.ruben.sigebi.domain.common.exception.InvalidStateException;
import com.ruben.sigebi.domain.bibliographyResource.interfaces.Loanable;
import com.ruben.sigebi.domain.common.exception.InvalidStatusException;

import java.time.Instant;
import java.util.Objects;
import java.util.Set;

public abstract class PhysicalResource extends BibliographyResource implements  Accessible {
    private ResourceState state;
    private PhysicalData physicalData;

    public PhysicalResource(ResourceMainData mainData, Language language, String resourceType, Set<AuthorId> authorIdSet, UserId userId){
        super(mainData,language,resourceType,authorIdSet);
        Objects.requireNonNull(userId);
        this.status = Status.ACTIVE;
        this.state = ResourceState.AVAILABLE;
        addDomainEvent(new ResourceCreated(getId(),userId, Instant.now()));
    }

    public PhysicalResource(ResourceID resourceID, Language language, ResourceMainData mainData, String resourceType, CreditsData creditsData, PublicationData publicationData) {
        super( resourceID, language, mainData, resourceType, creditsData, publicationData);
    }


    @Override
    public void deactivate() {
        if (this instanceof Loanable a){
            if (a.isLoaned()){
                throw new DomainException("Resource cannot be deactivated while is loaned");
            }
        }
        super.deactivate();
    }

    @Override
    public void publish(UserId userId){
        if (this.state.equals(ResourceState.AVAILABLE) || this.state.equals(ResourceState.LOANED )){
            throw new InvalidStateException("Cannot publish a resource that state is: "+ this.state);
        }
        if(this.status.equals(Status.INACTIVE)){
            throw new InvalidStatusException("Cannot publish a resource that status is "+ this.status);
        }
        this.state = ResourceState.AVAILABLE;
        addDomainEvent(new NormalPhysicalResourceUpdate(this.getId(),userId, Instant.now()));
    }

    @Override
    public void locked(UserId userId){
        if (this.state.equals(ResourceState.LOANED) || this.state.equals(ResourceState.LOCKED)){
            throw new InvalidStateException("Cannot lock a resource that state is "+ this.state);
        }
        if(this.status.equals(Status.INACTIVE)){
            throw new InvalidStatusException("Cannot lock a resource that status is "+ this.status);
        }
        this.state = ResourceState.LOCKED;
        addDomainEvent(new NormalPhysicalResourceUpdate(this.getId(),userId, Instant.now()));
    }

    public void setState(ResourceState state) {
        this.state = state;
    }


    public ResourceState getState() {
        return state;
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

