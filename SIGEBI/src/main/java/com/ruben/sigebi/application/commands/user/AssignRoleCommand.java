package com.ruben.sigebi.application.commands.user;

import com.ruben.sigebi.domain.roles.entity.Role;
import com.ruben.sigebi.domain.User.valueObject.UserId;

import java.util.Set;

public record AssignRoleCommand(UserId actor,UserId target, Set<Role> role){


}
