package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import java.time.LocalDateTime;
import java.util.List;

public record ProjectInnovationCompleteResponse(
        // ProjectInnovation fields
        Long id,
        Long projectId,
        Boolean isActive,
        LocalDateTime activeSince,
        Long createdBy,
        Long modifiedBy,
        String modificationJustification,
        
        // ProjectInnovationInfo fields
        Long infoId,
        Long idPhase,
        String phaseDescription,
        Integer year,
        String title,
        String narrative,
        Long phaseResearchId,
        Long stageInnovationId,
        Long geographicScopeId,
        Long innovationTypeId,
        String innovationTypeName,
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
        
        // Associated data
        List<ProjectInnovationActorsResponse> actors,
        List<ProjectInnovationSdgResponse> sdgs
) {
}