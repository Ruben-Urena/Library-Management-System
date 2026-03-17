package com.ruben.sigebi.api.dto.request.user;
import com.ruben.sigebi.domain.roles.valueObjects.Permission;
import java.util.Set;


public record RemovePermissionRequest(Set<Permission> permission){

}
