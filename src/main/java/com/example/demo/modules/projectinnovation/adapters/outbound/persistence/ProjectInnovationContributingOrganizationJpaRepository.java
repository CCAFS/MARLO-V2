package com.example.demo.modules.projectinnovation.adapters.outbound.persistence;

import com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationContributingOrganization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA Repository for ProjectInnovationContributingOrganization entity
 * Handles data access for contributing organizations
 */
@Repository
public interface ProjectInnovationContributingOrganizationJpaRepository extends JpaRepository<ProjectInnovationContributingOrganization, Long> {
    
    /**
     * Find contributing organizations by innovation ID and phase ID
     */
    @Query("SELECT pico FROM ProjectInnovationContributingOrganization pico " +
           "WHERE pico.projectInnovationId = :innovationId " +
           "AND pico.idPhase = :phaseId")
    List<ProjectInnovationContributingOrganization> findByProjectInnovationIdAndPhaseId(
            @Param("innovationId") Long innovationId, 
            @Param("phaseId") Long phaseId);
}