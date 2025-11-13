package com.example.demo.modules.projectinnovation.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entity representing the project_innovation_alliance_organizations table.
 * Stores alliance organizations linked to a project innovation and phase.
 */
@Entity
@Table(name = "project_innovation_alliance_organizations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectInnovationAllianceOrganization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "project_innovation_id")
    private Long projectInnovationId;

    @Column(name = "id_phase")
    private Long idPhase;

    @Column(name = "institution_type_id")
    private Long institutionTypeId;

    @Column(name = "organization_name", columnDefinition = "TEXT")
    private String organizationName;

    @Column(name = "is_scaling_partner")
    private Boolean isScalingPartner;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "active_since")
    private LocalDateTime activeSince;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(name = "modified_by")
    private Long modifiedBy;

    @Column(name = "modification_justification", columnDefinition = "TEXT")
    private String modificationJustification;

    @Column(name = "institution_id")
    private Long institutionId;

    @Column(name = "number")
    private Integer number;
}
