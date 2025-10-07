package com.example.demo.modules.projectinnovation.adapters.rest;

import com.example.demo.modules.projectinnovation.adapters.rest.dto.*;
import com.example.demo.modules.projectinnovation.adapters.rest.mapper.ProjectInnovationActorsMapper;
import com.example.demo.modules.projectinnovation.adapters.outbound.persistence.ProjectInnovationRepositoryAdapter;
import com.example.demo.modules.projectinnovation.application.port.inbound.ProjectInnovationUseCase;
import com.example.demo.modules.projectinnovation.application.service.ProjectInnovationActorsService;
import com.example.demo.modules.projectinnovation.domain.model.*;

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
    private final ProjectInnovationRepositoryAdapter repositoryAdapter;
    
    public ProjectInnovationController(
            ProjectInnovationUseCase projectInnovationUseCase,
            ProjectInnovationActorsService actorsService,
            ProjectInnovationActorsMapper actorsMapper,
            ProjectInnovationRepositoryAdapter repositoryAdapter) {
        this.projectInnovationUseCase = projectInnovationUseCase;
        this.actorsService = actorsService;
        this.actorsMapper = actorsMapper;
        this.repositoryAdapter = repositoryAdapter;
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
    
    @Deprecated(since = "1.0", forRemoval = true)
    @Operation(summary = "Get all project innovations including inactive ones", 
               description = "⚠️ DEPRECATED: Use /search instead (only returns active records). This endpoint returns both active and inactive project innovations")
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
    
    @Operation(summary = "Get complete project innovation info by innovation ID and phase ID", 
               description = "Returns complete innovation information including actors, SDGs, regions, organizations, and external partners for the specific innovation and phase")
    @GetMapping("/info")
    public ResponseEntity<ProjectInnovationInfoCompleteWithRelationsResponse> getProjectInnovationInfoByInnovationIdAndPhaseId(
            @Parameter(description = "Innovation ID", example = "1566")
            @RequestParam Long innovationId, 
            @Parameter(description = "Phase ID", example = "425")
            @RequestParam Long phaseId) {
        return projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(innovationId, phaseId)
                .map(info -> ResponseEntity.ok(toCompleteInfoWithRelationsResponse(info, innovationId, phaseId)))
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
    
    @Operation(summary = "Search innovations with advanced filters", 
               description = "Returns active innovations with complete information including actors, with optional filters by phase, readiness scale, innovation type, and SDG. Only returns active records. Includes total count of innovations found.")
    @GetMapping("/search")
    public ResponseEntity<ProjectInnovationSearchResponse> searchInnovations(
            @Parameter(description = "Phase ID to filter by", example = "425")
            @RequestParam(required = false) Long phase,
            @Parameter(description = "Readiness scale to filter by", example = "7")
            @RequestParam(required = false) Integer readinessScale,
            @Parameter(description = "Innovation type ID to filter by", example = "1")
            @RequestParam(required = false) Long innovationTypeId,
            @Parameter(description = "Innovation ID to filter by (for SDG search)", example = "1566")
            @RequestParam(required = false) Long innovationId,
            @Parameter(description = "SDG ID to filter by", example = "2")
            @RequestParam(required = false) Long sdgId) {
        
        // Get innovation info with filters instead of just innovation entities
        List<ProjectInnovationInfo> innovations;
        String searchType;
        
        // If any SDG-related filter is provided, use SDG search
        if (sdgId != null || (innovationId != null && phase != null)) {
            innovations = projectInnovationUseCase.findActiveInnovationsInfoBySdgFilters(innovationId, phase, sdgId);
            searchType = "SDG_FILTERS";
        }
        // If any general filter is provided, use general search
        else if (phase != null || readinessScale != null || innovationTypeId != null) {
            innovations = projectInnovationUseCase.findActiveInnovationsInfoWithFilters(phase, readinessScale, innovationTypeId);
            searchType = "GENERAL_FILTERS";
        }
        // If no filters provided, return all active innovations with info
        else {
            innovations = projectInnovationUseCase.findAllActiveInnovationsInfo();
            searchType = "ALL_ACTIVE";
        }
        
        List<ProjectInnovationInfoResponse> response = innovations.stream()
                .map(this::toInfoResponse)
                .toList();
        
        // Create filters metadata
        ProjectInnovationSearchResponse.SearchFilters appliedFilters = 
            new ProjectInnovationSearchResponse.SearchFilters(
                phase, readinessScale, innovationTypeId, innovationId, sdgId, searchType
            );
        
        ProjectInnovationSearchResponse searchResponse = 
            ProjectInnovationSearchResponse.of(response, appliedFilters);
            
        return ResponseEntity.ok(searchResponse);
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
    
    private ProjectInnovationSdgResponse toSdgResponse(ProjectInnovationSdg sdg) {
        String sdgShortName = "SDG " + sdg.getSdgId();
        String sdgFullName = getSdgFullName(sdg.getSdgId());
        return new ProjectInnovationSdgResponse(
                sdg.getId(),
                sdg.getInnovationId(),
                sdg.getSdgId(),
                sdgShortName,
                sdgFullName,
                sdg.getIdPhase(),
                sdg.getIsActive()
        );
    }
    
    private ProjectInnovationRegionResponse toRegionResponse(ProjectInnovationRegion region) {
        String regionName = getRegionNameById(region.getIdRegion());
        return new ProjectInnovationRegionResponse(
                region.getId(),
                region.getProjectInnovationId(),
                region.getIdRegion(),
                regionName,
                region.getIdPhase()
        );
    }
    
    private ProjectInnovationCountryResponse toCountryResponse(ProjectInnovationCountry country) {
        String countryName = getCountryNameById(country.getIdCountry());
        return new ProjectInnovationCountryResponse(
                country.getId(),
                country.getProjectInnovationId(),
                country.getIdCountry(),
                countryName,
                country.getIdPhase()
        );
    }
    
    private ProjectInnovationOrganizationResponse toOrganizationResponse(ProjectInnovationOrganization organization) {
        String organizationTypeName = getOrganizationTypeName(organization.getRepIndOrganizationTypeId());
        return new ProjectInnovationOrganizationResponse(
                organization.getId(),
                organization.getProjectInnovationId(),
                organization.getIdPhase(),
                organization.getRepIndOrganizationTypeId(),
                organizationTypeName
        );
    }
    
    private ProjectInnovationPartnershipResponse toPartnershipResponse(
            ProjectInnovationPartnership partnership, 
            List<Institution> institutions, 
            List<ProjectInnovationPartnershipPerson> contactPersons) {
        
        Institution institution = institutions.stream()
                .filter(inst -> inst.getId().equals(partnership.getInstitutionId()))
                .findFirst()
                .orElse(null);
        
        List<ContactPersonResponse> contacts = contactPersons.stream()
                .filter(contact -> contact.getPartnershipId().equals(partnership.getId()))
                .map(this::toContactPersonResponse)
                .toList();
        
        String partnerTypeName = getPartnerTypeName(partnership.getInnovationPartnerTypeId());
        
        return new ProjectInnovationPartnershipResponse(
                partnership.getId(),
                partnership.getProjectInnovationId(),
                partnership.getInstitutionId(),
                institution != null ? institution.getName() : "Unknown Institution",
                institution != null ? institution.getAcronym() : "",
                institution != null ? institution.getWebsiteLink() : "",
                partnership.getIdPhase(),
                partnership.getInnovationPartnerTypeId(),
                partnerTypeName,
                partnership.getIsActive(),
                partnership.getActiveSince(),
                contacts
        );
    }
    
    private ContactPersonResponse toContactPersonResponse(ProjectInnovationPartnershipPerson person) {
        // For now returning basic data - user info would require additional queries
        return new ContactPersonResponse(
                person.getId(),
                person.getPartnershipId(),
                person.getUserId(),
                "User " + person.getUserId(), // userName - would need user table
                "user" + person.getUserId() + "@example.com", // userEmail - would need user table
                person.getIsActive(),
                person.getActiveSince()
        );
    }
    
    private String getSdgFullName(Long sdgId) {
        if (sdgId == null) return null;
        return switch (sdgId.intValue()) {
            case 1 -> "No Poverty";
            case 2 -> "Zero Hunger";
            case 3 -> "Good Health and Well-being";
            case 4 -> "Quality Education";
            case 5 -> "Gender Equality";
            case 6 -> "Clean Water and Sanitation";
            case 7 -> "Affordable and Clean Energy";
            case 8 -> "Decent Work and Economic Growth";
            case 9 -> "Industry, Innovation and Infrastructure";
            case 10 -> "Reduced Inequality";
            case 11 -> "Sustainable Cities and Communities";
            case 12 -> "Responsible Consumption and Production";
            case 13 -> "Climate Action";
            case 14 -> "Life Below Water";
            case 15 -> "Life on Land";
            case 16 -> "Peace and Justice Strong Institutions";
            case 17 -> "Partnerships to achieve the Goal";
            default -> "SDG " + sdgId;
        };
    }
    
    private String getRegionNameById(Long regionId) {
        if (regionId == null) return null;
        return switch (regionId.intValue()) {
            case 1 -> "Northern Africa";
            case 2 -> "Sub-Saharan Africa";
            case 3 -> "Latin America and the Caribbean";
            case 4 -> "Northern America";
            case 5 -> "Central Asia";
            case 6 -> "Eastern Asia";
            case 7 -> "South-eastern Asia";
            case 8 -> "Southern Asia";
            case 9 -> "Western Asia";
            case 10 -> "Eastern Europe";
            case 11 -> "Northern Europe";
            case 12 -> "Southern Europe";
            case 13 -> "Western Europe";
            case 14 -> "Australia and New Zealand";
            case 15 -> "Melanesia";
            case 16 -> "Micronesia";
            case 17 -> "Polynesia";
            default -> "Region " + regionId;
        };
    }
    
    private String getOrganizationTypeName(Long organizationTypeId) {
        if (organizationTypeId == null) return null;
        return switch (organizationTypeId.intValue()) {
            case 1 -> "Government Agency";
            case 2 -> "Private Sector";
            case 3 -> "NGO/Civil Society";
            case 4 -> "Academic Institution";
            case 5 -> "International Organization";
            default -> "Organization Type " + organizationTypeId;
        };
    }
    
    private String getPartnerTypeName(Long partnerTypeId) {
        if (partnerTypeId == null) return null;
        return switch (partnerTypeId.intValue()) {
            case 1 -> "Leading Partner";
            case 2 -> "Implementing Partner";
            case 3 -> "Supporting Partner";
            case 4 -> "Co-funding Partner";
            default -> "Partner Type " + partnerTypeId;
        };
    }
    
    private String getCountryNameById(Long countryId) {
        if (countryId == null) return null;
        // Based on loc_elements table data - more comprehensive country mapping
        return switch (countryId.intValue()) {
            case 113 -> "Kenya";
            case 112 -> "Japan";
            case 111 -> "Jordan";
            case 110 -> "Jamaica";
            case 114 -> "Kyrgyzstan";
            case 115 -> "Cambodia";
            // Add more countries as needed - these are the most common ones
            // For production, this should query the loc_elements table
            default -> "Country " + countryId;
        };
    }
    
    private ProjectInnovationInfoCompleteWithRelationsResponse toCompleteInfoWithRelationsResponse(ProjectInnovationInfo info, Long innovationId, Long phaseId) {
        // Get actors data
        List<ProjectInnovationActors> actors = actorsService.findActiveActorsByInnovationIdAndPhase(innovationId, phaseId.intValue());
        List<ProjectInnovationActorsResponse> actorsResponse = actors.stream()
                .map(actorsMapper::toResponse)
                .toList();
        
        // Get SDGs
        List<ProjectInnovationSdg> sdgs = repositoryAdapter.findSdgsByInnovationIdAndPhase(innovationId, phaseId);
        List<ProjectInnovationSdgResponse> sdgsResponse = sdgs.stream()
                .map(this::toSdgResponse)
                .toList();
        
        // Get Regions
        List<ProjectInnovationRegion> regions = repositoryAdapter.findRegionsByInnovationIdAndPhase(innovationId, phaseId);
        List<ProjectInnovationRegionResponse> regionsResponse = regions.stream()
                .map(this::toRegionResponse)
                .toList();
        
        // Get Countries - filtered by phase
        List<ProjectInnovationCountry> countries = repositoryAdapter.findCountriesByInnovationIdAndPhase(innovationId, phaseId);
        List<ProjectInnovationCountryResponse> countriesResponse = countries.stream()
                .map(this::toCountryResponse)
                .toList();
        
        // Get Organizations
        List<ProjectInnovationOrganization> organizations = repositoryAdapter.findOrganizationsByInnovationIdAndPhase(innovationId, phaseId);
        List<ProjectInnovationOrganizationResponse> organizationsResponse = organizations.stream()
                .map(this::toOrganizationResponse)
                .toList();
        
        // Get Partnerships (External Partners)
        List<ProjectInnovationPartnership> partnerships = repositoryAdapter.findPartnershipsByInnovationIdAndPhase(innovationId, phaseId);
        List<Long> institutionIds = partnerships.stream()
                .map(ProjectInnovationPartnership::getInstitutionId)
                .toList();
        List<Institution> institutions = repositoryAdapter.findInstitutionsByIds(institutionIds);
        
        List<Long> partnershipIds = partnerships.stream()
                .map(ProjectInnovationPartnership::getId)
                .toList();
        List<ProjectInnovationPartnershipPerson> contactPersons = repositoryAdapter.findContactPersonsByPartnershipIds(partnershipIds);
        
        List<ProjectInnovationPartnershipResponse> partnershipsResponse = partnerships.stream()
                .map(partnership -> toPartnershipResponse(partnership, institutions, contactPersons))
                .toList();
        
        return new ProjectInnovationInfoCompleteWithRelationsResponse(
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
                info.getNumberOfInnovations() != null ? Math.toIntExact(info.getNumberOfInnovations()) : null,
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
                // Expanded objects
                getPhaseInfo(info.getIdPhase()),
                getInnovationStageInfo(info.getStageInnovationId()),
                getGeographicScopeInfo(info.getGeographicScopeId()),
                getInnovationTypeInfo(info.getInnovationTypeId()),
                getRegionInfo(info.getRepIndRegionId()),
                getContributionCrpInfo(info.getRepIndContributionCrpId()),
                getDegreeInnovationInfo(info.getRepIndDegreeInnovationId()),
                getInstitutionInfo(info.getLeadOrganizationId()),
                getFocusLevelInfo(info.getGenderFocusLevelId()),
                getFocusLevelInfo(info.getYouthFocusLevelId()),
                getInstitutionInfo(info.getIntellectualPropertyInstitutionId()),
                getPhaseResearchInfo(info.getPhaseResearchId()),
                innovationId,  // projectId
                true,  // isActive (assumed from findByInnovationIdAndPhase result)
                LocalDateTime.now(),  // activeSince (default)
                null,  // createdBy
                null,  // modifiedBy
                null,  // modificationJustification
                actorsResponse,
                // NEW RELATIONSHIPS
                sdgsResponse,
                regionsResponse,
                countriesResponse,
                organizationsResponse,
                partnershipsResponse
        );
    }
}