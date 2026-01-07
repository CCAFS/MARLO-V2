package com.example.demo.modules.actors.application.port.out;

import com.example.demo.modules.projectinnovation.domain.model.Actor;

import java.util.List;

/**
 * Output port for Actor repository operations
 */
public interface ActorRepositoryPort {
    
    /**
     * Find all active actors
     * @return List of active actors
     */
    List<Actor> findAllActive();
}
