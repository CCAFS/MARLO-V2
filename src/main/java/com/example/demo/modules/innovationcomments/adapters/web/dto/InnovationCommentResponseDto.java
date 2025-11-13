package com.example.demo.modules.innovationcomments.adapters.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

/**
 * DTO for Innovation Comment responses
 * Used for API responses containing comment data
 */
@Schema(description = "Innovation Comment Response")
public class InnovationCommentResponseDto {
    
    @Schema(description = "Comment unique identifier", example = "1")
    @JsonProperty("id")
    private Long id;
    
    @Schema(description = "Innovation ID this comment belongs to", example = "123")
    @JsonProperty("innovation_id")
    private Long innovationId;
    
    @Schema(description = "User's first name", example = "John")
    @JsonProperty("user_name")
    private String userName;
    
    @Schema(description = "User's last name", example = "Doe")
    @JsonProperty("user_lastname")
    private String userLastname;
    
    @Schema(description = "User's email address", example = "john.doe@example.com")
    @JsonProperty("user_email")
    private String userEmail;
    
    @Schema(description = "Comment text content", example = "This is a great innovation!")
    @JsonProperty("comment")
    private String comment;

    @Schema(description = "Innovation name", example = "Climate-smart irrigation technology")
    @JsonProperty("innovation_name")
    private String innovationName;

    @Schema(description = "Whether the comment is active", example = "true")
    @JsonProperty("is_active")
    private Boolean isActive;
    
    @Schema(description = "Comment creation/modification timestamp", example = "2024-01-15T10:30:00")
    @JsonProperty("active_since")
    private LocalDateTime activeSince;
    
    @Schema(description = "Modification justification", example = "Updated comment for clarity")
    @JsonProperty("modification_justification")
    private String modificationJustification;
    
    // Constructors
    
    public InnovationCommentResponseDto() {
    }
    
    public InnovationCommentResponseDto(Long id, Long innovationId, String userName, String userLastname,
                                      String userEmail, String comment, Boolean isActive,
                                      LocalDateTime activeSince, String modificationJustification) {
        this.id = id;
        this.innovationId = innovationId;
        this.userName = userName;
        this.userLastname = userLastname;
        this.userEmail = userEmail;
        this.comment = comment;
        this.innovationName = null;
        this.isActive = isActive;
        this.activeSince = activeSince;
        this.modificationJustification = modificationJustification;
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

    public String getInnovationName() {
        return innovationName;
    }

    public void setInnovationName(String innovationName) {
        this.innovationName = innovationName;
    }
}
