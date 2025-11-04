package com.example.demo.modules.projectinnovation.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Minimal representation of deliverables_info focusing on title lookup.
 */
@Entity
@Table(name = "deliverables_info")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeliverableInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "deliverable_id")
    private Long deliverableId;

    @Column(name = "id_phase")
    private Long idPhase;

    @Column(name = "title", columnDefinition = "TEXT")
    private String title;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "active_since")
    private LocalDateTime activeSince;
}
