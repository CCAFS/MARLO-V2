package com.example.demo.modules.innovationreports.adapters.web.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;

@Schema(description = "Innovation report response")
public class InnovationReportResponseDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("innovation_id")
    private Long innovationId;

    @JsonProperty("user_name")
    private String userName;

    @JsonProperty("user_lastname")
    private String userLastname;

    @JsonProperty("user_email")
    private String userEmail;

    @JsonProperty("interest_narrative")
    private String interestNarrative;

    @JsonProperty("is_active")
    private Boolean isActive;

    @JsonProperty("active_since")
    private LocalDateTime activeSince;

    @JsonProperty("modification_justification")
    private String modificationJustification;

    public InnovationReportResponseDto() {
    }

    public InnovationReportResponseDto(Long id,
                                       Long innovationId,
                                       String userName,
                                       String userLastname,
                                       String userEmail,
                                       String interestNarrative,
                                       Boolean isActive,
                                       LocalDateTime activeSince,
                                       String modificationJustification) {
        this.id = id;
        this.innovationId = innovationId;
        this.userName = userName;
        this.userLastname = userLastname;
        this.userEmail = userEmail;
        this.interestNarrative = interestNarrative;
        this.isActive = isActive;
        this.activeSince = activeSince;
        this.modificationJustification = modificationJustification;
    }

    public Long getId() {
        return id;
    }

    public Long getInnovationId() {
        return innovationId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserLastname() {
        return userLastname;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public String getInterestNarrative() {
        return interestNarrative;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public LocalDateTime getActiveSince() {
        return activeSince;
    }

    public String getModificationJustification() {
        return modificationJustification;
    }
}
