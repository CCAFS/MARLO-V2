package com.example.demo.modules.innovationtype.application.port.inbound;

import com.example.demo.modules.innovationtype.domain.model.InnovationType;

import java.util.List;
import java.util.Optional;

/**
 * Use case interface for InnovationType operations
 * Defines business operations for innovation type management
 */
public interface InnovationTypeUseCase {
    
    /**
     * Find all innovation types
     * @return List of all innovation types
     */
    List<InnovationType> findAllInnovationTypes();
    
    /**
     * Find innovation type by ID
     * @param id Innovation type ID
     * @return Optional containing the innovation type if found
     */
    Optional<InnovationType> findInnovationTypeById(Long id);
}