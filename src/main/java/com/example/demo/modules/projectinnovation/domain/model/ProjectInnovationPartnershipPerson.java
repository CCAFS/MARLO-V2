package com.example.demo.modules.projectinnovation.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entity for project_innovation_partnership_persons table
 * Represents contact persons associated with innovation partnerships
 */
@Entity
@Table(name = "project_innovation_partnership_persons")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectInnovationPartnershipPerson {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "partnership_id")
    private Long partnershipId;
    
    @Column(name = "user_id")
    private Long userId;
    
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