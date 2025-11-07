package com.example.demo.modules.innovationcomments.domain.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Entity for Innovation Catalog Comments
 * Stores user comments related to innovations
 */
@Entity
@Table(name = "innovation_catalog_comments")
public class InnovationCatalogComment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "innovation_id", nullable = false)
    private Long innovationId;
    
    @Column(name = "user_name", nullable = false)
    private String userName;
    
    @Column(name = "user_lastname")
    private String userLastname;
    
    @Column(name = "user_email", nullable = false)
    private String userEmail;
    
    @Column(name = "comment", columnDefinition = "TEXT")
    private String comment;
    
    @Column(name = "is_active", nullable = false)
    private Boolean isActive = true;
    
    @Column(name = "active_since", nullable = false)
    private LocalDateTime activeSince;
    
    @Column(name = "modification_justification", columnDefinition = "TEXT")
    private String modificationJustification;
    
    // Constructors
    public InnovationCatalogComment() {
        this.activeSince = LocalDateTime.now();
        this.isActive = true;
    }
    
    public InnovationCatalogComment(Long innovationId, String userName, String userLastname, String userEmail) {
        this();
        this.innovationId = innovationId;
        this.userName = userName;
        this.userLastname = userLastname;
        this.userEmail = userEmail;
    }
    
    public InnovationCatalogComment(Long innovationId, String userName, String userLastname, 
                                 String userEmail, String comment) {
        this(innovationId, userName, userLastname, userEmail);
        this.comment = comment;
    }
    
    // Getters and Setters
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
    
    public String getComment() {
        return comment;
    }
    
    public void setComment(String comment) {
        this.comment = comment;
    }
    
    public Boolean getIsActive() {
        return isActive;
    }
    
    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
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
    
    // Audit methods
    @PrePersist
    protected void onCreate() {
        activeSince = LocalDateTime.now();
        if (isActive == null) {
            isActive = true;
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        activeSince = LocalDateTime.now();
    }
    
    @Override
    public String toString() {
        return "InnovationCatalogComment{" +
                "id=" + id +
                ", innovationId=" + innovationId +
                ", userName='" + userName + '\'' +
                ", userLastname='" + userLastname + '\'' +
                ", userEmail='" + userEmail + '\'' +
                ", isActive=" + isActive +
                ", activeSince=" + activeSince +
                '}';
    }
}
