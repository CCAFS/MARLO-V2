package com.example.demo.modules.projectinnovation.adapters.rest;

import com.example.demo.modules.projectinnovation.adapters.rest.dto.*;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Enhanced response with all related information for Innovation Info endpoint
 * Includes Countries, Regions, SDGs, Contact Organizations, Organizations, and External Partners
 */
public record ProjectInnovationInfoCompleteWithRelationsResponse(
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
        Long innovationTypeId,
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
        List<ProjectInnovationPartnershipResponse> contactPersons,        // External Partners with Contact Names
        List<ProjectInnovationComplementarySolutionResponse> complementarySolutions, // Complementary solutions
        List<ProjectInnovationBundleResponse> bundles                     // Bundled innovations
) {
}
