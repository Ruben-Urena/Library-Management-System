package com.ruben.sigebi.api.dto.request.resource;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Set;

public record GetOneResourceRequest(
        String title,
        @JsonProperty("author")
        Set<AddAuthorRequest> author
){
}
