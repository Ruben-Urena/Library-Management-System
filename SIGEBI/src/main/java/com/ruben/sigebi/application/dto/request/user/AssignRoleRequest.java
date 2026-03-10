package com.ruben.sigebi.application.dto.request.user;
import com.ruben.sigebi.domain.User.entity.Role;
import com.ruben.sigebi.domain.User.valueObject.EmailAddress;
import com.ruben.sigebi.domain.User.valueObject.RoleID;
import com.ruben.sigebi.domain.User.valueObject.UserId;

import java.util.Map;
import java.util.Set;

public record AssignRoleRequest(UserId userId,Set<Role> role){

}
