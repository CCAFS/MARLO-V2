package com.example.demo.modules.innovationreports.domain.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity that maps the innovation_catalog_reports table.
 * Stores stakeholder interest reports associated with an innovation.
 */
@Entity
@Table(name = "innovation_catalog_reports")
public class InnovationCatalogReport {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "innovation_id")
    private Long innovationId;

    @Column(name = "user_name")
    private String userName;

    @Column(name = "user_lastname")
    private String userLastname;

    @Column(name = "user_email")
    private String userEmail;

    @Column(name = "interest_narrative", columnDefinition = "TEXT")
    private String interestNarrative;

    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;

    @Column(name = "active_since", nullable = false)
    private LocalDateTime activeSince = LocalDateTime.now();

    @Column(name = "modification_justification", columnDefinition = "TEXT")
    private String modificationJustification;

    public InnovationCatalogReport() {
    }

    public InnovationCatalogReport(Long innovationId, String userName, String userLastname, String userEmail, String interestNarrative) {
        this.innovationId = innovationId;
        this.userName = userName;
        this.userLastname = userLastname;
        this.userEmail = userEmail;
        this.interestNarrative = interestNarrative;
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

    @PreUpdate
    protected void onUpdate() {
        activeSince = LocalDateTime.now();
    }

    // Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getInnovationId() {
        return innovationId;
    }

    public void setInnovationId(Long innovationId) {
        this.innovationId = innovationId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserLastname() {
        return userLastname;
    }

    public void setUserLastname(String userLastname) {
        this.userLastname = userLastname;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getInterestNarrative() {
        return interestNarrative;
    }

    public void setInterestNarrative(String interestNarrative) {
        this.interestNarrative = interestNarrative;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean active) {
        isActive = active;
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
