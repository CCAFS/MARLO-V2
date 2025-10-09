package com.example.demo.modules.projectinnovation.adapters.outbound.persistence;

import com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationCountry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA Repository for ProjectInnovationCountry entity
 */
@Repository
public interface ProjectInnovationCountryJpaRepository extends JpaRepository<ProjectInnovationCountry, Integer> {
    
    List<ProjectInnovationCountry> findByProjectInnovationIdAndIdPhase(Long projectInnovationId, Long idPhase);
    
    @Query("SELECT pic FROM ProjectInnovationCountry pic " +
           "WHERE pic.projectInnovationId IN :innovationIds")
    List<ProjectInnovationCountry> findByInnovationIds(@Param("innovationIds") List<Long> innovationIds);
    
    @Query("SELECT COUNT(DISTINCT pic.idCountry) FROM ProjectInnovationCountry pic " +
           "JOIN ProjectInnovation pi ON pic.projectInnovationId = pi.id " +
           "WHERE pi.isActive = true " +
           "AND (:innovationId IS NULL OR pic.projectInnovationId = :innovationId) " +
           "AND (:phaseId IS NULL OR pic.idPhase = :phaseId)")
    Long countDistinctCountriesByInnovationAndPhase(@Param("innovationId") Long innovationId, @Param("phaseId") Long phaseId);
    
    @Query("SELECT COUNT(DISTINCT pic.projectInnovationId) FROM ProjectInnovationCountry pic " +
           "JOIN ProjectInnovation pi ON pic.projectInnovationId = pi.id " +
           "WHERE pi.isActive = true " +
           "AND (:innovationId IS NULL OR pic.projectInnovationId = :innovationId) " +
           "AND (:phaseId IS NULL OR pic.idPhase = :phaseId)")
    Long countDistinctInnovationsByInnovationAndPhase(@Param("innovationId") Long innovationId, @Param("phaseId") Long phaseId);
}