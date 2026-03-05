package com.ruben.sigebi.domain.bibliographyResource.interfaces;

import com.ruben.sigebi.domain.User.valueObject.UserId;

public interface Accessible {
    public void publish(UserId userId);
    public void conceal(UserId userId);
}
