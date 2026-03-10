package com.ruben.sigebi.application.dto.request.user;
import com.ruben.sigebi.domain.User.valueObject.EmailAddress;
import com.ruben.sigebi.domain.User.valueObject.Permission;
import com.ruben.sigebi.domain.User.valueObject.RoleID;
import com.ruben.sigebi.domain.User.valueObject.UserId;

import java.util.Set;

public record AssignPermissionRequest(UserId userId, RoleID roleID, Set<Permission> permissions) {

}
