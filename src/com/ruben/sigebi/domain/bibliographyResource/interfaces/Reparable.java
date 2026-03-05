package com.ruben.sigebi.domain.bibliographyResource.interfaces;

import com.ruben.sigebi.domain.User.valueObject.UserId;

public interface Reparable {
    public void repair(UserId userId);
}
