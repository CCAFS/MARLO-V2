package com.example.demo.modules.projectinnovation.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidad para rep_ind_stage_innovations
 * Catálogo de etapas de innovación
 */
@Entity
@Table(name = "rep_ind_stage_innovations")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class InnovationStage {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "name", columnDefinition = "TEXT")
    private String name;
    
    @Column(name = "definition", columnDefinition = "TEXT")
    private String definition;
}