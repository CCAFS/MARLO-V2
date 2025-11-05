package com.example.demo.modules.innovationcomments.adapters.web.controller;

import com.example.demo.modules.innovationcomments.adapters.web.dto.CreateInnovationCommentRequestDto;
import com.example.demo.modules.innovationcomments.adapters.web.dto.InnovationCommentResponseDto;
import com.example.demo.modules.innovationcomments.adapters.web.mapper.InnovationCommentMapper;
import com.example.demo.modules.innovationcomments.domain.model.InnovationCatalogComment;
import com.example.demo.modules.innovationcomments.domain.port.in.InnovationCommentUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * REST Controller for Innovation Comment operations
 * Provides endpoints for managing innovation comments
 */
@RestController
@RequestMapping("/api/innovation-comments")
@Tag(name = "Innovation Comments", description = "API for managing innovation comments")
public class InnovationCommentController {
    
    private static final Logger logger = LoggerFactory.getLogger(InnovationCommentController.class);
    private final InnovationCommentUseCase commentUseCase;
    private final InnovationCommentMapper commentMapper;
    
    public InnovationCommentController(InnovationCommentUseCase commentUseCase, 
                                     InnovationCommentMapper commentMapper) {
        this.commentUseCase = commentUseCase;
        this.commentMapper = commentMapper;
    }
    
    @Operation(
        summary = "Get all comments",
        description = "Retrieves all comments ordered by most recent first. Optionally limit the number of comments returned."
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved comments"),
        @ApiResponse(responseCode = "400", description = "Invalid limit parameter"),
        @ApiResponse(responseCode = "503", description = "Comments table unavailable"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping
    public ResponseEntity<List<InnovationCommentResponseDto>> getAllComments(
            @Parameter(description = "Maximum number of comments to return", required = false, example = "10")
            @RequestParam(value = "limit", required = false) Integer limit) {
        
        try {
            List<InnovationCatalogComment> comments = commentUseCase.getAllComments(limit);
            return ResponseEntity.ok(commentMapper.toResponseDtoList(comments));
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid limit parameter provided: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            if (e.getMessage() != null && e.getMessage().contains("table not found")) {
                logger.error("Database table not found: {}", e.getMessage());
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
            }
            logger.error("Error fetching comments: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            logger.error("Unexpected error fetching comments: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @Operation(
        summary = "Get active comments by innovation ID",
        description = "Retrieves all active comments for a specific innovation, ordered by creation date descending"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved comments"),
        @ApiResponse(responseCode = "400", description = "Invalid innovation ID"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/innovation/{innovationId}")
    public ResponseEntity<List<InnovationCommentResponseDto>> getActiveCommentsByInnovationId(
            @Parameter(description = "Innovation ID to get comments for", required = true)
            @PathVariable Long innovationId) {
        
        try {
            List<InnovationCatalogComment> comments = commentUseCase.getActiveCommentsByInnovationId(innovationId);
            List<InnovationCommentResponseDto> responseDtos = commentMapper.toResponseDtoList(comments);
            return ResponseEntity.ok(responseDtos);
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid request parameter for innovation ID {}: {}", innovationId, e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            if (e.getMessage() != null && e.getMessage().contains("table not found")) {
                logger.error("Database table not found: {}", e.getMessage());
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
            }
            logger.error("Error fetching comments for innovation {}: {}", innovationId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            logger.error("Unexpected error fetching comments for innovation {}: {}", innovationId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    

    
    @Operation(
        summary = "Create a new comment",
        description = "Creates a new comment for an innovation"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Comment successfully created"),
        @ApiResponse(responseCode = "400", description = "Invalid request data"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<InnovationCommentResponseDto> createComment(
            @Parameter(description = "Comment creation request", required = true)
            @Valid @RequestBody CreateInnovationCommentRequestDto requestDto) {
        
        try {
            InnovationCatalogComment createdComment;
            
            if (requestDto.getModificationJustification() != null && !requestDto.getModificationJustification().trim().isEmpty()) {
                createdComment = commentUseCase.createCommentWithAudit(
                    requestDto.getInnovationId(),
                    requestDto.getUserName(),
                    requestDto.getUserLastname(),
                    requestDto.getUserEmail(),
                    requestDto.getComment(),
                    requestDto.getModificationJustification()
                );
            } else {
                createdComment = commentUseCase.createComment(
                    requestDto.getInnovationId(),
                    requestDto.getUserName(),
                    requestDto.getUserLastname(),
                    requestDto.getUserEmail(),
                    requestDto.getComment()
                );
            }
            
            InnovationCommentResponseDto responseDto = commentMapper.toResponseDto(createdComment);
            logger.info("Successfully created comment for innovation {}", requestDto.getInnovationId());
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDto);
            
        } catch (IllegalArgumentException e) {
            logger.warn("Invalid request data for comment creation: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        } catch (RuntimeException e) {
            if (e.getMessage() != null && e.getMessage().contains("table not found")) {
                logger.error("Database table not found during comment creation: {}", e.getMessage());
                return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).build();
            }
            logger.error("Error creating comment: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        } catch (Exception e) {
            logger.error("Unexpected error creating comment: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @Operation(
        summary = "Get comments count by innovation ID",
        description = "Gets the count of active comments for a specific innovation"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved comment count"),
        @ApiResponse(responseCode = "400", description = "Invalid innovation ID"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/innovation/{innovationId}/count")
    public ResponseEntity<Long> getActiveCommentsCount(
            @Parameter(description = "Innovation ID to count comments for", required = true)
            @PathVariable Long innovationId) {
        
        try {
            Long count = commentUseCase.getActiveCommentsCount(innovationId);
            return ResponseEntity.ok(count);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @Operation(
        summary = "Get comments by user email",
        description = "Retrieves all active comments made by a specific user"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successfully retrieved user comments"),
        @ApiResponse(responseCode = "400", description = "Invalid user email"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/user/{userEmail}")
    public ResponseEntity<List<InnovationCommentResponseDto>> getActiveCommentsByUserEmail(
            @Parameter(description = "User email to get comments for", required = true)
            @PathVariable String userEmail) {
        
        try {
            List<InnovationCatalogComment> comments = commentUseCase.getActiveCommentsByUserEmail(userEmail);
            List<InnovationCommentResponseDto> responseDtos = commentMapper.toResponseDtoList(comments);
            return ResponseEntity.ok(responseDtos);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    

    
    @Operation(
        summary = "Deactivate a comment",
        description = "Soft deletes a comment by marking it as inactive"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Comment successfully deactivated"),
        @ApiResponse(responseCode = "400", description = "Invalid comment ID"),
        @ApiResponse(responseCode = "404", description = "Comment not found or already inactive"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @DeleteMapping("/{commentId}")
    public ResponseEntity<Void> deactivateComment(
            @Parameter(description = "Comment ID to deactivate", required = true)
            @PathVariable Long commentId) {
        
        try {
            boolean deactivated = commentUseCase.deactivateComment(commentId);
            if (deactivated) {
                return ResponseEntity.ok().build();
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    

    

}
