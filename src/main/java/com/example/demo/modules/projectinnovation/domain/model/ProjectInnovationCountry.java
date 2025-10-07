package com.example.demo.modules.projectinnovation.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity for project_innovation_countries table
 * Represents countries associated with project innovations
 */
@Entity
@Table(name = "project_innovation_countries")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectInnovationCountry {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name = "project_innovation_id")
    private Long projectInnovationId;
    
    @Column(name = "id_country")
    private Long idCountry;
    
    @Column(name = "id_phase")
    private Long idPhase;
}