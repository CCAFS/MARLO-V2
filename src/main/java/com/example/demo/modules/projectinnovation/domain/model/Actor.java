package com.example.demo.modules.projectinnovation.domain.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Actor entity representing the actors table.
 * Contains information about different types of actors involved in innovations.
 */
@Entity
@Table(name = "actors")
public class Actor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", columnDefinition = "TEXT")
    private String name;

    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

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

    @Column(name = "prms_id_equivalent")
    private Long prmsIdEquivalent;

    @Column(name = "prms_name_equivalent", columnDefinition = "TEXT")
    private String prmsNameEquivalent;

    // Default constructor
    public Actor() {}

    // Constructor with essential fields
    public Actor(String name, String description, Boolean isActive) {
        this.name = name;
        this.description = description;
        this.isActive = isActive;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public LocalDateTime getActiveSince() {
        return activeSince;
    }

    public void setActiveSince(LocalDateTime activeSince) {
        this.activeSince = activeSince;
    }

    public Long getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Long createdBy) {
        this.createdBy = createdBy;
    }

    public Long getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Long modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public String getModificationJustification() {
        return modificationJustification;
    }

    public void setModificationJustification(String modificationJustification) {
        this.modificationJustification = modificationJustification;
    }

    public Long getPrmsIdEquivalent() {
        return prmsIdEquivalent;
    }

    public void setPrmsIdEquivalent(Long prmsIdEquivalent) {
        this.prmsIdEquivalent = prmsIdEquivalent;
    }

    public String getPrmsNameEquivalent() {
        return prmsNameEquivalent;
    }

    public void setPrmsNameEquivalent(String prmsNameEquivalent) {
        this.prmsNameEquivalent = prmsNameEquivalent;
    }

    @Override
    public String toString() {
        return "Actor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", isActive=" + isActive +
                '}';
    }
}