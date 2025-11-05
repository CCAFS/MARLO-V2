package com.example.demo.modules.projectinnovation.adapters.outbound.persistence;

import com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationComplementarySolution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for project_innovation_complementary_solutions table.
 */
@Repository
public interface ProjectInnovationComplementarySolutionJpaRepository extends JpaRepository<ProjectInnovationComplementarySolution, Long> {

    /**
     * Retrieve active complementary solutions for a specific innovation and phase.
     */
    List<ProjectInnovationComplementarySolution> findByProjectInnovationIdAndIdPhaseAndIsActiveTrue(Long projectInnovationId, Long idPhase);
}
