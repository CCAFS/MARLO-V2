package com.example.demo.modules.innovationcomments.adapters.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * DTO for creating new innovation comments
 * Used for API requests to create comments
 */
@Schema(description = "Create Innovation Comment Request")
public class CreateInnovationCommentRequestDto {
    
    @Schema(description = "Innovation ID this comment belongs to", example = "123", required = true)
    @JsonProperty("innovation_id")
    @NotNull(message = "Innovation ID is required")
    private Long innovationId;
    
    @Schema(description = "User's first name", example = "John", required = true)
    @JsonProperty("user_name")
    @NotBlank(message = "User name is required")
    private String userName;
    
    @Schema(description = "User's last name", example = "Doe")
    @JsonProperty("user_lastname")
    private String userLastname;
    
    @Schema(description = "User's email address", example = "john.doe@example.com", required = true)
    @JsonProperty("user_email")
    @NotBlank(message = "User email is required")
    @Email(message = "Invalid email format")
    private String userEmail;
    
    @Schema(description = "Comment text content", example = "This is a great innovation!")
    @JsonProperty("comment")
    private String comment;
    
    @Schema(description = "Modification justification", example = "Initial comment creation")
    @JsonProperty("modification_justification")
    private String modificationJustification;
    
    // Constructors
    
    public CreateInnovationCommentRequestDto() {
    }
    
    public CreateInnovationCommentRequestDto(Long innovationId, String userName, String userLastname,
                                           String userEmail, String comment) {
        this.innovationId = innovationId;
        this.userName = userName;
        this.userLastname = userLastname;
        this.userEmail = userEmail;
        this.comment = comment;
    }
    
    public CreateInnovationCommentRequestDto(Long innovationId, String userName, String userLastname,
                                           String userEmail, String comment, String modificationJustification) {
        this(innovationId, userName, userLastname, userEmail, comment);
        this.modificationJustification = modificationJustification;
    }
    
    // Getters and Setters
    
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
    
    public String getModificationJustification() {
        return modificationJustification;
    }
    
    public void setModificationJustification(String modificationJustification) {
        this.modificationJustification = modificationJustification;
    }
}
