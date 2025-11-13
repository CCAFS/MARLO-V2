package com.example.demo.modules.projectinnovation.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity for project_innovation_regions table
 * Represents regions associated with project innovations
 */
@Entity
@Table(name = "project_innovation_regions")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectInnovationRegion {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "project_innovation_id")
    private Long projectInnovationId;
    
    @Column(name = "id_region")
    private Long idRegion;
    
    @Column(name = "id_phase")
    private Long idPhase;
}