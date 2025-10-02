package com.example.demo.modules.innovationtype.application.port.outbound;

import com.example.demo.modules.innovationtype.domain.model.InnovationType;

import java.util.List;
import java.util.Optional;

/**
 * Repository port interface for InnovationType data access
 * Defines data access operations for innovation types
 */
public interface InnovationTypeRepositoryPort {
    
    /**
     * Find all innovation types
     * @return List of all innovation types
     */
    List<InnovationType> findAll();
    
    /**
     * Find innovation type by ID
     * @param id Innovation type ID
     * @return Optional containing the innovation type if found
     */
    Optional<InnovationType> findById(Long id);
}