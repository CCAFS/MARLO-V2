package com.example.demo.modules.innovationtype.adapters.rest;

import com.example.demo.modules.innovationtype.adapters.rest.dto.InnovationTypeResponse;
import com.example.demo.modules.innovationtype.application.port.inbound.InnovationTypeUseCase;
import com.example.demo.modules.innovationtype.domain.model.InnovationType;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST Controller for Innovation Type operations
 * Provides endpoints for managing innovation types catalog
 */
@RestController
@RequestMapping("/api/innovation-types")
@Tag(name = "Innovation Types API", description = "API for managing innovation types catalog")
public class InnovationTypeController {
    
    private final InnovationTypeUseCase innovationTypeUseCase;
    
    public InnovationTypeController(InnovationTypeUseCase innovationTypeUseCase) {
        this.innovationTypeUseCase = innovationTypeUseCase;
    }
    
    @Operation(
        summary = "Get all innovation types", 
        description = "Returns all innovation types from the catalog, ordered by name"
    )
    @GetMapping
    public ResponseEntity<List<InnovationTypeResponse>> getAllInnovationTypes() {
        List<InnovationType> innovationTypes = innovationTypeUseCase.findAllInnovationTypes();
        List<InnovationTypeResponse> response = innovationTypes.stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }
    
    @Operation(
        summary = "Get innovation type by ID", 
        description = "Returns a specific innovation type by its ID"
    )
    @GetMapping("/{id}")
    public ResponseEntity<InnovationTypeResponse> getInnovationTypeById(
            @Parameter(description = "Innovation type ID", example = "1")
            @PathVariable Long id) {
        return innovationTypeUseCase.findInnovationTypeById(id)
                .map(innovationType -> ResponseEntity.ok(toResponse(innovationType)))
                .orElse(ResponseEntity.notFound().build());
    }
    

    
    /**
     * Convert InnovationType domain model to full response DTO
     */
    private InnovationTypeResponse toResponse(InnovationType innovationType) {
        return new InnovationTypeResponse(
                innovationType.getId(),
                innovationType.getName(),
                innovationType.getDefinition(),
                innovationType.getIsOldType(),
                innovationType.getPrmsIdEquivalent(),
                innovationType.getPrmsNameEquivalent()
        );
    }
    

}