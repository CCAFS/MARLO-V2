package com.example.demo.modules.actors.adapters.outbound.persistence;

import com.example.demo.modules.actors.application.port.out.ActorRepositoryPort;
import com.example.demo.modules.projectinnovation.adapters.outbound.persistence.ActorJpaRepository;
import com.example.demo.modules.projectinnovation.domain.model.Actor;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Repository adapter implementation for Actor
 * Implements the repository port using JPA repository
 */
@Component
public class ActorRepositoryAdapter implements ActorRepositoryPort {
    
    private final ActorJpaRepository actorJpaRepository;
    
    public ActorRepositoryAdapter(ActorJpaRepository actorJpaRepository) {
        this.actorJpaRepository = actorJpaRepository;
    }
    
    @Override
    public List<Actor> findAllActive() {
        return actorJpaRepository.findAllActive();
    }
}
