package com.example.demo.modules.projectinnovation.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entity for project_innovation_actors table
 * Represents actors involved in project innovations
 */
@Entity
@Table(name = "project_innovation_actors")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProjectInnovationActors {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "innovation_id", nullable = false)
    private Long innovationId;
    
    @Column(name = "actor_id")
    private Long actorId;
    
    // Relationship with Actor entity
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "actor_id", insertable = false, updatable = false)
    private Actor actor;
    
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
    
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
    
    // Demographic specific fields
    @Column(name = "is_women_youth")
    private Boolean isWomenYouth;
    
    @Column(name = "is_women_not_youth")
    private Boolean isWomenNotYouth;
    
    @Column(name = "is_men_youth")
    private Boolean isMenYouth;
    
    @Column(name = "is_men_not_youth")
    private Boolean isMenNotYouth;
    
    @Column(name = "is_nonbinary_youth")
    private Boolean isNonbinaryYouth;
    
    @Column(name = "is_nonbinary_not_youth")
    private Boolean isNonbinaryNotYouth;
    
    @Column(name = "is_sex_age_not_apply")
    private Boolean isSexAgeNotApply;
    
    // Numeric demographic fields
    @Column(name = "women_youth_number")
    private Integer womenYouthNumber;
    
    @Column(name = "women_non_youth_number")
    private Integer womenNonYouthNumber;
    
    @Column(name = "men_youth_number")
    private Integer menYouthNumber;
    
    @Column(name = "men_non_youth_number")
    private Integer menNonYouthNumber;
    
    @Column(name = "other", columnDefinition = "TEXT")
    private String other;
    
    @Column(name = "total")
    private Integer total;
    
    // Relationship with ProjectInnovation (temporarily commented to avoid circular dependencies)
    // @ManyToOne(fetch = FetchType.LAZY)
    // @JoinColumn(name = "innovation_id", insertable = false, updatable = false)
    // private ProjectInnovation projectInnovation;
    
    @PrePersist
    protected void onCreate() {
        if (activeSince == null) {
            activeSince = LocalDateTime.now();
        }
        if (isActive == null) {
            isActive = true;
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        activeSince = LocalDateTime.now();
    }
}