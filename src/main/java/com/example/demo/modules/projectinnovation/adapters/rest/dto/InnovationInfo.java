package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Complete innovation information including all relationships and details
 * Contains basic innovation data plus related Countries, Regions, SDGs, Organizations, and External Partners
 */
@Schema(description = "Complete innovation information with all related data filtered by phase")
public record InnovationInfo(
        // Basic Innovation Info (inherited from ProjectInnovationInfoCompleteResponse)
        Long id,
        Long projectInnovationId,
        Long idPhase,
        Long year,
        String title,
        String narrative,
        Long phaseResearchId,
        Long stageInnovationId,
        Long geographicScopeId,
        Long repIndRegionId,
        Long repIndContributionCrpId,
        Long repIndDegreeInnovationId,
        Long projectExpectedStudiesId,
        String descriptionStage,
        String evidenceLink,
        Long genderFocusLevelId,
        String genderExplanation,
        Long youthFocusLevelId,
        String youthExplanation,
        Long leadOrganizationId,
        String adaptativeResearchNarrative,
        Boolean isClearLead,
        String otherInnovationType,
        String externalLink,
        Integer numberOfInnovations,
        Boolean hasMilestones,
        String shortTitle,
        Long innovationNatureId,
        Boolean hasCgiarContribution,
        String reasonNotCgiarContribution,
        String beneficiariesNarrative,
        @Schema(description = "Knowledge results narrative describing the knowledge outputs and outcomes")
        String knowledgeResultsNarrative,
        Long intellectualPropertyInstitutionId,
        Boolean hasLegalRestrictions,
        Boolean hasAssetPotential,
        Boolean hasFurtherDevelopment,
        String otherIntellectualProperty,
        String innovationImportance,
        Integer readinessScale,
        String readinessReason,
        Long genderScoreId,
        Long climateChangeScoreId,
        Long foodSecurityScoreId,
        Long environmentalScoreId,
        Long povertyJobsScoreId,
        // Expanded objects from existing response
        PhaseDto phase,
        InnovationStageDto innovationStage,
        GeographicScopeDto geographicScope,
        InnovationTypeDto innovationType,
        RegionDto region,
        ContributionCrpDto contributionCrp,
        DegreeInnovationDto degreeInnovation,
        InstitutionDto leadOrganization,
        FocusLevelDto genderFocusLevel,
        FocusLevelDto youthFocusLevel,
        InstitutionDto intellectualPropertyInstitution,
        PhaseResearchDto phaseResearch,
        Long projectId,
        Boolean isActive,
        LocalDateTime activeSince,
        Long createdBy,
        Long modifiedBy,
        String modificationJustification,
        List<ProjectInnovationActorsResponse> actors,
        
        // NEW RELATIONSHIPS
        List<ProjectInnovationSdgResponse> sdgs,                          // SDGs
        List<ProjectInnovationRegionResponse> regions,                    // Regions
        List<ProjectInnovationCountryResponse> countries,                 // Countries
        List<ProjectInnovationReferenceResponse> references,              // References / Evidences
        List<ProjectInnovationOrganizationResponse> organizations,        // Contact Organizations
        List<ProjectInnovationAllianceOrganizationResponse> allianceOrganizations, // Alliance organizations
        List<ProjectInnovationPartnershipResponse> contactPersons,       // External Partners with Contact Names
        List<ProjectInnovationContributingOrganizationResponse> contributingOrganizations // Contributing Organizations
) {
}
