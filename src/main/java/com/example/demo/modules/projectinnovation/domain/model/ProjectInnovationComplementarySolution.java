package com.example.demo.modules.projectinnovation.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entity representing complementary solutions associated with a project innovation.
 */
@Entity
@Table(name = "project_innovation_complementary_solutions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectInnovationComplementarySolution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title", columnDefinition = "TEXT")
    private String title;

    @Column(name = "short_title", columnDefinition = "TEXT")
    private String shortTitle;

    @Column(name = "short_description", columnDefinition = "TEXT")
    private String shortDescription;

    @Column(name = "project_innovation_type_id")
    private Long projectInnovationTypeId;

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

    @Column(name = "id_phase")
    private Long idPhase;

    @Column(name = "project_innovation_id")
    private Long projectInnovationId;
}
