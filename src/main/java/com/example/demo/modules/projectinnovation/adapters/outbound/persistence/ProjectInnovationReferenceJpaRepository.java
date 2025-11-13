package com.example.demo.modules.projectinnovation.adapters.outbound.persistence;

import com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationReference;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA Repository for ProjectInnovationReference entity.
 */
@Repository
public interface ProjectInnovationReferenceJpaRepository extends JpaRepository<ProjectInnovationReference, Long> {
    
    /**
     * Find references by innovation and phase.
     */
    List<ProjectInnovationReference> findByProjectInnovationIdAndIdPhase(Long projectInnovationId, Long idPhase);
}
