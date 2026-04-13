package com.ruben.sigebi.application.commands.resource;
import com.ruben.sigebi.domain.bibliographyResource.valueObject.ResourceMainData;
import java.util.Set;

public record GetOneResourceCommand(
        ResourceMainData resourceMainData,
        Set<AddAuthorCommand> author
){
}
