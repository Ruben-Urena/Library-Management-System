package com.ruben.sigebi.api.dto.response.resource;
import java.util.Set;

public record GetOneResourceResponse(
        String title,
        Set<String> author,
        boolean susses,
        String message
){
    public static GetOneResourceResponse susses(String title, Set<String> author){
        return new GetOneResourceResponse(
                title,
                author,
                true,
                "Susses finding resource"
        );

    }
    public static GetOneResourceResponse fail(String message){
        return new  GetOneResourceResponse(
                null,
                null,
                false,
                message
        );
    }
}
