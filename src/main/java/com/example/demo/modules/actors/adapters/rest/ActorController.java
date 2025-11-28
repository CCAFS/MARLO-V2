package com.example.demo.modules.actors.adapters.rest;

import com.example.demo.modules.actors.adapters.rest.dto.ActorResponse;
import com.example.demo.modules.actors.adapters.rest.mapper.ActorMapper;
import com.example.demo.modules.actors.application.service.ActorService;
import com.example.demo.modules.projectinnovation.domain.model.Actor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST Controller for Actor operations
 */
@RestController
@RequestMapping("/api/actors")
@Tag(name = "Actor Types", description = "Actor Types API")
public class ActorController {
    
    private final ActorService actorService;
    private final ActorMapper actorMapper;
    
    public ActorController(ActorService actorService, ActorMapper actorMapper) {
        this.actorService = actorService;
        this.actorMapper = actorMapper;
    }
    
    @Operation(
        summary = "Get all active actors",
        description = "Retrieves a list of all active actors with their name and PRMS name equivalent"
    )
    @ApiResponses(value = {
        @ApiResponse(
            responseCode = "200",
            description = "Successfully retrieved list of actors",
            content = @Content(
                mediaType = "application/json",
                schema = @Schema(implementation = ActorResponse.class)
            )
        )
    })
    @GetMapping
    public ResponseEntity<List<ActorResponse>> getAllActiveActors() {
        List<Actor> actors = actorService.getAllActiveActors();
        List<ActorResponse> response = actorMapper.toResponseList(actors);
        return ResponseEntity.ok(response);
    }
}
