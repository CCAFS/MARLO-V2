package com.example.demo.modules.innovationsubscription.domain.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity representing email subscriptions to innovation catalog updates.
 */
@Entity
@Table(name = "innovation_catalog_suscriptions")
public class InnovationCatalogSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "active_since", nullable = false)
    private LocalDateTime activeSince = LocalDateTime.now();

    @Column(name = "modification_justification")
    private String modificationJustification;

    public InnovationCatalogSubscription() {
        // Default constructor for JPA
    }

    public InnovationCatalogSubscription(String email) {
        this.email = email;
        this.isActive = true;
        this.activeSince = LocalDateTime.now();
    }

    @PrePersist
    protected void onCreate() {
        if (activeSince == null) {
            activeSince = LocalDateTime.now();
        }
        if (isActive == null) {
            isActive = true;
        }
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
        this.activeSince = LocalDateTime.now();
    }

    public LocalDateTime getActiveSince() {
        return activeSince;
    }

    public void setActiveSince(LocalDateTime activeSince) {
        this.activeSince = activeSince;
    }

    public String getModificationJustification() {
        return modificationJustification;
    }

    public void setModificationJustification(String modificationJustification) {
        this.modificationJustification = modificationJustification;
    }
}
