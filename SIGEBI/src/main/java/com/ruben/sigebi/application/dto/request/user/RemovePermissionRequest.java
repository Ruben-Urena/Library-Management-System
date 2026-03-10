package com.ruben.sigebi.application.dto.request.user;
import com.ruben.sigebi.domain.User.valueObject.Permission;
import java.util.Set;


public record RemovePermissionRequest(Set<Permission> permission){

}
