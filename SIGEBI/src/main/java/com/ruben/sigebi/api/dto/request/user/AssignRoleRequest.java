package com.ruben.sigebi.api.dto.request.user;
import com.ruben.sigebi.domain.roles.entity.Role;
import com.ruben.sigebi.domain.User.valueObject.UserId;
import com.ruben.sigebi.domain.roles.valueObjects.RoleID;

import java.util.Set;

public record AssignRoleRequest(UserId userId,Set<RoleID> role){

}
