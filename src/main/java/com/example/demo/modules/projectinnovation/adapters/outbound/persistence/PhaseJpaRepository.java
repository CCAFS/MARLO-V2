package com.example.demo.modules.projectinnovation.adapters.outbound.persistence;

import com.example.demo.modules.projectinnovation.domain.model.Phase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository for Phase entity
 */
@Repository
public interface PhaseJpaRepository extends JpaRepository<Phase, Long> {
    // Basic methods like findById are already included in JpaRepository
}