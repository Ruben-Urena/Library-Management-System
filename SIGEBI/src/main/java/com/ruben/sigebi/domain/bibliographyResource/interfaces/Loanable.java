package com.ruben.sigebi.domain.bibliographyResource.interfaces;

import com.ruben.sigebi.domain.User.valueObject.UserId;

public interface Loanable{
    public void markAsLoaned(UserId userId);
    public boolean isLoaned();
    boolean canBeLoaned();

}
