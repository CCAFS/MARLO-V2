package com.example.demo.modules.projectinnovation.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entidad principal para project_innovations
 * Tabla principal que contiene los metadatos básicos de las innovaciones
 */
@Entity
@Table(name = "project_innovations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectInnovation {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "project_id", nullable = false)
    private Long projectId;
    
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
    
    @Column(name = "active_since", nullable = false)
    private LocalDateTime activeSince;
    
    @Column(name = "created_by", nullable = false)
    private Long createdBy;
    
    @Column(name = "modified_by")
    private Long modifiedBy;
    
    @Column(name = "modification_justification", columnDefinition = "TEXT")
    private String modificationJustification;
    
    // Relación con la información detallada (comentada temporalmente para pruebas)
    // @OneToOne(mappedBy = "projectInnovation", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    // private ProjectInnovationInfo innovationInfo;
    
    @PrePersist
    protected void onCreate() {
        if (activeSince == null) {
            activeSince = LocalDateTime.now();
        }
        if (isActive == null) {
            isActive = true;
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        activeSince = LocalDateTime.now();
    }
}