package com.ruben.sigebi.domain.bibliographyResource.interfaces;

import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceID;

import java.util.UUID;

public interface Loanable{
    ResourceID getId();
    public void markAsLoaned(UserId userId);
    boolean canBeLoaned();
    //getPhysicalResource
}
