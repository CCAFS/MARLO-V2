package com.example.demo.modules.projectinnovation.adapters.rest;

import com.example.demo.modules.innovationtype.adapters.rest.dto.InnovationTypeResponse;
import com.example.demo.modules.innovationtype.adapters.outbound.persistence.InnovationTypeRepositoryAdapter;
import com.example.demo.modules.projectinnovation.adapters.rest.dto.*;
import com.example.demo.modules.projectinnovation.adapters.rest.mapper.ProjectInnovationActorsMapper;
import com.example.demo.modules.projectinnovation.adapters.outbound.persistence.ProjectInnovationRepositoryAdapter;
import com.example.demo.modules.projectinnovation.adapters.outbound.persistence.LocElementJpaRepository;
import com.example.demo.modules.sustainabledevelopmentgoals.adapters.outbound.persistence.SustainableDevelopmentGoalJpaRepository;
import com.example.demo.modules.projectinnovation.application.port.inbound.ProjectInnovationUseCase;
import com.example.demo.modules.projectinnovation.application.service.ProjectInnovationActorsService;
import com.example.demo.modules.projectinnovation.domain.model.*;
import java.util.Collections;

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
    private final LocElementJpaRepository locElementRepository;
    private final InnovationTypeRepositoryAdapter innovationTypeRepository;
    private final SustainableDevelopmentGoalJpaRepository sdgRepository;
    
    public ProjectInnovationController(
            ProjectInnovationUseCase projectInnovationUseCase,
            ProjectInnovationActorsService actorsService,
            ProjectInnovationActorsMapper actorsMapper,
            ProjectInnovationRepositoryAdapter repositoryAdapter,
            LocElementJpaRepository locElementRepository,
            InnovationTypeRepositoryAdapter innovationTypeRepository,
            SustainableDevelopmentGoalJpaRepository sdgRepository) {
        this.projectInnovationUseCase = projectInnovationUseCase;
        this.actorsService = actorsService;
        this.actorsMapper = actorsMapper;
        this.repositoryAdapter = repositoryAdapter;
        this.locElementRepository = locElementRepository;
        this.innovationTypeRepository = innovationTypeRepository;
        this.sdgRepository = sdgRepository;
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
    
    @Operation(
        summary = "Get complete project innovation information with all relationships", 
        description = """
            Returns comprehensive innovation details including:
            • Basic innovation information (title, narrative, stage, etc.)
            • Countries: Geographic countries where innovation is implemented
            • Regions: Geographic regions covered by the innovation  
            • SDGs: Sustainable Development Goals addressed by the innovation
            • Organizations: Contact organizations involved in the innovation
            • External Partners: Partner institutions with contact persons
            
            All relationships are filtered by the specified phase to show only relevant data for that phase.
            """,
        tags = {"Innovation Information"}
    )
    @GetMapping("/info")
    public ResponseEntity<InnovationInfo> getProjectInnovationInfoByInnovationIdAndPhaseId(
            @Parameter(description = "Innovation ID to retrieve information for", example = "1566", required = true)
            @RequestParam Long innovationId, 
            @Parameter(description = "Phase ID to filter relationships by", example = "428", required = true)
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
               description = "Returns active innovations with complete information including actors, with optional filters by phase, readiness scale, innovation type, and SDG. Only returns active records. Supports pagination with offset and limit parameters.")
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
            @RequestParam(required = false) Long sdgId,
            @Parameter(description = "Number of records to skip (pagination)", example = "0")
            @RequestParam(required = false, defaultValue = "0") Integer offset,
            @Parameter(description = "Maximum number of records to return (pagination)", example = "20")
            @RequestParam(required = false, defaultValue = "20") Integer limit) {
        
        // Validate pagination parameters
        if (offset < 0) offset = 0;
        if (limit <= 0) limit = 20;
        if (limit > 100) limit = 100; // Maximum limit to prevent performance issues
        
        // Get innovation info with filters instead of just innovation entities
        List<ProjectInnovationInfo> allInnovations;
        String searchType;
        
        // If any SDG-related filter is provided, use SDG search
        if (sdgId != null || (innovationId != null && phase != null)) {
            allInnovations = projectInnovationUseCase.findActiveInnovationsInfoBySdgFilters(innovationId, phase, sdgId);
            searchType = "SDG_FILTERS";
        }
        // If any general filter is provided, use general search
        else if (phase != null || readinessScale != null || innovationTypeId != null) {
            allInnovations = projectInnovationUseCase.findActiveInnovationsInfoWithFilters(phase, readinessScale, innovationTypeId);
            searchType = "GENERAL_FILTERS";
        }
        // If no filters provided, return all active innovations with info
        else {
            allInnovations = projectInnovationUseCase.findAllActiveInnovationsInfo();
            searchType = "ALL_ACTIVE";
        }
        
        // Get total count before pagination
        int totalCount = allInnovations.size();
        
        // Apply pagination
        List<ProjectInnovationInfo> paginatedInnovations = allInnovations.stream()
                .skip(offset)
                .limit(limit)
                .toList();
        
        List<ProjectInnovationInfoResponse> response = paginatedInnovations.stream()
                .map(this::toInfoResponse)
                .toList();
        
        // Create filters metadata
        ProjectInnovationSearchResponse.SearchFilters appliedFilters = 
            new ProjectInnovationSearchResponse.SearchFilters(
                phase, readinessScale, innovationTypeId, innovationId, sdgId, searchType
            );
        
        // Create pagination metadata
        ProjectInnovationSearchResponse.PaginationInfo pagination = 
            ProjectInnovationSearchResponse.PaginationInfo.of(offset, limit, totalCount);
        
        ProjectInnovationSearchResponse searchResponse = 
            ProjectInnovationSearchResponse.of(response, totalCount, appliedFilters, pagination);
            
        return ResponseEntity.ok(searchResponse);
    }
    
    @Operation(summary = "Search innovations with simplified response", 
               description = "Returns active innovations with essential fields only including phase, innovation type, SDGs, regions and countries. Optimized for performance with minimal data transfer. Supports pagination with offset and limit parameters.")
    @GetMapping("/search-simple")
    public ResponseEntity<ProjectInnovationSimpleSearchResponse> searchInnovationsSimple(
            @Parameter(description = "Phase ID to filter by", example = "425")
            @RequestParam(required = false) Long phase,
            @Parameter(description = "Readiness scale to filter by", example = "7")
            @RequestParam(required = false) Integer readinessScale,
            @Parameter(description = "Innovation type ID to filter by", example = "1")
            @RequestParam(required = false) Long innovationTypeId,
            @Parameter(description = "Innovation ID to filter by (for SDG search)", example = "1566")
            @RequestParam(required = false) Long innovationId,
            @Parameter(description = "SDG ID to filter by", example = "2")
            @RequestParam(required = false) Long sdgId,
            @Parameter(description = "Number of records to skip (pagination)", example = "0")
            @RequestParam(required = false, defaultValue = "0") Integer offset,
            @Parameter(description = "Maximum number of records to return (pagination)", example = "20")
            @RequestParam(required = false, defaultValue = "20") Integer limit) {
        
        // Validate pagination parameters
        if (offset < 0) offset = 0;
        if (limit <= 0) limit = 20;
        if (limit > 100) limit = 100; // Maximum limit to prevent performance issues
        
        // Get innovation info with filters (reusing existing logic)
        List<ProjectInnovationInfo> allInnovations;
        String searchType;
        
        // If any SDG-related filter is provided, use SDG search
        if (sdgId != null || (innovationId != null && phase != null)) {
            allInnovations = projectInnovationUseCase.findActiveInnovationsInfoBySdgFilters(innovationId, phase, sdgId);
            searchType = "SDG_FILTERS";
        }
        // If any general filter is provided, use general search
        else if (phase != null || readinessScale != null || innovationTypeId != null) {
            allInnovations = projectInnovationUseCase.findActiveInnovationsInfoWithFilters(phase, readinessScale, innovationTypeId);
            searchType = "GENERAL_FILTERS";
        }
        // If no filters provided, return all active innovations with info
        else {
            allInnovations = projectInnovationUseCase.findAllActiveInnovationsInfo();
            searchType = "ALL_ACTIVE";
        }
        
        // Get total count before pagination
        int totalCount = allInnovations.size();
        
        // Apply pagination
        List<ProjectInnovationInfo> paginatedInnovations = allInnovations.stream()
                .skip(offset)
                .limit(limit)
                .toList();
        
        List<ProjectInnovationSimpleResponse> response = paginatedInnovations.stream()
                .map(this::toSimpleResponse)
                .toList();
        
        // Create filters metadata
        ProjectInnovationSimpleSearchResponse.SearchFilters appliedFilters = 
            new ProjectInnovationSimpleSearchResponse.SearchFilters(
                phase, readinessScale, innovationTypeId, innovationId, sdgId, searchType
            );
        
        // Create pagination metadata
        ProjectInnovationSimpleSearchResponse.PaginationInfo pagination = 
            ProjectInnovationSimpleSearchResponse.PaginationInfo.of(offset, limit, totalCount);
        
        ProjectInnovationSimpleSearchResponse searchResponse = 
            ProjectInnovationSimpleSearchResponse.of(response, totalCount, appliedFilters, pagination);
            
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
                info.getKnowledgeResultsNarrative(),
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
    
    private ProjectInnovationSimpleResponse toSimpleResponse(ProjectInnovationInfo info) {
        // Get associated ProjectInnovation for isActive and projectId
        var projectInnovation = info.getProjectInnovationId() != null ? 
            projectInnovationUseCase.findProjectInnovationById(info.getProjectInnovationId()).orElse(null) : null;
            
        // Create phase info (basic implementation - could be enhanced with real data)
        ProjectInnovationSimpleResponse.PhaseDto phaseInfo = info.getIdPhase() != null ?
            new ProjectInnovationSimpleResponse.PhaseDto(info.getIdPhase(), String.valueOf(info.getYear())) : null;
            
        // Create innovation type info (basic implementation - could be enhanced with real data)  
        InnovationTypeResponse.Simple innovationTypeInfo = info.getInnovationTypeId() != null ?
            new InnovationTypeResponse.Simple(
                info.getInnovationTypeId(), 
                getInnovationTypeNameById(info.getInnovationTypeId()), 
                false
            ) : null;
        
        // Get related entities - real data from database
        List<SdgDto> sdgs = getSdgsForInnovation(info.getProjectInnovationId(), info.getIdPhase());
        List<RegionDto> regions = getRegionsForInnovation(info.getProjectInnovationId(), info.getIdPhase());
        List<CountryDto> countries = getCountriesForInnovation(info.getProjectInnovationId(), info.getIdPhase());
        
        return new ProjectInnovationSimpleResponse(
            info.getId(),
            info.getProjectInnovationId(),
            info.getIdPhase(),
            info.getYear(),
            info.getTitle(),
            info.getNarrative(),
            info.getInnovationTypeId(),
            info.getInnovationNatureId(),
            info.getReadinessScale(),
            phaseInfo,
            innovationTypeInfo,
            projectInnovation != null ? projectInnovation.getProjectId() : null,
            projectInnovation != null ? projectInnovation.getIsActive() : true,
            sdgs,
            regions,
            countries
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
    
    private String getInnovationTypeNameById(Long typeId) {
        if (typeId == null) return "Unknown";
        
        try {
            // Get innovation type from rep_ind_innovation_types table
            return innovationTypeRepository.findById(typeId)
                .map(com.example.demo.modules.innovationtype.domain.model.InnovationType::getName)
                .orElse("Innovation Type " + typeId);
        } catch (Exception e) {
            // Fallback to hardcoded values if database query fails
            return switch (typeId.intValue()) {
                case 1 -> "Genetic (varieties and breeds)";
                case 2 -> "Production systems and Management practices";
                case 3 -> "Social Science";
                case 4 -> "Biophysical Research";
                case 5 -> "Research and Communication Methodologies and Tools";
                default -> "Innovation Type " + typeId;
            };
        }
    }
    
    private List<SdgDto> getSdgsForInnovation(Long innovationId, Long phaseId) {
        if (innovationId == null || phaseId == null) return Collections.emptyList();
        
        try {
            List<ProjectInnovationSdg> sdgs = repositoryAdapter.findSdgsByInnovationIdAndPhase(innovationId, phaseId);
            return sdgs.stream()
                    .map(sdg -> new SdgDto(
                        sdg.getSdgId(), 
                        getSdgShortNameById(sdg.getSdgId()),
                        getSdgFullNameById(sdg.getSdgId())
                    ))
                    .toList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
    
    private List<RegionDto> getRegionsForInnovation(Long innovationId, Long phaseId) {
        if (innovationId == null || phaseId == null) return Collections.emptyList();
        
        try {
            List<ProjectInnovationRegion> regions = repositoryAdapter.findRegionsByInnovationIdAndPhase(innovationId, phaseId);
            return regions.stream()
                    .map(region -> new RegionDto(
                        region.getIdRegion(), 
                        getRegionNameById(region.getIdRegion())
                    ))
                    .toList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
    
    private List<CountryDto> getCountriesForInnovation(Long innovationId, Long phaseId) {
        if (innovationId == null || phaseId == null) return Collections.emptyList();
        
        try {
            List<ProjectInnovationCountry> countries = repositoryAdapter.findCountriesByInnovationIdAndPhase(innovationId, phaseId);
            return countries.stream()
                    .map(country -> new CountryDto(
                        country.getIdCountry(), 
                        getCountryNameById(country.getIdCountry())
                    ))
                    .toList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
    
    private String getSdgShortNameById(Long sdgId) {
        if (sdgId == null) return "Unknown SDG";
        
        try {
            // Get SDG from sustainable_development_goals table
            return sdgRepository.findBySmoCode(sdgId)
                .map(com.example.demo.modules.sustainabledevelopmentgoals.domain.model.SustainableDevelopmentGoal::getShortName)
                .orElse("SDG " + sdgId);
        } catch (Exception e) {
            // Fallback to hardcoded values if database query fails
            return switch (sdgId.intValue()) {
                case 1 -> "No Poverty";
                case 2 -> "Zero Hunger"; 
                case 3 -> "Good Health";
                case 4 -> "Quality Education";
                case 5 -> "Gender Equality";
                case 6 -> "Clean Water";
                case 7 -> "Affordable Energy";
                case 8 -> "Decent Work";
                case 9 -> "Innovation";
                case 10 -> "Reduced Inequalities";
                case 11 -> "Sustainable Cities";
                case 12 -> "Responsible Consumption";
                case 13 -> "Climate Action";
                case 14 -> "Life Below Water";
                case 15 -> "Life on Land";
                case 16 -> "Peace and Justice";
                case 17 -> "Partnerships";
                default -> "SDG " + sdgId;
            };
        }
    }
    
    private String getSdgFullNameById(Long sdgId) {
        if (sdgId == null) return "Unknown SDG";
        
        try {
            // Get SDG from sustainable_development_goals table
            return sdgRepository.findBySmoCode(sdgId)
                .map(com.example.demo.modules.sustainabledevelopmentgoals.domain.model.SustainableDevelopmentGoal::getFullName)
                .orElse("SDG " + sdgId + " - Full name not available");
        } catch (Exception e) {
            // Fallback to hardcoded values if database query fails
            return switch (sdgId.intValue()) {
                case 1 -> "End poverty in all its forms everywhere";
                case 2 -> "End hunger, achieve food security and improved nutrition and promote sustainable agriculture";
                case 3 -> "Ensure healthy lives and promote well-being for all at all ages";
                case 4 -> "Ensure inclusive and equitable quality education and promote lifelong learning opportunities for all";
                case 5 -> "Achieve gender equality and empower all women and girls";
                case 6 -> "Ensure availability and sustainable management of water and sanitation for all";
                case 7 -> "Ensure access to affordable, reliable, sustainable and modern energy for all";
                case 8 -> "Promote sustained, inclusive and sustainable economic growth, full and productive employment and decent work for all";
                case 9 -> "Build resilient infrastructure, promote inclusive and sustainable industrialization and foster innovation";
                case 10 -> "Reduce inequality within and among countries";
                case 11 -> "Make cities and human settlements inclusive, safe, resilient and sustainable";
                case 12 -> "Ensure sustainable consumption and production patterns";
                case 13 -> "Take urgent action to combat climate change and its impacts";
                case 14 -> "Conserve and sustainably use the oceans, seas and marine resources for sustainable development";
                case 15 -> "Protect, restore and promote sustainable use of terrestrial ecosystems, sustainably manage forests, combat desertification, and halt and reverse land degradation and halt biodiversity loss";
                case 16 -> "Promote peaceful and inclusive societies for sustainable development, provide access to justice for all and build effective, accountable and inclusive institutions at all levels";
                case 17 -> "Strengthen the means of implementation and revitalize the Global Partnership for Sustainable Development";
                default -> "SDG " + sdgId + " - Full name not available";
            };
        }
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
    
    private ProjectInnovationContributingOrganizationResponse toContributingOrganizationResponse(
            ProjectInnovationContributingOrganization contributingOrg, 
            List<Institution> institutions) {
        
        Institution institution = institutions.stream()
                .filter(inst -> inst.getId().equals(contributingOrg.getInstitutionId()))
                .findFirst()
                .orElse(null);
        
        String institutionName = institution != null ? institution.getName() : "Institution " + contributingOrg.getInstitutionId();
        String institutionAcronym = institution != null ? institution.getAcronym() : null;
        
        return new ProjectInnovationContributingOrganizationResponse(
                contributingOrg.getId(),
                contributingOrg.getProjectInnovationId(),
                contributingOrg.getIdPhase(),
                contributingOrg.getInstitutionId(),
                institutionName,
                institutionAcronym
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
        // Get user information from users table
        User user = repositoryAdapter.findUserById(person.getUserId()).orElse(null);
        
        String userName = "Unknown User";
        String userEmail = "unknown@example.com";
        
        if (user != null) {
            // Construct full name from firstName and lastName
            StringBuilder nameBuilder = new StringBuilder();
            if (user.getFirstName() != null && !user.getFirstName().trim().isEmpty()) {
                nameBuilder.append(user.getFirstName().trim());
            }
            if (user.getLastName() != null && !user.getLastName().trim().isEmpty()) {
                if (nameBuilder.length() > 0) {
                    nameBuilder.append(" ");
                }
                nameBuilder.append(user.getLastName().trim());
            }
            
            userName = nameBuilder.length() > 0 ? nameBuilder.toString() : 
                      (user.getUsername() != null ? user.getUsername() : "User " + person.getUserId());
            userEmail = user.getEmail() != null ? user.getEmail() : "user" + person.getUserId() + "@example.com";
        }
        
        return new ContactPersonResponse(
                person.getId(),
                person.getPartnershipId(),
                person.getUserId(),
                userName,
                userEmail,
                person.getIsActive(),
                person.getActiveSince()
        );
    }
    
    private String getSdgFullName(Long sdgId) {
        if (sdgId == null) return null;
        
        try {
            // Get SDG from sustainable_development_goals table 
            return sdgRepository.findBySmoCode(sdgId)
                .map(com.example.demo.modules.sustainabledevelopmentgoals.domain.model.SustainableDevelopmentGoal::getShortName)
                .orElse("SDG " + sdgId);
        } catch (Exception e) {
            // Fallback to hardcoded values if database query fails
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
    }
    
    private String getRegionNameById(Long regionId) {
        if (regionId == null) return null;
        try {
            return locElementRepository.findActiveById(regionId)
                    .map(LocElement::getName)
                    .orElse("Region " + regionId);
        } catch (Exception e) {
            // Fallback in case of database error
            return "Region " + regionId;
        }
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
        try {
            return locElementRepository.findActiveById(countryId)
                    .map(LocElement::getName)
                    .orElse("Country " + countryId);
        } catch (Exception e) {
            // Fallback in case of database error
            return "Country " + countryId;
        }
    }
    
    private InnovationInfo toCompleteInfoWithRelationsResponse(ProjectInnovationInfo info, Long innovationId, Long phaseId) {
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
        
        // Get Contributing Organizations
        List<ProjectInnovationContributingOrganization> contributingOrganizations = repositoryAdapter.findContributingOrganizationsByInnovationIdAndPhase(innovationId, phaseId);
        List<Long> contributingInstitutionIds = contributingOrganizations.stream()
                .map(ProjectInnovationContributingOrganization::getInstitutionId)
                .toList();
        List<Institution> contributingInstitutions = contributingInstitutionIds.isEmpty() ? 
                Collections.emptyList() : repositoryAdapter.findInstitutionsByIds(contributingInstitutionIds);
        List<ProjectInnovationContributingOrganizationResponse> contributingOrganizationsResponse = contributingOrganizations.stream()
                .map(org -> toContributingOrganizationResponse(org, contributingInstitutions))
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
        
        return new InnovationInfo(
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
                info.getKnowledgeResultsNarrative(),
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
                partnershipsResponse,
                contributingOrganizationsResponse
        );
    }
    
    @Operation(summary = "Get innovation and country statistics by phase",
               description = "Returns count of active innovations and unique countries for a specific phase. Provides aggregate statistics for all innovations within the specified phase.")
    @GetMapping("/stats")
    public ResponseEntity<InnovationCountryStatsResponse> getInnovationCountryStats(
            @Parameter(description = "Phase ID to get statistics for", example = "428", required = true)
            @RequestParam Long phaseId) {
        
        try {
            // Get count of unique countries for this phase (all innovations)
            Long countryCount = repositoryAdapter.countDistinctCountries(null, phaseId);
            
            // Get count of unique innovations for this phase
            Long innovationCount = repositoryAdapter.countDistinctInnovations(null, phaseId);
            
            InnovationCountryStatsResponse response = InnovationCountryStatsResponse.of(
                    innovationCount.intValue(),
                    countryCount.intValue(),
                    null, // No longer filtering by specific innovation
                    phaseId
            );
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            // Return empty stats in case of error
            InnovationCountryStatsResponse errorResponse = InnovationCountryStatsResponse.of(
                    0, 0, null, phaseId
            );
            return ResponseEntity.ok(errorResponse);
        }
    }
    
    @Operation(summary = "Get all innovation types", 
               description = "Returns all innovation types from rep_ind_innovation_types table")
    @GetMapping("/innovation-types")
    public ResponseEntity<List<InnovationTypeResponse.Simple>> getAllInnovationTypes() {
        try {
            List<com.example.demo.modules.innovationtype.domain.model.InnovationType> types = 
                innovationTypeRepository.findAll();
            
            List<InnovationTypeResponse.Simple> response = types.stream()
                .map(type -> new InnovationTypeResponse.Simple(
                    type.getId(),
                    type.getName(),
                    type.getIsOldType() != null ? type.getIsOldType() : false
                ))
                .toList();
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(Collections.emptyList());
        }
    }
}