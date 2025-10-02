package com.example.demo.modules.projectinnovation.adapters.rest;

import com.example.demo.modules.projectinnovation.adapters.rest.dto.*;
import com.example.demo.modules.projectinnovation.adapters.rest.mapper.ProjectInnovationActorsMapper;
import com.example.demo.modules.projectinnovation.application.port.inbound.ProjectInnovationUseCase;
import com.example.demo.modules.projectinnovation.application.service.ProjectInnovationActorsService;
import com.example.demo.modules.projectinnovation.domain.model.ProjectInnovation;
import com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationActors;
import com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationInfo;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/innovations")
@Tag(name = "Innovation API", description = "API for managing innovations")
public class ProjectInnovationController {
    
    private final ProjectInnovationUseCase projectInnovationUseCase;
    private final ProjectInnovationActorsService actorsService;
    private final ProjectInnovationActorsMapper actorsMapper;
    
    public ProjectInnovationController(
            ProjectInnovationUseCase projectInnovationUseCase,
            ProjectInnovationActorsService actorsService,
            ProjectInnovationActorsMapper actorsMapper) {
        this.projectInnovationUseCase = projectInnovationUseCase;
        this.actorsService = actorsService;
        this.actorsMapper = actorsMapper;
    }
    
    @Operation(summary = "Get project innovation by ID")
    @GetMapping("/by-id")
    public ResponseEntity<ProjectInnovationResponse> getProjectInnovationById(
            @Parameter(description = "Innovation ID", example = "1")
            @RequestParam Long id) {
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
    @PutMapping("/update")
    public ResponseEntity<ProjectInnovationResponse> updateProjectInnovation(
            @Parameter(description = "Innovation ID", example = "1")
            @RequestParam Long id,
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
    @GetMapping("/by-project")
    public ResponseEntity<List<ProjectInnovationResponse>> getProjectInnovationsByProjectId(
            @Parameter(description = "Project ID", example = "1")
            @RequestParam Long projectId) {
        List<ProjectInnovation> projectInnovations = projectInnovationUseCase.findProjectInnovationsByProjectId(projectId);
        List<ProjectInnovationResponse> response = projectInnovations.stream()
                .map(this::toResponse)
                .toList();
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "Activate project innovation")
    @PatchMapping("/activate")
    public ResponseEntity<ProjectInnovationResponse> activateProjectInnovation(
            @Parameter(description = "Innovation ID", example = "1")
            @RequestParam Long id) {
        try {
            ProjectInnovation activated = projectInnovationUseCase.activateProjectInnovation(id);
            return ResponseEntity.ok(toResponse(activated));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @Operation(summary = "Get project innovation info by project innovation ID")
    @GetMapping("/info")
    public ResponseEntity<List<ProjectInnovationInfoResponse>> getProjectInnovationInfo(
            @Parameter(description = "Project Innovation ID", example = "1")
            @RequestParam Long projectInnovationId) {
        List<ProjectInnovationInfo> infoList = projectInnovationUseCase
                .findProjectInnovationInfoByProjectInnovationId(projectInnovationId);
        List<ProjectInnovationInfoResponse> response = infoList.stream()
                .map(this::toInfoResponse)
                .toList();
        return ResponseEntity.ok(response);
    }
    
    @Operation(summary = "Get complete project innovation info by innovation ID and phase ID", 
               description = "Returns complete innovation information including actors data for the specific innovation and phase")
    @GetMapping("/info/complete")
    public ResponseEntity<ProjectInnovationInfoCompleteResponse> getProjectInnovationInfoByInnovationIdAndPhaseId(
            @Parameter(description = "Innovation ID", example = "1566")
            @RequestParam Long innovationId, 
            @Parameter(description = "Phase ID", example = "425")
            @RequestParam Long phaseId) {
        return projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(innovationId, phaseId)
                .map(info -> ResponseEntity.ok(toCompleteInfoResponse(info, innovationId, phaseId)))
                .orElse(ResponseEntity.notFound().build());
    }
    
    @Operation(summary = "Get all innovations by phase", 
               description = "Returns all project innovation info for a specific phase, including only active innovations")
    @GetMapping("/by-phase")
    public ResponseEntity<List<ProjectInnovationInfoResponse>> getInnovationsByPhase(
            @Parameter(description = "Phase ID to filter innovations", example = "1")
            @RequestParam Long phaseId) {
        List<ProjectInnovationInfo> innovations = projectInnovationUseCase.findProjectInnovationInfoByPhase(phaseId);
        List<ProjectInnovationInfoResponse> response = innovations.stream()
                .map(this::toInfoResponse)
                .toList();
        return ResponseEntity.ok(response);
    }
    
    private ProjectInnovationResponse toResponse(ProjectInnovation projectInnovation) {
        // Get actors associated with this innovation
        List<ProjectInnovationActors> actors = actorsService.findActiveActorsByInnovationId(projectInnovation.getId());
        List<ProjectInnovationActorsResponse> actorsResponse = actorsMapper.toResponseList(actors);
        
        return new ProjectInnovationResponse(
                projectInnovation.getId(),
                projectInnovation.getProjectId(),
                projectInnovation.getIsActive(),
                projectInnovation.getActiveSince(),
                projectInnovation.getCreatedBy(),
                projectInnovation.getModifiedBy(),
                projectInnovation.getModificationJustification(),
                actorsResponse
        );
    }
    
    private ProjectInnovationInfoResponse toInfoResponse(ProjectInnovationInfo info) {
        // Get actors for this innovation and phase
        List<ProjectInnovationActorsResponse> actorsResponse = List.of();
        if (info.getProjectInnovationId() != null && info.getIdPhase() != null) {
            List<ProjectInnovationActors> actors = actorsService.findActiveActorsByInnovationIdAndPhase(
                info.getProjectInnovationId(), info.getIdPhase().intValue());
            actorsResponse = actorsMapper.toResponseList(actors);
        }
        
        // Get associated ProjectInnovation information
        var projectInnovation = info.getProjectInnovationId() != null ? 
            projectInnovationUseCase.findProjectInnovationById(info.getProjectInnovationId()).orElse(null) : null;
        
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
                info.getPovertyJobsScoreId(),
                // New fields from endpoint 2
                projectInnovation != null ? projectInnovation.getProjectId() : null,
                projectInnovation != null ? projectInnovation.getIsActive() : null,
                projectInnovation != null ? projectInnovation.getActiveSince() : null,
                projectInnovation != null ? projectInnovation.getCreatedBy() : null,
                projectInnovation != null ? projectInnovation.getModifiedBy() : null,
                projectInnovation != null ? projectInnovation.getModificationJustification() : null,
                actorsResponse
        );
    }

    private ProjectInnovationInfoCompleteResponse toCompleteInfoResponse(ProjectInnovationInfo info, Long innovationId, Long phaseId) {
        // Load actors for specific innovation and phase
        List<ProjectInnovationActorsResponse> actorsResponse = List.of();
        if (innovationId != null && phaseId != null) {
            List<ProjectInnovationActors> actors = actorsService.findActiveActorsByInnovationIdAndPhase(innovationId, phaseId.intValue());
            actorsResponse = actorsMapper.toResponseList(actors);
        }
        
        return new ProjectInnovationInfoCompleteResponse(
                info.getId(),
                info.getProjectInnovationId(),
                info.getIdPhase(),
                getPhaseInfo(info.getIdPhase()),
                info.getYear(),
                info.getTitle(),
                info.getNarrative(),
                info.getPhaseResearchId(),
                getPhaseResearchInfo(info.getPhaseResearchId()),
                info.getStageInnovationId(),
                getInnovationStageInfo(info.getStageInnovationId()),
                info.getGeographicScopeId(),
                getGeographicScopeInfo(info.getGeographicScopeId()),
                info.getInnovationTypeId(),
                getInnovationTypeInfo(info.getInnovationTypeId()),
                info.getRepIndRegionId(),
                getRegionInfo(info.getRepIndRegionId()),
                info.getRepIndContributionCrpId(),
                getContributionCrpInfo(info.getRepIndContributionCrpId()),
                info.getRepIndDegreeInnovationId(),
                getDegreeInnovationInfo(info.getRepIndDegreeInnovationId()),
                info.getProjectExpectedStudiesId(),
                info.getDescriptionStage(),
                info.getEvidenceLink(),
                info.getGenderFocusLevelId(),
                getFocusLevelInfo(info.getGenderFocusLevelId()),
                info.getGenderExplanation(),
                info.getYouthFocusLevelId(),
                getFocusLevelInfo(info.getYouthFocusLevelId()),
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
                getInnovationNatureInfo(info.getInnovationNatureId()),
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
                getImpactAreaScoreInfo(info.getGenderScoreId()),
                info.getClimateChangeScoreId(),
                getImpactAreaScoreInfo(info.getClimateChangeScoreId()),
                info.getFoodSecurityScoreId(),
                getImpactAreaScoreInfo(info.getFoodSecurityScoreId()),
                info.getEnvironmentalScoreId(),
                getImpactAreaScoreInfo(info.getEnvironmentalScoreId()),
                info.getPovertyJobsScoreId(),
                getImpactAreaScoreInfo(info.getPovertyJobsScoreId()),
                actorsResponse
        );
    }
    
    // Helper methods to get information from related tables
    private PhaseDto getPhaseInfo(Long phaseId) {
        if (phaseId == null) return null;
        // For now we return test data, real queries can be implemented later
        return new PhaseDto(phaseId, "Phase " + phaseId);
    }
    
    private InnovationStageDto getInnovationStageInfo(Long stageId) {
        if (stageId == null) return null;
        // Data based on the query we made previously
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
        // For now we return test data, real queries can be implemented later
        return new GeographicScopeDto(scopeId, "Geographic Scope " + scopeId);
    }
    
    private InnovationTypeDto getInnovationTypeInfo(Long typeId) {
        if (typeId == null) return null;
        // Data based on the query we made previously
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
        // For now we return test data, real queries can be implemented later
        return new InstitutionDto(institutionId, "Institution " + institutionId, "INST" + institutionId);
    }
    
    private PhaseResearchDto getPhaseResearchInfo(Long phaseResearchId) {
        if (phaseResearchId == null) return null;
        String phaseName = switch (phaseResearchId.intValue()) {
            case 1 -> "Phase 1: Research (Discovery/Proof of Concept)";
            case 2 -> "Phase 2: Piloting";
            case 3 -> "Phase 3/4: Scaling up and scaling out";
            default -> "Phase Research " + phaseResearchId;
        };
        return new PhaseResearchDto(phaseResearchId, phaseName);
    }
    
    private RegionDto getRegionInfo(Long regionId) {
        if (regionId == null) return null;
        String regionName = switch (regionId.intValue()) {
            case 1 -> "Northern Africa";
            case 2 -> "Sub-Saharan Africa";
            case 3 -> "Latin America and the Caribbean";
            default -> "Region " + regionId;
        };
        return new RegionDto(regionId, regionName);
    }
    
    private ContributionCrpDto getContributionCrpInfo(Long contributionId) {
        if (contributionId == null) return null;
        String contributionName = switch (contributionId.intValue()) {
            case 1 -> "Sole Contribution";
            case 2 -> "Lead Contribution";
            case 3 -> "Partial Contribution";
            default -> "Contribution " + contributionId;
        };
        return new ContributionCrpDto(contributionId, contributionName);
    }
    
    private DegreeInnovationDto getDegreeInnovationInfo(Long degreeId) {
        if (degreeId == null) return null;
        String degreeName = switch (degreeId.intValue()) {
            case 1 -> "Novel";
            case 2 -> "Adaptive";
            default -> "Degree " + degreeId;
        };
        return new DegreeInnovationDto(degreeId, degreeName);
    }
    
    private FocusLevelDto getFocusLevelInfo(Long focusLevelId) {
        if (focusLevelId == null) return null;
        String focusName = switch (focusLevelId.intValue()) {
            case 1 -> "0 - Not Targeted";
            case 2 -> "1 - Significant";
            case 3 -> "2 - Principal";
            default -> "Focus Level " + focusLevelId;
        };
        return new FocusLevelDto(focusLevelId, focusName);
    }
    
    private InnovationNatureDto getInnovationNatureInfo(Long natureId) {
        if (natureId == null) return null;
        String natureName = switch (natureId.intValue()) {
            case 1 -> "Disruptive Innovation";
            case 2 -> "Incremental Innovation";
            case 3 -> "Radical Innovation";
            default -> "Innovation Nature " + natureId;
        };
        return new InnovationNatureDto(natureId, natureName);
    }
    
    private ImpactAreaScoreDto getImpactAreaScoreInfo(Long scoreId) {
        if (scoreId == null) return null;
        // For now we return test data, real queries can be implemented later
        return new ImpactAreaScoreDto(scoreId, "Impact Score " + scoreId, "Description for score " + scoreId);
    }
}