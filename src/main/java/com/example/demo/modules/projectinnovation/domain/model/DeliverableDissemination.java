package com.example.demo.modules.projectinnovation.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Minimal representation of deliverable dissemination info to expose URLs.
 */
@Entity
@Table(name = "deliverable_dissemination")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliverableDissemination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "deliverable_id")
    private Long deliverableId;

    @Column(name = "id_phase")
    private Long idPhase;

    @Column(name = "dissemination_URL", columnDefinition = "TEXT")
    private String disseminationUrl;
}
