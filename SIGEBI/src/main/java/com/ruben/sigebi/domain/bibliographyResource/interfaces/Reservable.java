package com.ruben.sigebi.domain.bibliographyResource.interfaces;
import com.ruben.sigebi.domain.User.valueObject.UserId;

public interface Reservable {
    public void markAsReserved(UserId userId);
}
