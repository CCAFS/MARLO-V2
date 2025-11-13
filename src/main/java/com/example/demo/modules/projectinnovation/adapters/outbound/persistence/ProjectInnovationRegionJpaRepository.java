package com.example.demo.modules.projectinnovation.adapters.outbound.persistence;

import com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationRegion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA Repository for ProjectInnovationRegion entity
 */
@Repository
public interface ProjectInnovationRegionJpaRepository extends JpaRepository<ProjectInnovationRegion, Long> {
    
    List<ProjectInnovationRegion> findByProjectInnovationIdAndIdPhase(Long projectInnovationId, Long idPhase);
    
    @Query("SELECT pir FROM ProjectInnovationRegion pir " +
           "WHERE pir.projectInnovationId IN :innovationIds")
    List<ProjectInnovationRegion> findByInnovationIds(@Param("innovationIds") List<Long> innovationIds);
}