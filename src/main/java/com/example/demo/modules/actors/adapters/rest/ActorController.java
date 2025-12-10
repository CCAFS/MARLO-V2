package com.example.demo.modules.actors.adapters.rest;

import com.example.demo.modules.actors.adapters.rest.dto.ActorControlListResponse;
import com.example.demo.modules.actors.adapters.rest.dto.ActorResponse;
import com.example.demo.modules.actors.adapters.rest.mapper.ActorMapper;
import com.example.demo.modules.actors.application.service.ActorService;
import com.example.demo.modules.projectinnovation.domain.model.Actor;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        summary = "Get active actors",
        description = "Retrieves active actors with their name and PRMS name equivalent. Supports optional filtering by actor name."
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
    public ResponseEntity<List<ActorResponse>> getAllActiveActors(
            @Parameter(description = "Optional case-insensitive filter for actor name")
            @RequestParam(value = "name", required = false) String nameFilter
    ) {
        List<Actor> actors = actorService.getActiveActors(nameFilter);
        List<ActorResponse> response = actorMapper.toResponseList(actors);
        return ResponseEntity.ok(response);
    }

    @Operation(
        summary = "Control list of actors",
        description = "Returns control list entries (value/label) for active actors. Supports optional filtering by name."
    )
    @GetMapping("/control-list")
    public ResponseEntity<List<ActorControlListResponse>> getActorControlList(
            @Parameter(description = "Optional case-insensitive filter for actor name")
            @RequestParam(value = "name", required = false) String nameFilter
    ) {
        List<Actor> actors = actorService.getActiveActors(nameFilter);
        List<ActorControlListResponse> response = actorMapper.toControlList(actors);
        return ResponseEntity.ok(response);
    }
}
