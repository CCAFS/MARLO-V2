package com.example.demo.modules.projectinnovation.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity for project_innovation_organizations table
 * Represents organizations associated with project innovations
 */
@Entity
@Table(name = "project_innovation_organizations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectInnovationOrganization {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "project_innovation_id")
    private Long projectInnovationId;
    
    @Column(name = "id_phase")
    private Long idPhase;
    
    @Column(name = "rep_ind_organization_type_id")
    private Long repIndOrganizationTypeId;
}