package com.example.demo.modules.projectinnovation.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entity representing references associated with a project innovation.
 */
@Entity
@Table(name = "project_innovation_references")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectInnovationReference {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "project_innovation_id")
    private Long projectInnovationId;
    
    @Column(name = "reference", columnDefinition = "TEXT")
    private String reference;
    
    @Column(name = "id_phase")
    private Long idPhase;
    
    @Column(name = "link", columnDefinition = "TEXT")
    private String link;
    
    @Column(name = "is_external_author")
    private Boolean isExternalAuthor;
    
    @Column(name = "has_evidence_by_deliverable")
    private Boolean hasEvidenceByDeliverable;
    
    @Column(name = "deliverable_id")
    private Long deliverableId;
    
    @Column(name = "type_id")
    private Long typeId;
    
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
