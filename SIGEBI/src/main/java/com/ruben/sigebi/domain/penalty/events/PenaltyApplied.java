package com.ruben.sigebi.domain.penalty.events;

import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.common.interfaces.DomainEvent;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.PenaltyId;
import java.time.Instant;

public record PenaltyApplied(
        PenaltyId penaltyId,
        UserId borrowerID,
        Instant occurredOn

) implements DomainEvent {
    @Override
    public Instant occurredOn() {
        return occurredOn;
    }
}
