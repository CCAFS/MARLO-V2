package com.example.demo.modules.projectinnovation.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entity for institutions table (reference table)
 * Represents institutions involved in partnerships
 */
@Entity
@Table(name = "institutions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Institution {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name", columnDefinition = "TEXT")
    private String name;
    
    @Column(name = "acronym")
    private String acronym;
    
    @Column(name = "website_link", columnDefinition = "TEXT")
    private String websiteLink;
    
    @Column(name = "program_id")
    private Long programId;
    
    @Column(name = "institution_type_id")
    private Long institutionTypeId;
    
    @Column(name = "added")
    private LocalDateTime added;
    
    @Column(name = "parent_id")
    private Long parentId;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @Column(name = "is_active")
    private Boolean isActive;
    
    @Column(name = "created_by")
    private Long createdBy;
    
    @Column(name = "updated_by")
    private Long updatedBy;
    
    @Column(name = "modification_justification", columnDefinition = "TEXT")
    private String modificationJustification;
}