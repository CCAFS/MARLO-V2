package com.example.demo.modules.projectinnovation.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad para project_innovation_info
 * Contiene la información detallada de cada innovación
 */
@Entity
@Table(name = "project_innovation_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectInnovationInfo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "project_innovation_id", insertable = false, updatable = false)
    private Long projectInnovationId;
    
    @Column(name = "id_phase")
    private Long idPhase;
    
    @Column(name = "year")
    private Long year;
    
    @Column(name = "title", columnDefinition = "TEXT")
    private String title;
    
    @Column(name = "narrative", columnDefinition = "TEXT")
    private String narrative;
    
    @Column(name = "phase_research_id")
    private Long phaseResearchId;
    
    @Column(name = "stage_innovation_id")
    private Long stageInnovationId;
    
    @Column(name = "geographic_scope_id")
    private Long geographicScopeId;
    
    @Column(name = "innovation_type_id")
    private Long innovationTypeId;
    
    @Column(name = "rep_ind_region_id")
    private Long repIndRegionId;
    
    @Column(name = "rep_ind_contribution_crp_id")
    private Long repIndContributionCrpId;
    
    @Column(name = "rep_ind_degree_innovation_id")
    private Long repIndDegreeInnovationId;
    
    @Column(name = "project_expected_studies_id")
    private Long projectExpectedStudiesId;
    
    @Column(name = "description_stage", columnDefinition = "TEXT")
    private String descriptionStage;
    
    @Column(name = "evidence_link", columnDefinition = "TEXT")
    private String evidenceLink;
    
    @Column(name = "gender_focus_level_id")
    private Long genderFocusLevelId;
    
    @Column(name = "gender_explaniation", columnDefinition = "TEXT")
    private String genderExplanation;
    
    @Column(name = "youth_focus_level_id")
    private Long youthFocusLevelId;
    
    @Column(name = "youth_explaniation", columnDefinition = "TEXT")
    private String youthExplanation;
    
    @Column(name = "lead_organization_id")
    private Long leadOrganizationId;
    
    @Column(name = "adaptative_research_narrative", columnDefinition = "TEXT")
    private String adaptativeResearchNarrative;
    
    @Column(name = "is_clear_lead")
    private Boolean isClearLead;
    
    @Column(name = "other_innovation_type", columnDefinition = "TEXT")
    private String otherInnovationType;
    
    @Column(name = "external_link", columnDefinition = "TEXT")
    private String externalLink;
    
    @Column(name = "number_of_innovations")
    private Long numberOfInnovations;
    
    @Column(name = "has_milestones")
    private Boolean hasMilestones;
    
    @Column(name = "short_title", columnDefinition = "TEXT")
    private String shortTitle;
    
    @Column(name = "innovation_nature_id")
    private Long innovationNatureId;
    
    @Column(name = "has_cgiar_contribution")
    private Boolean hasCgiarContribution;
    
    @Column(name = "reason_not_cgiar_contribution", columnDefinition = "TEXT")
    private String reasonNotCgiarContribution;
    
    @Column(name = "beneficiaries_narrative", columnDefinition = "TEXT")
    private String beneficiariesNarrative;
    
    @Column(name = "intellectual_property_institution_id")
    private Long intellectualPropertyInstitutionId;
    
    @Column(name = "has_legal_restrictions")
    private Boolean hasLegalRestrictions;
    
    @Column(name = "has_asset_potential")
    private Boolean hasAssetPotential;
    
    @Column(name = "has_further_development")
    private Boolean hasFurtherDevelopment;
    
    @Column(name = "other_intellectual_property", columnDefinition = "TEXT")
    private String otherIntellectualProperty;
    
    @Column(name = "innovation_importance", columnDefinition = "TEXT")
    private String innovationImportance;
    
    @Column(name = "readiness_scale")
    private Integer readinessScale;
    
    @Column(name = "readiness_reason", columnDefinition = "TEXT")
    private String readinessReason;
    
    // Scores de impacto
    @Column(name = "gender_score_id")
    private Long genderScoreId;
    
    @Column(name = "climate_change_score_id")
    private Long climateChangeScoreId;
    
    @Column(name = "food_security_score_id")
    private Long foodSecurityScoreId;
    
    @Column(name = "environmental_score_id")
    private Long environmentalScoreId;
    
    @Column(name = "poverty_jobs_score_id")
    private Long povertyJobsScoreId;
    
    // Inverse relationship (temporarily commented for testing)
    // @OneToOne
    // @JoinColumn(name = "project_innovation_id", referencedColumnName = "id")
    // private ProjectInnovation projectInnovation;
    
    // Catalog references (lazy loading) (temporarily commented for testing)
    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "stage_innovation_id", insertable = false, updatable = false)
    // private InnovationStage innovationStage;
    
    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "innovation_type_id", insertable = false, updatable = false)
    // private InnovationType innovationType;
}