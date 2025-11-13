package com.example.demo.modules.projectinnovation.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity representing the project_innovation_contributing_organizations table
 * Maps the relationship between project innovations and contributing institutions
 */
@Entity
@Table(name = "project_innovation_contributing_organizations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectInnovationContributingOrganization {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "project_innovation_id")
    private Long projectInnovationId;
    
    @Column(name = "id_phase")
    private Long idPhase;
    
    @Column(name = "institution_id")
    private Long institutionId;
}