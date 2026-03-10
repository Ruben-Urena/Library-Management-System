package com.ruben.sigebi.domain.loan.events;
import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.LoanableResourceId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceID;
import com.ruben.sigebi.domain.common.interfaces.DomainEvent;
import com.ruben.sigebi.domain.loan.valueObjects.LoanId;

import java.time.Instant;

public record LoanOverdue(
        ResourceID resourceID,
        LoanId loanId,
        UserId borrower,
        Instant occurredOn

)implements DomainEvent {
    @Override
    public Instant occurredOn() {
        return occurredOn;
    }
}
//loanOverDue; all loan that is not available, loan time ends up.
//