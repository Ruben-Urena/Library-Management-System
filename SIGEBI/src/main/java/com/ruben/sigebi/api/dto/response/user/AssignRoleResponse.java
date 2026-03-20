package com.ruben.sigebi.api.dto.response.user;

import com.ruben.sigebi.domain.roles.entity.Role;
import com.ruben.sigebi.domain.roles.valueObjects.RoleID;
import com.ruben.sigebi.domain.User.valueObject.UserId;

import java.util.Map;

public record AssignRoleResponse(UserId userId, Map<String,RoleID> roleAndId){

}
