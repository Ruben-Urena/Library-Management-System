package com.ruben.sigebi.domain.bibliographyResource.events;

import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.common.interfaces.DomainEvent;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceID;

import java.time.Instant;

public record ResourceCreated(
        ResourceID resourceID,
        UserId createdBy,
        Instant occurredOn

) implements DomainEvent {
    @Override
    public Instant occurredOn() {
        return occurredOn;
    }
}
