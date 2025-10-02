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
        summary = "Get innovation types", 
        description = "Returns innovation types from the catalog. If ID is provided, returns specific type; otherwise returns all types ordered by name"
    )
    @GetMapping
    public ResponseEntity<?> getInnovationTypes(
            @Parameter(description = "Innovation type ID (optional)", example = "1")
            @RequestParam(required = false) Long id) {
        
        if (id != null) {
            // Return specific innovation type
            return innovationTypeUseCase.findInnovationTypeById(id)
                    .map(innovationType -> ResponseEntity.ok(toResponse(innovationType)))
                    .orElse(ResponseEntity.notFound().build());
        } else {
            // Return all innovation types
            List<InnovationType> innovationTypes = innovationTypeUseCase.findAllInnovationTypes();
            List<InnovationTypeResponse> response = innovationTypes.stream()
                    .map(this::toResponse)
                    .toList();
            return ResponseEntity.ok(response);
        }
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