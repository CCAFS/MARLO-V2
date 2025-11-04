package com.example.demo.modules.innovationreports.adapters.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "Create Innovation Report Request")
public class CreateInnovationReportRequestDto {

    @Schema(description = "Innovation ID this report refers to", example = "123", required = true)
    @JsonProperty("innovation_id")
    @NotNull(message = "Innovation ID is required")
    private Long innovationId;

    @Schema(description = "Reporter first name", example = "Jane", required = true)
    @JsonProperty("user_name")
    @NotBlank(message = "User name is required")
    private String userName;

    @Schema(description = "Reporter last name", example = "Smith", required = true)
    @JsonProperty("user_lastname")
    @NotBlank(message = "User lastname is required")
    private String userLastname;

    @Schema(description = "Reporter email address", example = "jane.smith@example.com", required = true)
    @JsonProperty("user_email")
    @NotBlank(message = "User email is required")
    @Email(message = "Invalid email format")
    private String userEmail;

    @Schema(description = "Narrative describing the stakeholder interest in the innovation", example = "Interested in piloting the innovation in West Africa.")
    @JsonProperty("interest_narrative")
    private String interestNarrative;

    @Schema(description = "Optional justification for the report creation", example = "Initial stakeholder outreach")
    @JsonProperty("modification_justification")
    private String modificationJustification;

    public CreateInnovationReportRequestDto() {
    }

    public CreateInnovationReportRequestDto(Long innovationId, String userName, String userLastname, String userEmail, String interestNarrative) {
        this.innovationId = innovationId;
        this.userName = userName;
        this.userLastname = userLastname;
        this.userEmail = userEmail;
        this.interestNarrative = interestNarrative;
    }

    public CreateInnovationReportRequestDto(Long innovationId, String userName, String userLastname, String userEmail, String interestNarrative, String modificationJustification) {
        this(innovationId, userName, userLastname, userEmail, interestNarrative);
        this.modificationJustification = modificationJustification;
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

    public String getModificationJustification() {
        return modificationJustification;
    }

    public void setModificationJustification(String modificationJustification) {
        this.modificationJustification = modificationJustification;
    }
}
