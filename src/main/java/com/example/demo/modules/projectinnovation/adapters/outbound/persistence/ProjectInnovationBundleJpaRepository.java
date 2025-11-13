package com.example.demo.modules.projectinnovation.adapters.outbound.persistence;

import com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationBundle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for project_innovation_bundles table.
 */
@Repository
public interface ProjectInnovationBundleJpaRepository extends JpaRepository<ProjectInnovationBundle, Long> {

    /**
     * Retrieve active bundles for a specific innovation and phase.
     */
    List<ProjectInnovationBundle> findByProjectInnovationIdAndIdPhaseAndIsActiveTrue(Long projectInnovationId, Long idPhase);
}
