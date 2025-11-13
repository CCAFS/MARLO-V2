package com.example.demo.modules.projectinnovation.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * LocElement entity representing the loc_elements table
 * Contains information about geographical locations (countries, regions, etc.)
 */
@Entity
@Table(name = "loc_elements")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LocElement {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name", columnDefinition = "TEXT")
    private String name;
    
    @Column(name = "iso_alpha_2")
    private String isoAlpha2;
    
    @Column(name = "iso_numeric")
    private Long isoNumeric;
    
    @Column(name = "parent_id")
    private Long parentId;
    
    @Column(name = "element_type_id")
    private Long elementTypeId;
    
    @Column(name = "geoposition_id")
    private Long geopositionId;
    
    @Column(name = "is_site_integration")
    private Boolean isSiteIntegration;
    
    @Column(name = "is_active")
    private Boolean isActive;
    
    @Column(name = "created_by")
    private Long createdBy;
    
    @Column(name = "active_since")
    private LocalDateTime activeSince;
    
    @Column(name = "modified_by")
    private Long modifiedBy;
    
    @Column(name = "modification_justification", columnDefinition = "TEXT")
    private String modificationJustification;
    
    @Column(name = "global_unit_id")
    private Long globalUnitId;
    
    @Column(name = "rep_ind_regions_id")
    private Long repIndRegionsId;
    
    @Column(name = "iso_alpha_3")
    private String isoAlpha3;
}