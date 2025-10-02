package com.example.demo.modules.sustainabledevelopmentgoals.adapters.rest;

import com.example.demo.modules.sustainabledevelopmentgoals.adapters.rest.dto.SustainableDevelopmentGoalResponse;
import com.example.demo.modules.sustainabledevelopmentgoals.application.port.inbound.SustainableDevelopmentGoalUseCase;
import com.example.demo.modules.sustainabledevelopmentgoals.domain.model.SustainableDevelopmentGoal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sustainable-development-goals")
@Tag(name = "Sustainable Development Goals API", description = "API for managing Sustainable Development Goals")
public class SustainableDevelopmentGoalController {
    
    private final SustainableDevelopmentGoalUseCase sustainableDevelopmentGoalUseCase;
    
    public SustainableDevelopmentGoalController(SustainableDevelopmentGoalUseCase sustainableDevelopmentGoalUseCase) {
        this.sustainableDevelopmentGoalUseCase = sustainableDevelopmentGoalUseCase;
    }
    
    @Operation(summary = "Get all sustainable development goals or get by ID", 
               description = "Returns all sustainable development goals when no parameter is provided, or a specific goal when ID is provided")
    @GetMapping
    public ResponseEntity<List<SustainableDevelopmentGoalResponse>> getSustainableDevelopmentGoals(
            @Parameter(description = "Optional ID to get a specific sustainable development goal", example = "1")
            @RequestParam(required = false) Long id) {
        
        if (id != null) {
            // Return specific sustainable development goal by ID
            Optional<SustainableDevelopmentGoal> sdgOptional = sustainableDevelopmentGoalUseCase.findSustainableDevelopmentGoalById(id);
            if (sdgOptional.isPresent()) {
                List<SustainableDevelopmentGoalResponse> response = List.of(toResponse(sdgOptional.get()));
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            // Return all sustainable development goals
            List<SustainableDevelopmentGoal> sdgs = sustainableDevelopmentGoalUseCase.findAllSustainableDevelopmentGoals();
            List<SustainableDevelopmentGoalResponse> response = sdgs.stream()
                    .map(this::toResponse)
                    .toList();
            return ResponseEntity.ok(response);
        }
    }
    
    @Operation(summary = "Get sustainable development goal by SMO code", 
               description = "Returns a specific sustainable development goal by its SMO code")
    @GetMapping("/by-smo-code")
    public ResponseEntity<SustainableDevelopmentGoalResponse> getSustainableDevelopmentGoalBySmoCode(
            @Parameter(description = "SMO code of the sustainable development goal", example = "1")
            @RequestParam Long smoCode) {
        
        return sustainableDevelopmentGoalUseCase.findSustainableDevelopmentGoalBySmoCode(smoCode)
                .map(sdg -> ResponseEntity.ok(toResponse(sdg)))
                .orElse(ResponseEntity.notFound().build());
    }
    
    private SustainableDevelopmentGoalResponse toResponse(SustainableDevelopmentGoal sdg) {
        return new SustainableDevelopmentGoalResponse(
                sdg.getId(),
                sdg.getSmoCode(),
                sdg.getShortName(),
                sdg.getFullName(),
                sdg.getIcon(),
                sdg.getDescription()
        );
    }
}