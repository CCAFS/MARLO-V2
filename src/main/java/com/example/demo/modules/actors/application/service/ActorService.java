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
     * Get active actors optionally filtered by name fragment
     * @param nameFilter Optional filter (case-insensitive)
     * @return List of active actors
     */
    public List<Actor> getActiveActors(String nameFilter) {
        String normalizedFilter = normalizeFilter(nameFilter);
        if (normalizedFilter == null) {
            return actorRepositoryPort.findAllActive();
        }
        return actorRepositoryPort.findActiveByNameContaining(normalizedFilter);
    }

    private String normalizeFilter(String nameFilter) {
        if (nameFilter == null) {
            return null;
        }

        String trimmed = nameFilter.trim();
        if (trimmed.isEmpty()) {
            return null;
        }

        if ("null".equalsIgnoreCase(trimmed) || "undefined".equalsIgnoreCase(trimmed)) {
            return null;
        }

        return trimmed;
    }
}
