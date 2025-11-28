package com.example.demo.modules.actors.application.service;

import com.example.demo.modules.actors.application.port.out.ActorRepositoryPort;
import com.example.demo.modules.projectinnovation.domain.model.Actor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service for Actor operations
 * Implements business logic for actor management
 */
@Service
public class ActorService {
    
    private final ActorRepositoryPort actorRepositoryPort;
    
    public ActorService(ActorRepositoryPort actorRepositoryPort) {
        this.actorRepositoryPort = actorRepositoryPort;
    }
    
    /**
     * Get all active actors
     * @return List of active actors
     */
    public List<Actor> getAllActiveActors() {
        return actorRepositoryPort.findAllActive();
    }
}
