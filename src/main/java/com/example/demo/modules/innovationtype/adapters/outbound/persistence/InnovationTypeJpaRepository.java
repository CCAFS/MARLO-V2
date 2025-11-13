package com.example.demo.modules.innovationtype.adapters.outbound.persistence;

import com.example.demo.modules.innovationtype.domain.model.InnovationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository for InnovationType entity
 * Provides data access methods for innovation types
 */
@Repository
public interface InnovationTypeJpaRepository extends JpaRepository<InnovationType, Long> {
    
    // Basic CRUD operations inherited from JpaRepository
}