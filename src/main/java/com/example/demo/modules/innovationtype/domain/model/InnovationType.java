package com.example.demo.modules.innovationtype.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity for rep_ind_innovation_types table
 * Catalog of innovation types used in MARLO system
 */
@Entity(name = "RepIndInnovationType")
@Table(name = "rep_ind_innovation_types")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InnovationType {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name", columnDefinition = "TEXT")
    private String name;
    
    @Column(name = "definition", columnDefinition = "TEXT")
    private String definition;
    
    @Column(name = "is_old_type")
    private Boolean isOldType;
    
    @Column(name = "prms_id_equivalent")
    private Long prmsIdEquivalent;
    
    @Column(name = "prms_name_equivalent", columnDefinition = "TEXT")
    private String prmsNameEquivalent;
}