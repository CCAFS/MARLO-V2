package com.example.demo.modules.innovationreports.adapters.web.controller;

import com.example.demo.modules.innovationreports.adapters.web.dto.CreateInnovationReportRequestDto;
import com.example.demo.modules.innovationreports.adapters.web.dto.InnovationReportResponseDto;
import com.example.demo.modules.innovationreports.adapters.web.mapper.InnovationReportMapper;
import com.example.demo.modules.innovationreports.domain.model.InnovationCatalogReport;
import com.example.demo.modules.innovationreports.domain.port.in.InnovationReportUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/innovation-reports")
@Tag(name = "Innovation Reports", description = "API for managing innovation catalog reports")
public class InnovationReportController {

    private static final Logger logger = LoggerFactory.getLogger(InnovationReportController.class);

    private final InnovationReportUseCase reportUseCase;
    private final InnovationReportMapper mapper;

    public InnovationReportController(InnovationReportUseCase reportUseCase, InnovationReportMapper mapper) {
        this.reportUseCase = reportUseCase;
        this.mapper = mapper;
    }

    @Operation(summary = "Get active reports by innovation ID")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reports retrieved successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid innovation ID"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/innovation/{innovationId}")
    public ResponseEntity<List<InnovationReportResponseDto>> getActiveReportsByInnovationId(
            @Parameter(description = "Innovation ID", required = true)
            @PathVariable Long innovationId) {
        try {
            List<InnovationCatalogReport> reports = reportUseCase.getActiveReportsByInnovationId(innovationId);
            return ResponseEntity.ok(mapper.toResponseDtoList(reports));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Error fetching reports for innovation {}: {}", innovationId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Get active reports count by innovation ID")
    @GetMapping("/innovation/{innovationId}/count")
    public ResponseEntity<Long> getActiveReportsCount(
            @Parameter(description = "Innovation ID", required = true)
            @PathVariable Long innovationId) {
        try {
            return ResponseEntity.ok(reportUseCase.getActiveReportsCount(innovationId));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Error counting reports for innovation {}: {}", innovationId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Get active reports by user email")
    @GetMapping("/user/{userEmail}")
    public ResponseEntity<List<InnovationReportResponseDto>> getActiveReportsByUserEmail(
            @Parameter(description = "Reporter email", required = true)
            @PathVariable String userEmail) {
        try {
            List<InnovationCatalogReport> reports = reportUseCase.getActiveReportsByUserEmail(userEmail);
            return ResponseEntity.ok(mapper.toResponseDtoList(reports));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Error fetching reports for user {}: {}", userEmail, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Create a new innovation report")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Report created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request payload"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @PostMapping
    public ResponseEntity<InnovationReportResponseDto> createReport(
            @Valid @RequestBody CreateInnovationReportRequestDto requestDto) {
        try {
            InnovationCatalogReport created;
            if (requestDto.getModificationJustification() != null && !requestDto.getModificationJustification().isBlank()) {
                created = reportUseCase.createReportWithAudit(
                        requestDto.getInnovationId(),
                        requestDto.getUserName(),
                        requestDto.getUserLastname(),
                        requestDto.getUserEmail(),
                        requestDto.getInterestNarrative(),
                        requestDto.getModificationJustification()
                );
            } else {
                created = reportUseCase.createReport(
                        requestDto.getInnovationId(),
                        requestDto.getUserName(),
                        requestDto.getUserLastname(),
                        requestDto.getUserEmail(),
                        requestDto.getInterestNarrative()
                );
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(mapper.toResponseDto(created));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Error creating report for innovation {}: {}", requestDto.getInnovationId(), e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Deactivate a report")
    @DeleteMapping("/{reportId}")
    public ResponseEntity<Void> deactivateReport(
            @Parameter(description = "Report ID", required = true)
            @PathVariable Long reportId) {
        try {
            boolean deactivated = reportUseCase.deactivateReport(reportId);
            if (deactivated) {
                return ResponseEntity.ok().build();
            }
            return ResponseEntity.notFound().build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            logger.error("Error deactivating report {}: {}", reportId, e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
