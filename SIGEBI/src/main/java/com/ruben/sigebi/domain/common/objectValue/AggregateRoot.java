package com.ruben.sigebi.domain.common.objectValue;

import com.ruben.sigebi.domain.common.interfaces.DomainEvent;
import java.util.ArrayList;
import java.util.List;

public abstract class AggregateRoot {
//    private Status entityState;

    private final List<DomainEvent> domainEvents = new ArrayList<>();

    protected void addDomainEvent(DomainEvent event) {
        domainEvents.add(event);
    }

    public List<DomainEvent> pullDomainEvents() {
        List<DomainEvent> events = List.copyOf(domainEvents);
        domainEvents.clear();
        return events;
    }

//    public Status getEntityState() {
//        return entityState;
//    }
//
//    public void setEntityState(Status entityState) {
//        this.entityState = entityState;
//    }
}