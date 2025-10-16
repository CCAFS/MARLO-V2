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
    
    // OPTIMIZED: Using EXISTS instead of JOIN for better performance
    @Query("SELECT COUNT(DISTINCT pic.idCountry) FROM ProjectInnovationCountry pic " +
           "WHERE (:phaseId IS NULL OR pic.idPhase = :phaseId) " +
           "AND (:innovationId IS NULL OR pic.projectInnovationId = :innovationId) " +
           "AND EXISTS (SELECT 1 FROM ProjectInnovation pi " +
                       "WHERE pi.id = pic.projectInnovationId AND pi.isActive = true)")
    Long countDistinctCountriesByInnovationAndPhase(@Param("innovationId") Long innovationId, @Param("phaseId") Long phaseId);
    
    // OPTIMIZED: Using EXISTS instead of JOIN for better performance
    @Query("SELECT COUNT(DISTINCT pic.projectInnovationId) FROM ProjectInnovationCountry pic " +
           "WHERE (:phaseId IS NULL OR pic.idPhase = :phaseId) " +
           "AND (:innovationId IS NULL OR pic.projectInnovationId = :innovationId) " +
           "AND EXISTS (SELECT 1 FROM ProjectInnovation pi " +
                       "WHERE pi.id = pic.projectInnovationId AND pi.isActive = true)")
    Long countDistinctInnovationsByInnovationAndPhase(@Param("innovationId") Long innovationId, @Param("phaseId") Long phaseId);
    
    // NATIVE QUERY: Ultra-optimized for production with proper indexing
    @Query(value = "SELECT COUNT(DISTINCT pic.id_country) " +
                   "FROM project_innovation_country pic " +
                   "WHERE (:phaseId IS NULL OR pic.id_phase = :phaseId) " +
                   "AND (:innovationId IS NULL OR pic.project_innovation_id = :innovationId) " +
                   "AND pic.project_innovation_id IN (" +
                       "SELECT pi.id FROM project_innovations pi WHERE pi.is_active = 1)", 
           nativeQuery = true)
    Long countDistinctCountriesByInnovationAndPhaseNative(@Param("innovationId") Long innovationId, @Param("phaseId") Long phaseId);
    
    // NATIVE QUERY: Ultra-optimized for production with proper indexing
    @Query(value = "SELECT COUNT(DISTINCT pic.project_innovation_id) " +
                   "FROM project_innovation_country pic " +
                   "WHERE (:phaseId IS NULL OR pic.id_phase = :phaseId) " +
                   "AND (:innovationId IS NULL OR pic.project_innovation_id = :innovationId) " +
                   "AND pic.project_innovation_id IN (" +
                       "SELECT pi.id FROM project_innovations pi WHERE pi.is_active = 1)", 
           nativeQuery = true)
    Long countDistinctInnovationsByInnovationAndPhaseNative(@Param("innovationId") Long innovationId, @Param("phaseId") Long phaseId);
}