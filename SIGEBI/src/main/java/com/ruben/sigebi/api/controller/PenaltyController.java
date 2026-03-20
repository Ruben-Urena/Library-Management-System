package com.ruben.sigebi.api.controller;

import com.ruben.sigebi.api.dto.request.penalty.ApplyPenaltyRequest;
import com.ruben.sigebi.api.dto.request.penalty.RemovePenaltyRequest;
import com.ruben.sigebi.api.dto.response.penalty.PenaltyResponse;
import com.ruben.sigebi.api.dto.response.resource.GetUserPenaltiesResponse;
import com.ruben.sigebi.application.usecases.penalty.manualy.ApplyPenaltyManualUseCase;
import com.ruben.sigebi.application.usecases.penalty.manualy.RemovePenaltyManualUseCase;
import com.ruben.sigebi.application.usecases.penalty.schedule.view.GetUserPenaltiesUseCase;
import com.ruben.sigebi.domain.common.exception.DomainException;
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

    public PenaltyController(
            ApplyPenaltyManualUseCase applyPenaltyManualUseCase,
            RemovePenaltyManualUseCase removePenaltyManualUseCase, GetUserPenaltiesUseCase getUserPenaltiesUseCase
    ) {
        this.applyPenaltyManualUseCase = applyPenaltyManualUseCase;
        this.removePenaltyManualUseCase = removePenaltyManualUseCase;
        this.getUserPenaltiesUseCase = getUserPenaltiesUseCase;
    }

    // ── POST /api/admin/penalties/apply ───────────────────
    @PostMapping("/apply")
    public ResponseEntity<PenaltyResponse> applyPenalty(
            @RequestBody ApplyPenaltyRequest request
    ) {
        return ResponseEntity.ok(applyPenaltyManualUseCase.execute(request));
    }

    // ── DELETE /api/admin/penalties/remove ────────────────
    @DeleteMapping("/remove")
    public ResponseEntity<PenaltyResponse> removePenalty(
            @RequestBody RemovePenaltyRequest request
    ) {
        return ResponseEntity.ok(removePenaltyManualUseCase.execute(request));
    }



    @GetMapping("/user/{userId}")
    public ResponseEntity<List<GetUserPenaltiesResponse>> getUserPenalties(
            @PathVariable UUID userId
    ) {
        try {
            return ResponseEntity.ok(getUserPenaltiesUseCase.execute(userId));
        }catch (DomainException e){
            System.err.println(e.getMessage());
        }

        return ResponseEntity.notFound().build();
    }

}