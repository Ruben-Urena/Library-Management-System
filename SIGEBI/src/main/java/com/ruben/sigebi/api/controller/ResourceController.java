package com.ruben.sigebi.api.controller;

import com.ruben.sigebi.api.dto.request.resource.AddResourceRequest;
import com.ruben.sigebi.api.dto.request.resource.GetOneResourceRequest;
import com.ruben.sigebi.api.dto.response.resource.AddResourceResponse;
import com.ruben.sigebi.api.dto.response.resource.GetAllResourceResponse;
import com.ruben.sigebi.api.dto.response.resource.GetOneResourceResponse;
import com.ruben.sigebi.api.mappers.ResourceMapper;
import com.ruben.sigebi.application.commands.resource.AddResourceCommand;
import com.ruben.sigebi.application.usecases.resource.AddResourceUseCase;
import com.ruben.sigebi.application.usecases.resource.view.ShowResourceUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/resources")
public class ResourceController {
    private final ShowResourceUseCase showResourceUseCase;
    private final AddResourceUseCase resourceUseCase;


    public ResourceController(ShowResourceUseCase showResourceUseCase, AddResourceUseCase resourceUseCase) {

        this.showResourceUseCase = showResourceUseCase;
        this.resourceUseCase = resourceUseCase;
    }

    @GetMapping("/get/resources")
    public List<GetAllResourceResponse> getAllResources() {
        return  showResourceUseCase.execute();
    }

    @PostMapping("/add/resource")
    public AddResourceResponse addResource(@RequestBody AddResourceRequest request){
        var resource =   ResourceMapper.resourceToCommand(request);
        return resourceUseCase.execute(resource);
    }



    @PostMapping("/get/resource")
    public GetOneResourceResponse getOneResource(@RequestBody GetOneResourceRequest request){
        var resource = ResourceMapper.getOneResourceToCommand(request);
        return showResourceUseCase.execute(resource);
    }





}
