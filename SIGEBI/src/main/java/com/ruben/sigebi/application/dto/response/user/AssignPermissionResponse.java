package com.ruben.sigebi.application.dto.response.user;

import com.ruben.sigebi.domain.User.valueObject.Permission;
import com.ruben.sigebi.domain.User.valueObject.RoleID;
import com.ruben.sigebi.domain.User.valueObject.UserId;

import java.util.Set;

public record AssignPermissionResponse(Set<Permission> permissions, RoleID roleID, UserId target){

}
