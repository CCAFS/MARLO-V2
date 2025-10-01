package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import java.time.LocalDateTime;

public record ProjectInnovationActorsResponse(
    Long id,
    Long innovationId,
    Long actorId,
    ActorInfo actorInfo,  // Complete actor information
    Boolean isActive,
    LocalDateTime activeSince,
    Long createdBy,
    Long modifiedBy,
    String modificationJustification,
    Long idPhase,
    
    // Demographic fields
    Boolean isWomenYouth,
    Boolean isWomenNotYouth,
    Boolean isMenYouth,
    Boolean isMenNotYouth,
    Boolean isNonbinaryYouth,
    Boolean isNonbinaryNotYouth,
    Boolean isSexAgeNotApply,
    
    // Numeric demographic fields
    Integer womenYouthNumber,
    Integer womenNonYouthNumber,
    Integer menYouthNumber,
    Integer menNonYouthNumber,
    String other,
    Integer total
) {

    /**
     * Nested record for actor information
     */
    public record ActorInfo(
        Long id,
        String name,
        String description,
        Boolean isActive
    ) {}
}