package com.ruben.sigebi.api.controller;

import com.ruben.sigebi.api.dto.request.penalty.ApplyPenaltyRequest;
import com.ruben.sigebi.api.dto.request.penalty.RemovePenaltyRequest;
import com.ruben.sigebi.api.dto.response.penalty.PenaltyResponse;
import com.ruben.sigebi.api.dto.response.resource.GetUserPenaltiesResponse;
import com.ruben.sigebi.application.usecases.penalty.manualy.ApplyPenaltyManualUseCase;
import com.ruben.sigebi.application.usecases.penalty.manualy.RemovePenaltyManualUseCase;
import com.ruben.sigebi.application.usecases.penalty.schedule.view.GetUserPenaltiesUseCase;
import com.ruben.sigebi.domain.common.exception.DomainException;
import com.ruben.sigebi.domain.common.exception.ElementNotFoundInTheDatabaseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/penalties")
public class PenaltyController {

    private final ApplyPenaltyManualUseCase applyPenaltyManualUseCase;
    private final RemovePenaltyManualUseCase removePenaltyManualUseCase;
    private final GetUserPenaltiesUseCase getUserPenaltiesUseCase;

    public PenaltyController(ApplyPenaltyManualUseCase applyPenaltyManualUseCase,
                             RemovePenaltyManualUseCase removePenaltyManualUseCase,
                             GetUserPenaltiesUseCase getUserPenaltiesUseCase) {
        this.applyPenaltyManualUseCase = applyPenaltyManualUseCase;
        this.removePenaltyManualUseCase = removePenaltyManualUseCase;
        this.getUserPenaltiesUseCase = getUserPenaltiesUseCase;
    }

    /**
     * POST /api/penalties/apply
     * Un admin aplica una penalización manualmente a un usuario con préstamo vencido.
     *
     * Body: { "borrowerId": "uuid", "adminId": "uuid" }
     */
    @PostMapping("/apply")
    public ResponseEntity<PenaltyResponse> applyPenalty(@RequestBody ApplyPenaltyRequest request) {
        PenaltyResponse response = applyPenaltyManualUseCase.execute(request);
        HttpStatus status = response.success() ? HttpStatus.CREATED : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }

    /**
     * DELETE /api/penalties/{penaltyId}
     * Un admin elimina una penalización expirada.
     *
     * Body: { "penaltyId": "uuid", "adminId": "uuid" }
     */
    @DeleteMapping("/{penaltyId}")
    public ResponseEntity<PenaltyResponse> removePenalty(
            @PathVariable UUID penaltyId,
            @RequestBody RemovePenaltyRequest request) {
        PenaltyResponse response = removePenaltyManualUseCase.execute(request);
        HttpStatus status = response.success() ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
        return ResponseEntity.status(status).body(response);
    }

    /**
     * GET /api/penalties/user/{userId}
     * Devuelve todas las penalizaciones de un usuario.
     */
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<GetUserPenaltiesResponse>> getUserPenalties(@PathVariable UUID userId) {
        try {
            return ResponseEntity.ok(getUserPenaltiesUseCase.execute(userId));
        } catch (ElementNotFoundInTheDatabaseException e) {
            return ResponseEntity.notFound().build();
        }
    }
}