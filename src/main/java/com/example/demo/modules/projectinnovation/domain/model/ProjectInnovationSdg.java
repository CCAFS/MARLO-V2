package com.example.demo.modules.projectinnovation.domain.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "project_innovation_sdgs")
@Data
public class ProjectInnovationSdg {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "innovation_id")
    private Long innovationId;
    
    @Column(name = "sdg_id")
    private Long sdgId;
    
    @Column(name = "id_phase")
    private Long idPhase;
    
    @Column(name = "is_active")
    private Boolean isActive;
    
    @Column(name = "active_since")
    private LocalDateTime activeSince;
    
    @Column(name = "created_by")
    private Long createdBy;
    
    @Column(name = "modified_by")
    private Long modifiedBy;
    
    @Column(name = "modification_justification")
    private String modificationJustification;
}