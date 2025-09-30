package com.example.demo.modules.projectinnovation.adapters.rest;

import com.example.demo.modules.projectinnovation.adapters.rest.dto.*;
import com.example.demo.modules.projectinnovation.application.port.inbound.ProjectInnovationUseCase;
import com.example.demo.modules.projectinnovation.domain.model.ProjectInnovation;
import com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/project-innovations")
@Tag(name = "Project Innovation API", description = "API for managing project innovations")
public class ProjectInnovationController {
    
    private final ProjectInnovationUseCase projectInnovationUseCase;
    
    public ProjectInnovationController(ProjectInnovationUseCase projectInnovationUseCase) {
        this.projectInnovationUseCase = projectInnovationUseCase;
    }
    
    @Operation(summary = "Get all project innovations")
    @GetMapping
    public ResponseEntity<List<ProjectInnovationResponse>> getAllProjectInnovations() {
        List<ProjectInnovation> projectInnovations = projectInnovationUseCase.findAllProjectInnovations();
        List<ProjectInnovationResponse> response = projectInnovations.stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "Get project innovation by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ProjectInnovationResponse> getProjectInnovationById(@PathVariable Long id) {
        return projectInnovationUseCase.findProjectInnovationById(id)
                .map(projectInnovation -> ResponseEntity.ok(toResponse(projectInnovation)))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @Operation(summary = "Create new project innovation")
    @PostMapping
    public ResponseEntity<ProjectInnovationResponse> createProjectInnovation(
            @Valid @RequestBody CreateProjectInnovationRequest request) {
        ProjectInnovation projectInnovation = new ProjectInnovation();
        projectInnovation.setProjectId(request.projectId());
        projectInnovation.setCreatedBy(request.createdBy());
        projectInnovation.setModificationJustification(request.modificationJustification());
        
        ProjectInnovation created = projectInnovationUseCase.createProjectInnovation(projectInnovation);
        return ResponseEntity.status(HttpStatus.CREATED).body(toResponse(created));
    }
    
    @Operation(summary = "Update project innovation")
    @PutMapping("/{id}")
    public ResponseEntity<ProjectInnovationResponse> updateProjectInnovation(
            @PathVariable Long id,
            @Valid @RequestBody UpdateProjectInnovationRequest request) {
        try {
            ProjectInnovation projectInnovation = new ProjectInnovation();
            projectInnovation.setIsActive(request.isActive());
            projectInnovation.setModifiedBy(request.modifiedBy());
            projectInnovation.setModificationJustification(request.modificationJustification());
            
            ProjectInnovation updated = projectInnovationUseCase.updateProjectInnovation(id, projectInnovation);
            return ResponseEntity.ok(toResponse(updated));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @Operation(summary = "Delete project innovation", description = "Marks the project innovation as inactive instead of physical deletion")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProjectInnovation(@PathVariable Long id) {
        try {
            projectInnovationUseCase.deleteProjectInnovation(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @Operation(summary = "Get all project innovations including inactive ones", description = "Returns both active and inactive project innovations")
    @GetMapping("/all")
    public ResponseEntity<List<ProjectInnovationResponse>> getAllProjectInnovationsIncludingInactive() {
        List<ProjectInnovation> projectInnovations = projectInnovationUseCase.findAllProjectInnovationsIncludingInactive();
        List<ProjectInnovationResponse> response = projectInnovations.stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "Get project innovations by project ID", description = "Returns only active project innovations for the specified project")
    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<ProjectInnovationResponse>> getProjectInnovationsByProjectId(@PathVariable Long projectId) {
        List<ProjectInnovation> projectInnovations = projectInnovationUseCase.findProjectInnovationsByProjectId(projectId);
        List<ProjectInnovationResponse> response = projectInnovations.stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "Activate project innovation")
    @PatchMapping("/{id}/activate")
    public ResponseEntity<ProjectInnovationResponse> activateProjectInnovation(@PathVariable Long id) {
        try {
            ProjectInnovation activated = projectInnovationUseCase.activateProjectInnovation(id);
            return ResponseEntity.ok(toResponse(activated));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @Operation(summary = "Deactivate project innovation")
    @PatchMapping("/{id}/deactivate")
    public ResponseEntity<ProjectInnovationResponse> deactivateProjectInnovation(@PathVariable Long id) {
        try {
            ProjectInnovation deactivated = projectInnovationUseCase.deactivateProjectInnovation(id);
            return ResponseEntity.ok(toResponse(deactivated));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @Operation(summary = "Get project innovation info by project innovation ID")
    @GetMapping("/{projectInnovationId}/info")
    public ResponseEntity<List<ProjectInnovationInfoResponse>> getProjectInnovationInfo(
            @PathVariable Long projectInnovationId) {
        List<ProjectInnovationInfo> infoList = projectInnovationUseCase
                .findProjectInnovationInfoByProjectInnovationId(projectInnovationId);
        List<ProjectInnovationInfoResponse> response = infoList.stream()
                .map(this::toInfoResponse)
                .toList();
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "Get complete project innovation info by innovation ID and phase ID with all relations")
    @GetMapping("/info/{innovationId}/phase/{phaseId}")
    public ResponseEntity<ProjectInnovationInfoCompleteResponse> getProjectInnovationInfoByInnovationIdAndPhaseId(
            @PathVariable Long innovationId, 
            @PathVariable Long phaseId) {
        return projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(innovationId, phaseId)
                .map(info -> ResponseEntity.ok(toCompleteInfoResponse(info)))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @Operation(summary = "Get basic project innovation info by innovation ID and phase ID (without relations)")
    @GetMapping("/info/{innovationId}/phase/{phaseId}/basic")
    public ResponseEntity<ProjectInnovationInfoResponse> getBasicProjectInnovationInfoByInnovationIdAndPhaseId(
            @PathVariable Long innovationId, 
            @PathVariable Long phaseId) {
        return projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(innovationId, phaseId)
                .map(info -> ResponseEntity.ok(toInfoResponse(info)))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @Operation(summary = "Get complete project innovation info by innovation ID and phase ID with all relations")
    @GetMapping("/info/{innovationId}/phase/{phaseId}/complete")
    public ResponseEntity<ProjectInnovationInfoCompleteResponse> getCompleteProjectInnovationInfoByInnovationIdAndPhaseId(
            @PathVariable Long innovationId, 
            @PathVariable Long phaseId) {
        return projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(innovationId, phaseId)
                .map(info -> ResponseEntity.ok(toCompleteInfoResponse(info)))
                .orElse(ResponseEntity.notFound().build());
    }
    
    private ProjectInnovationResponse toResponse(ProjectInnovation projectInnovation) {
        return new ProjectInnovationResponse(
                projectInnovation.getId(),
                projectInnovation.getProjectId(),
                projectInnovation.getIsActive(),
                projectInnovation.getActiveSince(),
                projectInnovation.getCreatedBy(),
                projectInnovation.getModifiedBy(),
                projectInnovation.getModificationJustification()
        );
    }
    
    private ProjectInnovationInfoResponse toInfoResponse(ProjectInnovationInfo info) {
        return new ProjectInnovationInfoResponse(
                info.getId(),
                info.getProjectInnovationId(),
                info.getIdPhase(),
                info.getYear(),
                info.getTitle(),
                info.getNarrative(),
                info.getPhaseResearchId(),
                info.getStageInnovationId(),
                info.getGeographicScopeId(),
                info.getInnovationTypeId(),
                info.getRepIndRegionId(),
                info.getRepIndContributionCrpId(),
                info.getRepIndDegreeInnovationId(),
                info.getProjectExpectedStudiesId(),
                info.getDescriptionStage(),
                info.getEvidenceLink(),
                info.getGenderFocusLevelId(),
                info.getGenderExplanation(),
                info.getYouthFocusLevelId(),
                info.getYouthExplanation(),
                info.getLeadOrganizationId(),
                info.getAdaptativeResearchNarrative(),
                info.getIsClearLead(),
                info.getOtherInnovationType(),
                info.getExternalLink(),
                info.getNumberOfInnovations(),
                info.getHasMilestones(),
                info.getShortTitle(),
                info.getInnovationNatureId(),
                info.getHasCgiarContribution(),
                info.getReasonNotCgiarContribution(),
                info.getBeneficiariesNarrative(),
                info.getIntellectualPropertyInstitutionId(),
                info.getHasLegalRestrictions(),
                info.getHasAssetPotential(),
                info.getHasFurtherDevelopment(),
                info.getOtherIntellectualProperty(),
                info.getInnovationImportance(),
                info.getReadinessScale(),
                info.getReadinessReason(),
                info.getGenderScoreId(),
                info.getClimateChangeScoreId(),
                info.getFoodSecurityScoreId(),
                info.getEnvironmentalScoreId(),
                info.getPovertyJobsScoreId()
        );
    }

    private ProjectInnovationInfoCompleteResponse toCompleteInfoResponse(ProjectInnovationInfo info) {
        return new ProjectInnovationInfoCompleteResponse(
                info.getId(),
                info.getProjectInnovationId(),
                info.getIdPhase(),
                getPhaseInfo(info.getIdPhase()),
                info.getYear(),
                info.getTitle(),
                info.getNarrative(),
                info.getPhaseResearchId(),
                info.getStageInnovationId(),
                getInnovationStageInfo(info.getStageInnovationId()),
                info.getGeographicScopeId(),
                getGeographicScopeInfo(info.getGeographicScopeId()),
                info.getInnovationTypeId(),
                getInnovationTypeInfo(info.getInnovationTypeId()),
                info.getRepIndRegionId(),
                info.getRepIndContributionCrpId(),
                info.getRepIndDegreeInnovationId(),
                info.getProjectExpectedStudiesId(),
                info.getDescriptionStage(),
                info.getEvidenceLink(),
                info.getGenderFocusLevelId(),
                info.getGenderExplanation(),
                info.getYouthFocusLevelId(),
                info.getYouthExplanation(),
                info.getLeadOrganizationId(),
                getInstitutionInfo(info.getLeadOrganizationId()),
                info.getAdaptativeResearchNarrative(),
                info.getIsClearLead(),
                info.getOtherInnovationType(),
                info.getExternalLink(),
                info.getNumberOfInnovations(),
                info.getHasMilestones(),
                info.getShortTitle(),
                info.getInnovationNatureId(),
                info.getHasCgiarContribution(),
                info.getReasonNotCgiarContribution(),
                info.getBeneficiariesNarrative(),
                info.getIntellectualPropertyInstitutionId(),
                getInstitutionInfo(info.getIntellectualPropertyInstitutionId()),
                info.getHasLegalRestrictions(),
                info.getHasAssetPotential(),
                info.getHasFurtherDevelopment(),
                info.getOtherIntellectualProperty(),
                info.getInnovationImportance(),
                info.getReadinessScale(),
                info.getReadinessReason(),
                info.getGenderScoreId(),
                info.getClimateChangeScoreId(),
                info.getFoodSecurityScoreId(),
                info.getEnvironmentalScoreId(),
                info.getPovertyJobsScoreId()
        );
    }
    
    // Métodos auxiliares para obtener información de tablas relacionadas
    private PhaseDto getPhaseInfo(Long phaseId) {
        if (phaseId == null) return null;
        // Por ahora retornamos datos de prueba, se pueden implementar consultas reales más adelante
        return new PhaseDto(phaseId, "Phase " + phaseId);
    }
    
    private InnovationStageDto getInnovationStageInfo(Long stageId) {
        if (stageId == null) return null;
        // Datos basados en la consulta que hicimos anteriormente
        String stageName = switch (stageId.intValue()) {
            case 1 -> "Stage 1: discovery/proof of concept (PC - end of research phase)";
            case 2 -> "Stage 2: successful piloting (PIL - end of piloting phase)";
            case 3 -> "Stage 3: available/ ready for uptake (AV)";
            case 4 -> "Stage 4: uptake by next user (USE)";
            default -> "Stage " + stageId;
        };
        return new InnovationStageDto(stageId, stageName);
    }
    
    private GeographicScopeDto getGeographicScopeInfo(Long scopeId) {
        if (scopeId == null) return null;
        // Por ahora retornamos datos de prueba, se pueden implementar consultas reales más adelante
        return new GeographicScopeDto(scopeId, "Geographic Scope " + scopeId);
    }
    
    private InnovationTypeDto getInnovationTypeInfo(Long typeId) {
        if (typeId == null) return null;
        // Datos basados en la consulta que hicimos anteriormente
        String typeName = switch (typeId.intValue()) {
            case 1 -> "Genetic (varieties and breeds)";
            case 2 -> "Production systems and Management practices";
            case 3 -> "Social Science";
            case 4 -> "Biophysical Research";
            case 5 -> "Research and Communication Methodologies and Tools";
            default -> "Innovation Type " + typeId;
        };
        return new InnovationTypeDto(typeId, typeName);
    }
    
    private InstitutionDto getInstitutionInfo(Long institutionId) {
        if (institutionId == null) return null;
        // Por ahora retornamos datos de prueba, se pueden implementar consultas reales más adelante
        return new InstitutionDto(institutionId, "Institution " + institutionId, "INST" + institutionId);
    }
}