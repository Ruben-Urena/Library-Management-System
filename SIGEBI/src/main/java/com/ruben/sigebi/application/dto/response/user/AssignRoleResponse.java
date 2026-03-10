package com.ruben.sigebi.application.dto.response.user;

import com.ruben.sigebi.domain.User.entity.Role;
import com.ruben.sigebi.domain.User.valueObject.RoleID;
import com.ruben.sigebi.domain.User.valueObject.UserId;

import java.util.Map;
import java.util.Set;

public record AssignRoleResponse(UserId userId, Map<Role,RoleID> roleAndId){

}
