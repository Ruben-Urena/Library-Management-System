package com.ruben.sigebi.api.controller;

import com.ruben.sigebi.api.dto.response.resource.GetAllResourceResponse;
import com.ruben.sigebi.application.usecases.resource.view.ShowResourceUseCase;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/resources")

public class ResourceController {
    private final ShowResourceUseCase showResourceUseCase;


    public ResourceController( ShowResourceUseCase showResourceUseCase) {

        this.showResourceUseCase = showResourceUseCase;
    }

    @GetMapping
    public List<GetAllResourceResponse> getAllResources() {
        return  showResourceUseCase.execute();
    }

}
