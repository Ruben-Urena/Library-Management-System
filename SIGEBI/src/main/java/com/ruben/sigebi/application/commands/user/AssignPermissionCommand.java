package com.ruben.sigebi.application.commands.user;
import com.ruben.sigebi.domain.User.entity.User;
import com.ruben.sigebi.domain.User.valueObject.Permission;
import com.ruben.sigebi.domain.User.valueObject.RoleID;
import com.ruben.sigebi.domain.User.valueObject.UserId;

import java.util.Set;

public record AssignPermissionCommand(UserId target, UserId actor, RoleID roleID, Set<Permission> permissions){

}
