package com.ruben.sigebi.api.dto.response.user;

import com.ruben.sigebi.domain.roles.valueObjects.Permission;
import com.ruben.sigebi.domain.roles.valueObjects.RoleID;
import com.ruben.sigebi.domain.User.valueObject.UserId;

import java.util.Set;

public record AssignPermissionResponse(Set<Permission> permissions, RoleID roleID, UserId target){

}
