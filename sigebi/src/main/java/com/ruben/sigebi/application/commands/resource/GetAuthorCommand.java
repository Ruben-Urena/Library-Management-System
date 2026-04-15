package com.ruben.sigebi.application.commands.resource;

import com.ruben.sigebi.domain.common.objectValue.FullName;

public record GetAuthorCommand(
       FullName fullName
) {
}
