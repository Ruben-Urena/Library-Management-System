package com.ruben.sigebi.application.commands.user;
import com.ruben.sigebi.domain.roles.valueObjects.Permission;
import com.ruben.sigebi.domain.roles.valueObjects.RoleID;
import com.ruben.sigebi.domain.User.valueObject.UserId;

import java.util.Set;

public record AssignPermissionCommand(UserId target, UserId actor, RoleID roleID, Set<Permission> permissions){

}
