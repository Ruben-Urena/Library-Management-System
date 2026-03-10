package com.ruben.sigebi.application.commands.resource;

import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceID;

public record LoanResourceCommand(ResourceID resourceID, UserId userId) {
}
