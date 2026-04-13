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
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/resources")
public class ResourceController {

    private final ShowResourceUseCase showResourceUseCase;
    private final AddResourceUseCase addResourceUseCase;

    public ResourceController(ShowResourceUseCase showResourceUseCase,
                              AddResourceUseCase addResourceUseCase) {
        this.showResourceUseCase = showResourceUseCase;
        this.addResourceUseCase = addResourceUseCase;
    }

    /**
     * GET /api/resources
     * Devuelve todos los recursos bibliográficos.
     */
    @GetMapping
    public ResponseEntity<List<GetAllResourceResponse>> getAllResources() {
        return ResponseEntity.ok(showResourceUseCase.execute());
    }

    /**
     * GET /api/resources/{id}
     * Devuelve un recurso por título y autores.
     */
    @PostMapping("/search")
    public ResponseEntity<GetOneResourceResponse> getOneResource(
            @RequestBody GetOneResourceRequest request) {
        var command = ResourceMapper.getOneResourceToCommand(request);
        return ResponseEntity.ok(showResourceUseCase.execute(command));
    }

    /**
     * POST /api/resources
     * Crea un recurso bibliográfico nuevo junto con sus copias físicas.
     *
     * Body ejemplo:
     * {
     *   "title": "Clean Code",
     *   "subtitle": "A Handbook of Agile Software Craftsmanship",
     *   "language": "ENGLISH",
     *   "resourceType": "BOOK",
     *   "authors": [{ "firstName": "Robert", "lastName": "Martin" }],
     *   "isbn": "978-0132350884",
     *   "quantity": 3
     * }
     */
    @PostMapping
    public ResponseEntity<AddResourceResponse> addResource(
            @Valid @RequestBody AddResourceRequest request) {
        var command = ResourceMapper.resourceToCommand(request);
        AddResourceResponse response = addResourceUseCase.execute(command);
        HttpStatus status = response.success() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }
}
