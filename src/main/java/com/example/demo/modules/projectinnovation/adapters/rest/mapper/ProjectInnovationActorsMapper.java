package com.example.demo.modules.projectinnovation.adapters.rest.mapper;

import com.example.demo.modules.projectinnovation.adapters.rest.dto.ProjectInnovationActorsResponse;
import com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationActors;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProjectInnovationActorsMapper {
    
    public ProjectInnovationActorsResponse toResponse(ProjectInnovationActors actors) {
        if (actors == null) {
            return null;
        }
        
        // Create actor info from the related actor entity
        ProjectInnovationActorsResponse.ActorInfo actorInfo = null;
        if (actors.getActor() != null) {
            actorInfo = new ProjectInnovationActorsResponse.ActorInfo(
                actors.getActor().getId(),
                actors.getActor().getName(),
                actors.getActor().getDescription(),
                actors.getActor().getIsActive()
            );
        }
        
        return new ProjectInnovationActorsResponse(
            actors.getId(),
            actors.getInnovationId(),
            actors.getActorId(),
            actorInfo,  // Include complete actor information
            actors.getIsActive(),
            actors.getActiveSince(),
            actors.getCreatedBy(),
            actors.getModifiedBy(),
            actors.getModificationJustification(),
            actors.getIdPhase(),
            actors.getIsWomenYouth(),
            actors.getIsWomenNotYouth(),
            actors.getIsMenYouth(),
            actors.getIsMenNotYouth(),
            actors.getIsNonbinaryYouth(),
            actors.getIsNonbinaryNotYouth(),
            actors.getIsSexAgeNotApply(),
            actors.getWomenYouthNumber(),
            actors.getWomenNonYouthNumber(),
            actors.getMenYouthNumber(),
            actors.getMenNonYouthNumber(),
            actors.getOther(),
            actors.getTotal()
        );
    }
    
    public List<ProjectInnovationActorsResponse> toResponseList(List<ProjectInnovationActors> actorsList) {
        if (actorsList == null) {
            return null;
        }
        
        return actorsList.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
}