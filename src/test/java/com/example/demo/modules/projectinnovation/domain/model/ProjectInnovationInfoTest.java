package com.example.demo.modules.projectinnovation.domain.model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class ProjectInnovationInfoTest {

    private ProjectInnovationInfo info1;
    private ProjectInnovationInfo info2;

    @BeforeEach
    void setUp() {
        info1 = new ProjectInnovationInfo();
        info2 = new ProjectInnovationInfo();
    }

    @Test
    void constructor_WithNoArgsConstructor_ShouldCreateEmpty() {
        // Act
        ProjectInnovationInfo info = new ProjectInnovationInfo();

        // Assert
        assertNotNull(info);
    }

    @Test
    void equals_WithSameId_ShouldReturnTrue() {
        // Arrange
        info1.setId(1L);
        info2.setId(1L);

        // Act & Assert
        assertEquals(info1, info2);
    }

    @Test
    void equals_WithDifferentIds_ShouldReturnFalse() {
        // Arrange
        info1.setId(1L);
        info2.setId(2L);

        // Act & Assert
        assertNotEquals(info1, info2);
    }

    @Test
    void equals_WithNull_ShouldReturnFalse() {
        // Act & Assert
        assertNotEquals(info1, null);
    }

    @Test
    void equals_WithDifferentClass_ShouldReturnFalse() {
        // Act & Assert
        assertNotEquals(info1, "not an info");
    }

    @Test
    void equals_WithSameInstance_ShouldReturnTrue() {
        // Act & Assert
        assertEquals(info1, info1);
    }

    @Test
    void equals_WithAllFieldsSame_ShouldReturnTrue() {
        // Arrange
        Long id = 1L;
        Long projectInnovationId = 100L;
        Long idPhase = 1L;
        Long year = 2024L;
        String title = "Test Title";
        String narrative = "Test Narrative";

        info1.setId(id);
        info1.setProjectInnovationId(projectInnovationId);
        info1.setIdPhase(idPhase);
        info1.setYear(year);
        info1.setTitle(title);
        info1.setNarrative(narrative);

        info2.setId(id);
        info2.setProjectInnovationId(projectInnovationId);
        info2.setIdPhase(idPhase);
        info2.setYear(year);
        info2.setTitle(title);
        info2.setNarrative(narrative);

        // Act & Assert
        assertEquals(info1, info2);
    }

    @Test
    void equals_WithDifferentFields_ShouldReturnFalse() {
        // Arrange
        info1.setId(1L);
        info1.setTitle("Title 1");
        info2.setId(1L);
        info2.setTitle("Title 2");

        // Act & Assert
        assertNotEquals(info1, info2);
    }

    @Test
    void hashCode_WithSameFields_ShouldReturnSameHashCode() {
        // Arrange
        Long id = 1L;
        String title = "Test Title";
        info1.setId(id);
        info1.setTitle(title);
        info2.setId(id);
        info2.setTitle(title);

        // Act & Assert
        assertEquals(info1.hashCode(), info2.hashCode());
    }

    @Test
    void hashCode_WithDifferentFields_ShouldReturnDifferentHashCode() {
        // Arrange
        info1.setId(1L);
        info1.setTitle("Title 1");
        info2.setId(2L);
        info2.setTitle("Title 2");

        // Act & Assert
        assertNotEquals(info1.hashCode(), info2.hashCode());
    }

    @Test
    void hashCode_WithNullFields_ShouldNotThrowException() {
        // Act & Assert - should not throw
        assertDoesNotThrow(() -> info1.hashCode());
    }

    @Test
    void toString_ShouldContainClassName() {
        // Act
        String toString = info1.toString();

        // Assert
        assertTrue(toString.contains("ProjectInnovationInfo"));
    }

    @Test
    void toString_WithFieldsSet_ShouldContainFieldValues() {
        // Arrange
        info1.setId(1L);
        info1.setTitle("Test Title");

        // Act
        String toString = info1.toString();

        // Assert
        assertTrue(toString.contains("1") || toString.contains("Test Title"));
    }

    @Test
    void gettersAndSetters_ShouldWorkCorrectly() {
        // Arrange
        Long id = 1L;
        Long projectInnovationId = 100L;
        Long idPhase = 1L;
        Long year = 2024L;
        String title = "Test Title";
        String narrative = "Test Narrative";
        Long phaseResearchId = 1L;
        Long stageInnovationId = 1L;
        Long geographicScopeId = 1L;
        Long innovationTypeId = 1L;
        Long repIndRegionId = 1L;
        Long repIndContributionCrpId = 1L;
        Long repIndDegreeInnovationId = 1L;
        Long projectExpectedStudiesId = 1L;
        String descriptionStage = "Stage Description";
        String evidenceLink = "https://evidence.com";
        Long genderFocusLevelId = 1L;
        String genderExplanation = "Gender Explanation";
        Long youthFocusLevelId = 1L;
        String youthExplanation = "Youth Explanation";
        Long leadOrganizationId = 1L;
        String adaptativeResearchNarrative = "Adaptive Research";
        Boolean isClearLead = true;
        String otherInnovationType = "Other Type";
        String externalLink = "https://external.com";
        Long numberOfInnovations = 5L;
        Boolean hasMilestones = true;
        String shortTitle = "Short Title";
        Long innovationNatureId = 1L;
        Boolean hasCgiarContribution = true;
        String reasonNotCgiarContribution = "Reason";
        String beneficiariesNarrative = "Beneficiaries";
        String knowledgeResultsNarrative = "Knowledge Results";
        Long hasKnowledgePotentialId = 1L;
        String reasonKnowledgePotential = "Reason";
        Long intellectualPropertyInstitutionId = 1L;
        Boolean hasLegalRestrictions = false;
        Boolean hasAssetPotential = true;
        Boolean hasFurtherDevelopment = false;
        String otherIntellectualProperty = "Other IP";
        String innovationImportance = "Importance";
        Integer readinessScale = 5;
        String readinessReason = "Reason";
        Long genderScoreId = 1L;
        Long climateChangeScoreId = 1L;
        Long foodSecurityScoreId = 1L;
        Long environmentalScoreId = 1L;
        Long povertyJobsScoreId = 1L;

        // Act
        info1.setId(id);
        info1.setProjectInnovationId(projectInnovationId);
        info1.setIdPhase(idPhase);
        info1.setYear(year);
        info1.setTitle(title);
        info1.setNarrative(narrative);
        info1.setPhaseResearchId(phaseResearchId);
        info1.setStageInnovationId(stageInnovationId);
        info1.setGeographicScopeId(geographicScopeId);
        info1.setInnovationTypeId(innovationTypeId);
        info1.setRepIndRegionId(repIndRegionId);
        info1.setRepIndContributionCrpId(repIndContributionCrpId);
        info1.setRepIndDegreeInnovationId(repIndDegreeInnovationId);
        info1.setProjectExpectedStudiesId(projectExpectedStudiesId);
        info1.setDescriptionStage(descriptionStage);
        info1.setEvidenceLink(evidenceLink);
        info1.setGenderFocusLevelId(genderFocusLevelId);
        info1.setGenderExplanation(genderExplanation);
        info1.setYouthFocusLevelId(youthFocusLevelId);
        info1.setYouthExplanation(youthExplanation);
        info1.setLeadOrganizationId(leadOrganizationId);
        info1.setAdaptativeResearchNarrative(adaptativeResearchNarrative);
        info1.setIsClearLead(isClearLead);
        info1.setOtherInnovationType(otherInnovationType);
        info1.setExternalLink(externalLink);
        info1.setNumberOfInnovations(numberOfInnovations);
        info1.setHasMilestones(hasMilestones);
        info1.setShortTitle(shortTitle);
        info1.setInnovationNatureId(innovationNatureId);
        info1.setHasCgiarContribution(hasCgiarContribution);
        info1.setReasonNotCgiarContribution(reasonNotCgiarContribution);
        info1.setBeneficiariesNarrative(beneficiariesNarrative);
        info1.setKnowledgeResultsNarrative(knowledgeResultsNarrative);
        info1.setHasKnowledgePotentialId(hasKnowledgePotentialId);
        info1.setReasonKnowledgePotential(reasonKnowledgePotential);
        info1.setIntellectualPropertyInstitutionId(intellectualPropertyInstitutionId);
        info1.setHasLegalRestrictions(hasLegalRestrictions);
        info1.setHasAssetPotential(hasAssetPotential);
        info1.setHasFurtherDevelopment(hasFurtherDevelopment);
        info1.setOtherIntellectualProperty(otherIntellectualProperty);
        info1.setInnovationImportance(innovationImportance);
        info1.setReadinessScale(readinessScale);
        info1.setReadinessReason(readinessReason);
        info1.setGenderScoreId(genderScoreId);
        info1.setClimateChangeScoreId(climateChangeScoreId);
        info1.setFoodSecurityScoreId(foodSecurityScoreId);
        info1.setEnvironmentalScoreId(environmentalScoreId);
        info1.setPovertyJobsScoreId(povertyJobsScoreId);

        // Assert
        assertEquals(id, info1.getId());
        assertEquals(projectInnovationId, info1.getProjectInnovationId());
        assertEquals(idPhase, info1.getIdPhase());
        assertEquals(year, info1.getYear());
        assertEquals(title, info1.getTitle());
        assertEquals(narrative, info1.getNarrative());
        assertEquals(phaseResearchId, info1.getPhaseResearchId());
        assertEquals(stageInnovationId, info1.getStageInnovationId());
        assertEquals(geographicScopeId, info1.getGeographicScopeId());
        assertEquals(innovationTypeId, info1.getInnovationTypeId());
        assertEquals(repIndRegionId, info1.getRepIndRegionId());
        assertEquals(repIndContributionCrpId, info1.getRepIndContributionCrpId());
        assertEquals(repIndDegreeInnovationId, info1.getRepIndDegreeInnovationId());
        assertEquals(projectExpectedStudiesId, info1.getProjectExpectedStudiesId());
        assertEquals(descriptionStage, info1.getDescriptionStage());
        assertEquals(evidenceLink, info1.getEvidenceLink());
        assertEquals(genderFocusLevelId, info1.getGenderFocusLevelId());
        assertEquals(genderExplanation, info1.getGenderExplanation());
        assertEquals(youthFocusLevelId, info1.getYouthFocusLevelId());
        assertEquals(youthExplanation, info1.getYouthExplanation());
        assertEquals(leadOrganizationId, info1.getLeadOrganizationId());
        assertEquals(adaptativeResearchNarrative, info1.getAdaptativeResearchNarrative());
        assertEquals(isClearLead, info1.getIsClearLead());
        assertEquals(otherInnovationType, info1.getOtherInnovationType());
        assertEquals(externalLink, info1.getExternalLink());
        assertEquals(numberOfInnovations, info1.getNumberOfInnovations());
        assertEquals(hasMilestones, info1.getHasMilestones());
        assertEquals(shortTitle, info1.getShortTitle());
        assertEquals(innovationNatureId, info1.getInnovationNatureId());
        assertEquals(hasCgiarContribution, info1.getHasCgiarContribution());
        assertEquals(reasonNotCgiarContribution, info1.getReasonNotCgiarContribution());
        assertEquals(beneficiariesNarrative, info1.getBeneficiariesNarrative());
        assertEquals(knowledgeResultsNarrative, info1.getKnowledgeResultsNarrative());
        assertEquals(hasKnowledgePotentialId, info1.getHasKnowledgePotentialId());
        assertEquals(reasonKnowledgePotential, info1.getReasonKnowledgePotential());
        assertEquals(intellectualPropertyInstitutionId, info1.getIntellectualPropertyInstitutionId());
        assertEquals(hasLegalRestrictions, info1.getHasLegalRestrictions());
        assertEquals(hasAssetPotential, info1.getHasAssetPotential());
        assertEquals(hasFurtherDevelopment, info1.getHasFurtherDevelopment());
        assertEquals(otherIntellectualProperty, info1.getOtherIntellectualProperty());
        assertEquals(innovationImportance, info1.getInnovationImportance());
        assertEquals(readinessScale, info1.getReadinessScale());
        assertEquals(readinessReason, info1.getReadinessReason());
        assertEquals(genderScoreId, info1.getGenderScoreId());
        assertEquals(climateChangeScoreId, info1.getClimateChangeScoreId());
        assertEquals(foodSecurityScoreId, info1.getFoodSecurityScoreId());
        assertEquals(environmentalScoreId, info1.getEnvironmentalScoreId());
        assertEquals(povertyJobsScoreId, info1.getPovertyJobsScoreId());
    }

    @Test
    void equals_WithNullFields_ShouldHandleNulls() {
        // Arrange
        info1.setId(null);
        info1.setTitle(null);
        info2.setId(null);
        info2.setTitle(null);

        // Act & Assert
        assertEquals(info1, info2);
    }

    @Test
    void equals_WithOneNullField_ShouldReturnFalse() {
        // Arrange
        info1.setId(1L);
        info1.setTitle("Title");
        info2.setId(1L);
        info2.setTitle(null);

        // Act & Assert
        assertNotEquals(info1, info2);
    }

    @Test
    void equals_WithDifferentNullFields_ShouldReturnFalse() {
        // Arrange
        info1.setId(1L);
        info1.setTitle(null);
        info2.setId(1L);
        info2.setTitle("Title");

        // Act & Assert
        assertNotEquals(info1, info2);
    }

    @Test
    void equals_WithBooleanFields_ShouldCompareCorrectly() {
        // Arrange
        info1.setId(1L);
        info1.setIsClearLead(true);
        info1.setHasMilestones(false);
        info1.setHasCgiarContribution(true);
        info1.setHasLegalRestrictions(false);
        info1.setHasAssetPotential(true);
        info1.setHasFurtherDevelopment(false);

        info2.setId(1L);
        info2.setIsClearLead(true);
        info2.setHasMilestones(false);
        info2.setHasCgiarContribution(true);
        info2.setHasLegalRestrictions(false);
        info2.setHasAssetPotential(true);
        info2.setHasFurtherDevelopment(false);

        // Act & Assert
        assertEquals(info1, info2);
    }

    @Test
    void equals_WithDifferentBooleanFields_ShouldReturnFalse() {
        // Arrange
        info1.setId(1L);
        info1.setIsClearLead(true);
        info2.setId(1L);
        info2.setIsClearLead(false);

        // Act & Assert
        assertNotEquals(info1, info2);
    }

    @Test
    void equals_WithNumericFields_ShouldCompareCorrectly() {
        // Arrange
        info1.setId(1L);
        info1.setYear(2024L);
        info1.setNumberOfInnovations(5L);
        info1.setReadinessScale(3);
        info2.setId(1L);
        info2.setYear(2024L);
        info2.setNumberOfInnovations(5L);
        info2.setReadinessScale(3);

        // Act & Assert
        assertEquals(info1, info2);
    }

    @Test
    void equals_WithDifferentNumericFields_ShouldReturnFalse() {
        // Arrange
        info1.setId(1L);
        info1.setYear(2024L);
        info2.setId(1L);
        info2.setYear(2025L);

        // Act & Assert
        assertNotEquals(info1, info2);
    }

    @Test
    void equals_WithLongFields_ShouldCompareCorrectly() {
        // Arrange
        info1.setId(1L);
        info1.setProjectInnovationId(100L);
        info1.setIdPhase(1L);
        info1.setPhaseResearchId(2L);
        info1.setStageInnovationId(3L);
        info1.setGeographicScopeId(4L);
        info1.setInnovationTypeId(5L);
        info2.setId(1L);
        info2.setProjectInnovationId(100L);
        info2.setIdPhase(1L);
        info2.setPhaseResearchId(2L);
        info2.setStageInnovationId(3L);
        info2.setGeographicScopeId(4L);
        info2.setInnovationTypeId(5L);

        // Act & Assert
        assertEquals(info1, info2);
    }

    @Test
    void equals_WithDifferentLongFields_ShouldReturnFalse() {
        // Arrange
        info1.setId(1L);
        info1.setProjectInnovationId(100L);
        info2.setId(1L);
        info2.setProjectInnovationId(200L);

        // Act & Assert
        assertNotEquals(info1, info2);
    }

    @Test
    void equals_WithIntegerFields_ShouldCompareCorrectly() {
        // Arrange
        info1.setId(1L);
        info1.setReadinessScale(5);
        info2.setId(1L);
        info2.setReadinessScale(5);

        // Act & Assert
        assertEquals(info1, info2);
    }

    @Test
    void equals_WithDifferentIntegerFields_ShouldReturnFalse() {
        // Arrange
        info1.setId(1L);
        info1.setReadinessScale(5);
        info2.setId(1L);
        info2.setReadinessScale(10);

        // Act & Assert
        assertNotEquals(info1, info2);
    }

    @Test
    void equals_WithNullBooleanFields_ShouldHandleNulls() {
        // Arrange
        info1.setId(1L);
        info1.setIsClearLead(null);
        info2.setId(1L);
        info2.setIsClearLead(null);

        // Act & Assert
        assertEquals(info1, info2);
    }

    @Test
    void equals_WithOneNullBooleanField_ShouldReturnFalse() {
        // Arrange
        info1.setId(1L);
        info1.setIsClearLead(true);
        info2.setId(1L);
        info2.setIsClearLead(null);

        // Act & Assert
        assertNotEquals(info1, info2);
    }

    @Test
    void equals_WithNullNumericFields_ShouldHandleNulls() {
        // Arrange
        info1.setId(1L);
        info1.setYear(null);
        info1.setNumberOfInnovations(null);
        info2.setId(1L);
        info2.setYear(null);
        info2.setNumberOfInnovations(null);

        // Act & Assert
        assertEquals(info1, info2);
    }

    @Test
    void equals_WithOneNullNumericField_ShouldReturnFalse() {
        // Arrange
        info1.setId(1L);
        info1.setYear(2024L);
        info2.setId(1L);
        info2.setYear(null);

        // Act & Assert
        assertNotEquals(info1, info2);
    }

    @Test
    void hashCode_WithBooleanFields_ShouldWorkCorrectly() {
        // Arrange
        info1.setId(1L);
        info1.setIsClearLead(true);
        info1.setHasMilestones(false);
        info2.setId(1L);
        info2.setIsClearLead(true);
        info2.setHasMilestones(false);

        // Act & Assert
        assertEquals(info1.hashCode(), info2.hashCode());
    }

    @Test
    void hashCode_WithNumericFields_ShouldWorkCorrectly() {
        // Arrange
        info1.setId(1L);
        info1.setYear(2024L);
        info1.setReadinessScale(5);
        info2.setId(1L);
        info2.setYear(2024L);
        info2.setReadinessScale(5);

        // Act & Assert
        assertEquals(info1.hashCode(), info2.hashCode());
    }

    @Test
    void hashCode_WithNullBooleanFields_ShouldWorkCorrectly() {
        // Arrange
        info1.setId(1L);
        info1.setIsClearLead(null);
        info2.setId(1L);
        info2.setIsClearLead(null);

        // Act & Assert
        assertEquals(info1.hashCode(), info2.hashCode());
    }

    @Test
    void hashCode_WithNullNumericFields_ShouldWorkCorrectly() {
        // Arrange
        info1.setId(1L);
        info1.setYear(null);
        info2.setId(1L);
        info2.setYear(null);

        // Act & Assert
        assertEquals(info1.hashCode(), info2.hashCode());
    }

    @Test
    void equals_WithAllBooleanCombinations_ShouldCoverAllBranches() {
        // Test all combinations of boolean fields
        Boolean[] values = {true, false, null};
        for (Boolean val1 : values) {
            for (Boolean val2 : values) {
                info1.setId(1L);
                info1.setIsClearLead(val1);
                info2.setId(1L);
                info2.setIsClearLead(val2);
                
                if (val1 == val2 || (val1 != null && val1.equals(val2))) {
                    assertEquals(info1, info2, "Should be equal for " + val1 + " and " + val2);
                } else {
                    assertNotEquals(info1, info2, "Should not be equal for " + val1 + " and " + val2);
                }
            }
        }
    }

    @Test
    void equals_WithMultipleFieldDifferences_ShouldReturnFalse() {
        // Arrange
        info1.setId(1L);
        info1.setTitle("Title 1");
        info1.setYear(2024L);
        info1.setIsClearLead(true);
        info2.setId(1L);
        info2.setTitle("Title 2");
        info2.setYear(2025L);
        info2.setIsClearLead(false);

        // Act & Assert
        assertNotEquals(info1, info2);
    }

    @Test
    void equals_WithComplexObjectComparison_ShouldWorkCorrectly() {
        // Arrange - Set many fields to ensure equals checks all
        info1.setId(1L);
        info1.setProjectInnovationId(100L);
        info1.setIdPhase(1L);
        info1.setYear(2024L);
        info1.setTitle("Title");
        info1.setNarrative("Narrative");
        info1.setPhaseResearchId(1L);
        info1.setStageInnovationId(1L);
        info1.setGeographicScopeId(1L);
        info1.setInnovationTypeId(1L);
        info1.setRepIndRegionId(1L);
        info1.setRepIndContributionCrpId(1L);
        info1.setRepIndDegreeInnovationId(1L);
        info1.setProjectExpectedStudiesId(1L);
        info1.setDescriptionStage("Stage");
        info1.setEvidenceLink("Link");
        info1.setGenderFocusLevelId(1L);
        info1.setGenderExplanation("Explanation");
        info1.setYouthFocusLevelId(1L);
        info1.setYouthExplanation("Explanation");
        info1.setLeadOrganizationId(1L);
        info1.setAdaptativeResearchNarrative("Narrative");
        info1.setIsClearLead(true);
        info1.setOtherInnovationType("Type");
        info1.setExternalLink("Link");
        info1.setNumberOfInnovations(5L);
        info1.setHasMilestones(true);
        info1.setShortTitle("Short");
        info1.setInnovationNatureId(1L);
        info1.setHasCgiarContribution(true);
        info1.setReasonNotCgiarContribution("Reason");
        info1.setBeneficiariesNarrative("Narrative");
        info1.setKnowledgeResultsNarrative("Narrative");
        info1.setHasKnowledgePotentialId(1L);
        info1.setReasonKnowledgePotential("Reason");
        info1.setIntellectualPropertyInstitutionId(1L);
        info1.setHasLegalRestrictions(false);
        info1.setHasAssetPotential(true);
        info1.setHasFurtherDevelopment(false);
        info1.setOtherIntellectualProperty("IP");
        info1.setInnovationImportance("Importance");
        info1.setReadinessScale(5);
        info1.setReadinessReason("Reason");
        info1.setGenderScoreId(1L);
        info1.setClimateChangeScoreId(1L);
        info1.setFoodSecurityScoreId(1L);
        info1.setEnvironmentalScoreId(1L);
        info1.setPovertyJobsScoreId(1L);

        // Copy all fields to info2
        info2.setId(1L);
        info2.setProjectInnovationId(100L);
        info2.setIdPhase(1L);
        info2.setYear(2024L);
        info2.setTitle("Title");
        info2.setNarrative("Narrative");
        info2.setPhaseResearchId(1L);
        info2.setStageInnovationId(1L);
        info2.setGeographicScopeId(1L);
        info2.setInnovationTypeId(1L);
        info2.setRepIndRegionId(1L);
        info2.setRepIndContributionCrpId(1L);
        info2.setRepIndDegreeInnovationId(1L);
        info2.setProjectExpectedStudiesId(1L);
        info2.setDescriptionStage("Stage");
        info2.setEvidenceLink("Link");
        info2.setGenderFocusLevelId(1L);
        info2.setGenderExplanation("Explanation");
        info2.setYouthFocusLevelId(1L);
        info2.setYouthExplanation("Explanation");
        info2.setLeadOrganizationId(1L);
        info2.setAdaptativeResearchNarrative("Narrative");
        info2.setIsClearLead(true);
        info2.setOtherInnovationType("Type");
        info2.setExternalLink("Link");
        info2.setNumberOfInnovations(5L);
        info2.setHasMilestones(true);
        info2.setShortTitle("Short");
        info2.setInnovationNatureId(1L);
        info2.setHasCgiarContribution(true);
        info2.setReasonNotCgiarContribution("Reason");
        info2.setBeneficiariesNarrative("Narrative");
        info2.setKnowledgeResultsNarrative("Narrative");
        info2.setHasKnowledgePotentialId(1L);
        info2.setReasonKnowledgePotential("Reason");
        info2.setIntellectualPropertyInstitutionId(1L);
        info2.setHasLegalRestrictions(false);
        info2.setHasAssetPotential(true);
        info2.setHasFurtherDevelopment(false);
        info2.setOtherIntellectualProperty("IP");
        info2.setInnovationImportance("Importance");
        info2.setReadinessScale(5);
        info2.setReadinessReason("Reason");
        info2.setGenderScoreId(1L);
        info2.setClimateChangeScoreId(1L);
        info2.setFoodSecurityScoreId(1L);
        info2.setEnvironmentalScoreId(1L);
        info2.setPovertyJobsScoreId(1L);

        // Act & Assert
        assertEquals(info1, info2);
    }
}
