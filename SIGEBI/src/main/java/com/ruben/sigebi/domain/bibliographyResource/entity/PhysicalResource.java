package com.ruben.sigebi.domain.bibliographyResource.entity;
import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.bibliographyResource.enums.ResourceState;
import com.ruben.sigebi.domain.bibliographyResource.events.NormalPhysicalResourceUpdate;
import com.ruben.sigebi.domain.bibliographyResource.events.ResourceCreated;
import com.ruben.sigebi.domain.bibliographyResource.interfaces.Accessible;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.*;
import com.ruben.sigebi.domain.common.enums.Status;
import com.ruben.sigebi.domain.common.exception.*;
import com.ruben.sigebi.domain.bibliographyResource.interfaces.Loanable;

import java.time.Instant;
import java.util.Objects;
import java.util.Set;

public abstract class PhysicalResource extends BibliographyResource implements  Accessible {
    private Integer quantity;
    private ResourceState state;
    private PhysicalData physicalData;

    //user creation
    public PhysicalResource(ResourceMainData mainData, Language language, String resourceType, Set<AuthorId> authorIdSet, Integer quantity){
        super(mainData,language,resourceType,authorIdSet);
        this.status = Status.ACTIVE;
        this.state = ResourceState.AVAILABLE;
        setQuantity(quantity);
        activate();
//        addDomainEvent(new ResourceCreated(getId(),userId, Instant.now()));
    }

    //database creation
    public PhysicalResource(ResourceMainData mainData, Language language, String resourceType, Set<AuthorId> authorId, ResourceID resourceID) {
        super(mainData, language, resourceType, authorId, resourceID);
    }

    public PhysicalResource(ResourceID resourceID, Language language, ResourceMainData mainData, String resourceType, CreditsData creditsData, PublicationData publicationData) {
        super( resourceID, language, mainData, resourceType, creditsData, publicationData);
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        Objects.requireNonNull(quantity," Quantity cannot be null");
        if (quantity < 1){
            throw new  InvalidationException("Quantity cannot be negative: " + quantity);
        }
        this.quantity = quantity;
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


    public void setPhysicalData(PhysicalData physicalData) {
        Objects.requireNonNull(physicalData);
        if (this.physicalData != null){
            throw  new BusinessRuleViolationException("Physical data is already set");
        }
        this.physicalData = physicalData;
//        addDomainEvent(new NormalPhysicalResourceUpdate(this.getId(),userId, Instant.now()));
    }

    public void changeShelfLocation(String newShelfLocation, UserId userId) {
        if (this.physicalData != null){
            this.physicalData = new PhysicalData(physicalData.physicalFormat(), newShelfLocation);
        }
        this.physicalData = new PhysicalData(null, newShelfLocation);
        addDomainEvent(new NormalPhysicalResourceUpdate(this.getId(),userId, Instant.now()));
    }
}

