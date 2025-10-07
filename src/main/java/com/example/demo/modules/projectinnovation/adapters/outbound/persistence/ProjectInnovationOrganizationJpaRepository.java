package com.example.demo.modules.projectinnovation.adapters.outbound.persistence;

import com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationOrganization;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA Repository for ProjectInnovationOrganization entity
 */
@Repository
public interface ProjectInnovationOrganizationJpaRepository extends JpaRepository<ProjectInnovationOrganization, Long> {
    
    List<ProjectInnovationOrganization> findByProjectInnovationIdAndIdPhase(Long projectInnovationId, Long idPhase);
    
    @Query("SELECT pio FROM ProjectInnovationOrganization pio " +
           "WHERE pio.projectInnovationId IN :innovationIds")
    List<ProjectInnovationOrganization> findByInnovationIds(@Param("innovationIds") List<Long> innovationIds);
}