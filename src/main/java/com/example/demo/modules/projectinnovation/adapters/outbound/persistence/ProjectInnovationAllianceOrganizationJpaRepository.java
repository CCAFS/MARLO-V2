package com.example.demo.modules.projectinnovation.adapters.outbound.persistence;

import com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationAllianceOrganization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA repository for alliance organizations related to project innovations.
 */
@Repository
public interface ProjectInnovationAllianceOrganizationJpaRepository extends JpaRepository<ProjectInnovationAllianceOrganization, Long> {

    List<ProjectInnovationAllianceOrganization> findByProjectInnovationIdAndIdPhaseAndIsActive(Long projectInnovationId, Long idPhase, Boolean isActive);
}
