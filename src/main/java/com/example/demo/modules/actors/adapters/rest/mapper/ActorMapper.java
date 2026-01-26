package com.example.demo.modules.actors.adapters.rest.mapper;

import com.example.demo.modules.actors.adapters.rest.dto.ActorResponse;
import com.example.demo.modules.projectinnovation.domain.model.Actor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Mapper for Actor entity to ActorResponse DTO
 */
@Component
public class ActorMapper {
    
    public ActorResponse toResponse(Actor actor) {
        if (actor == null) {
            return null;
        }
        
        return new ActorResponse(
            actor.getId(),
            actor.getName(),
            actor.getPrmsNameEquivalent()
        );
    }
    
    public List<ActorResponse> toResponseList(List<Actor> actors) {
        if (actors == null) {
            return List.of();
        }
        
        return actors.stream()
                .map(this::toResponse)
                .toList();
    }
}
