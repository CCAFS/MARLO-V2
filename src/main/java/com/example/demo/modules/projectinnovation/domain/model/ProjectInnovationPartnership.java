package com.example.demo.modules.projectinnovation.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entity for project_innovation_partnerships table
 * Represents external partnerships associated with project innovations
 */
@Entity
@Table(name = "project_innovation_partnerships")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectInnovationPartnership {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "project_innovation_id")
    private Long projectInnovationId;
    
    @Column(name = "institution_id")
    private Long institutionId;
    
    @Column(name = "id_phase")
    private Long idPhase;
    
    @Column(name = "innovation_partner_type_id")
    private Long innovationPartnerTypeId;
    
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
}