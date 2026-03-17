package com.ruben.sigebi.api.mappers;


import com.ruben.sigebi.application.commands.user.AssignPermissionCommand;
import com.ruben.sigebi.application.commands.user.AssignRoleCommand;
import com.ruben.sigebi.api.dto.request.user.AssignPermissionRequest;
import com.ruben.sigebi.api.dto.request.user.AssignRoleRequest;
import com.ruben.sigebi.api.dto.response.user.AssignPermissionResponse;
import com.ruben.sigebi.api.dto.response.user.AssignRoleResponse;
import com.ruben.sigebi.domain.roles.entity.Role;
import com.ruben.sigebi.domain.User.entity.User;
import com.ruben.sigebi.domain.roles.valueObjects.RoleID;
import com.ruben.sigebi.domain.User.valueObject.UserId;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;


public class UserMapper {

    public static AssignPermissionCommand PermissionToCommand(AssignPermissionRequest assignPermissionRequests, UserId actor){
        return new AssignPermissionCommand(
                assignPermissionRequests.userId(),
                actor,
                assignPermissionRequests.roleID(),
                assignPermissionRequests.permissions()
        );
    }

    public static AssignPermissionResponse PermissionToResponse( AssignPermissionCommand command){
        return  new AssignPermissionResponse(
                command.permissions(),
                command.roleID(),
                command.target()
        );
    }
    public static AssignRoleCommand RoleToCommand(AssignRoleRequest assignRoleRequest, UserId actor){
        return new AssignRoleCommand(
                actor,
                assignRoleRequest.userId(),
                assignRoleRequest.role()
        );
    }

    public static AssignRoleResponse RoleToResponse(User user, Set<Role> role){
        Map<Role, RoleID> roleAndID = new HashMap<>();
        for (var a : role){
            roleAndID.put(a, a.getRoleID());
        }

        return  new AssignRoleResponse(
                user.getUserId(),
                roleAndID
        );
    }



}