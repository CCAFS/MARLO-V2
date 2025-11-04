package com.example.demo.modules.projectinnovation.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entity representing entries from project_innovation_bundles table.
 */
@Entity
@Table(name = "project_innovation_bundles")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectInnovationBundle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "project_innovation_id")
    private Long projectInnovationId;

    @Column(name = "selected_innovation_id")
    private Long selectedInnovationId;

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

    @Column(name = "modification_justification", columnDefinition = "TEXT")
    private String modificationJustification;
}
