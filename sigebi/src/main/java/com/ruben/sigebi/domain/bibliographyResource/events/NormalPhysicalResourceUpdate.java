package com.ruben.sigebi.domain.bibliographyResource.events;

import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceID;
import com.ruben.sigebi.domain.common.interfaces.DomainEvent;
import java.time.Instant;


public record NormalPhysicalResourceUpdate(
        ResourceID resource,
        UserId updateBy,
        Instant occurredOn

)implements DomainEvent {
    @Override
    public Instant occurredOn() {
        return occurredOn;
    }
}
