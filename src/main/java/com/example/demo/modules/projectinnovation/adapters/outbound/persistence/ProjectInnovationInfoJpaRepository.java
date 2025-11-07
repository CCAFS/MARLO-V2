package com.example.demo.modules.projectinnovation.adapters.outbound.persistence;

import com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA Repository for ProjectInnovationInfo entity
 */
@Repository
public interface ProjectInnovationInfoJpaRepository extends JpaRepository<ProjectInnovationInfo, Long> {
    
    List<ProjectInnovationInfo> findByProjectInnovationId(Long projectInnovationId);
    
    ProjectInnovationInfo findByProjectInnovationIdAndIdPhase(Long projectInnovationId, Long idPhase);
    
    @Query("SELECT pii FROM ProjectInnovationInfo pii " +
           "WHERE pii.projectInnovationId = :projectInnovationId AND pii.idPhase = :idPhase")
    ProjectInnovationInfo findByProjectInnovationIdAndIdPhaseWithDetails(@Param("projectInnovationId") Long projectInnovationId, @Param("idPhase") Long idPhase);
    
    // Find all innovations by phase
    List<ProjectInnovationInfo> findByIdPhase(Long idPhase);
    
    // OPTIMIZED: Using EXISTS instead of JOIN for better performance
    @Query("SELECT pii FROM ProjectInnovationInfo pii " +
           "WHERE pii.idPhase = :phaseId " +
           "AND EXISTS (SELECT 1 FROM ProjectInnovation pi " +
                       "WHERE pi.id = pii.projectInnovationId AND pi.isActive = true)")
    List<ProjectInnovationInfo> findByPhaseAndActiveInnovation(@Param("phaseId") Long phaseId);
    
    // NATIVE QUERY: Ultra-optimized for production
    @Query(value = "SELECT pii.* FROM project_innovation_info pii " +
                   "WHERE pii.id_phase = :phaseId " +
                   "AND pii.project_innovation_id IN (" +
                       "SELECT pi.id FROM project_innovations pi WHERE pi.is_active = 1)", 
           nativeQuery = true)
    List<ProjectInnovationInfo> findByPhaseAndActiveInnovationNative(@Param("phaseId") Long phaseId);
    
    // OPTIMIZED: Direct query for average calculation without loading full entities
    @Query("SELECT AVG(CAST(pii.readinessScale AS double)) FROM ProjectInnovationInfo pii " +
           "WHERE pii.idPhase = :phaseId " +
           "AND pii.readinessScale IS NOT NULL " +
           "AND EXISTS (SELECT 1 FROM ProjectInnovation pi " +
                       "WHERE pi.id = pii.projectInnovationId AND pi.isActive = true)")
    Double findAverageScalingReadinessByPhaseOptimized(@Param("phaseId") Long phaseId);

    @Query("SELECT pii FROM ProjectInnovationInfo pii " +
           "WHERE pii.projectInnovationId IN :projectInnovationIds " +
           "AND pii.idPhase = :phaseId " +
           "AND EXISTS (SELECT 1 FROM ProjectInnovation pi " +
                       "WHERE pi.id = pii.projectInnovationId AND pi.isActive = true)")
    List<ProjectInnovationInfo> findActiveByProjectInnovationIdsAndPhase(
            @Param("projectInnovationIds") List<Long> projectInnovationIds,
            @Param("phaseId") Long phaseId);

    @Query("SELECT pii.title FROM ProjectInnovationInfo pii " +
           "JOIN ProjectInnovation pi ON pi.id = pii.projectInnovationId " +
           "WHERE pii.projectInnovationId = :projectInnovationId " +
           "AND pii.idPhase = :phaseId " +
           "AND pi.isActive = true")
    Optional<String> findActiveTitleByProjectInnovationIdAndPhase(
            @Param("projectInnovationId") Long projectInnovationId,
            @Param("phaseId") Long phaseId);
    
    // JOIN queries temporarily commented for testing
    // @Query("SELECT pii FROM ProjectInnovationInfo pii " +
    //        "LEFT JOIN FETCH pii.innovationStage " +
    //        "LEFT JOIN FETCH pii.innovationType " +
    //        "WHERE pii.projectInnovationId = :projectInnovationId")
    // List<ProjectInnovationInfo> findByProjectInnovationIdWithReferences(@Param("projectInnovationId") Long projectInnovationId);
    
    // @Query("SELECT pii FROM ProjectInnovationInfo pii " +
    //        "LEFT JOIN FETCH pii.innovationStage " +
    //        "LEFT JOIN FETCH pii.innovationType " +
    //        "WHERE pii.id = :id")
    // ProjectInnovationInfo findByIdWithReferences(@Param("id") Long id);
    
    // Advanced filtering methods that return complete ProjectInnovationInfo (only active records)
    @Query(value = "SELECT DISTINCT pii.* FROM project_innovation_info pii " +
           "JOIN project_innovations p ON pii.project_innovation_id = p.id " +
           "WHERE p.is_active = true " +
           "AND (:phase IS NULL OR pii.id_phase = :phase) " +
           "AND (:readinessScale IS NULL OR pii.readiness_scale = :readinessScale) " +
           "AND (:innovationTypeId IS NULL OR pii.innovation_type_id = :innovationTypeId) " +
           "AND (:countryIds IS NULL OR EXISTS ( " +
               "SELECT 1 FROM project_innovation_countries pic " +
               "WHERE pic.project_innovation_id = pii.project_innovation_id " +
               "AND pic.id_phase = pii.id_phase " +
               "AND pic.id_country IN (:countryIds))) " +
           "ORDER BY pii.id DESC", nativeQuery = true)
    List<ProjectInnovationInfo> findActiveInnovationsInfoWithFilters(
            @Param("phase") Long phase,
            @Param("readinessScale") Integer readinessScale,
            @Param("innovationTypeId") Long innovationTypeId,
            @Param("countryIds") List<Long> countryIds);
    
    // Find innovation info by SDG relationship
    @Query(value = "SELECT DISTINCT pii.* FROM project_innovation_info pii " +
           "JOIN project_innovations p ON pii.project_innovation_id = p.id " +
           "JOIN project_innovation_sdgs pis ON p.id = pis.innovation_id AND pii.id_phase = pis.id_phase " +
           "WHERE p.is_active = true " +
           "AND pis.is_active = true " +
           "AND (:innovationId IS NULL OR pis.innovation_id = :innovationId) " +
           "AND (:phase IS NULL OR pis.id_phase = :phase) " +
           "AND (:sdgId IS NULL OR pis.sdg_id = :sdgId) " +
           "AND (:countryIds IS NULL OR EXISTS ( " +
               "SELECT 1 FROM project_innovation_countries pic " +
               "WHERE pic.project_innovation_id = pii.project_innovation_id " +
               "AND pic.id_phase = pii.id_phase " +
               "AND pic.id_country IN (:countryIds))) " +
           "ORDER BY pii.id DESC", nativeQuery = true)
    List<ProjectInnovationInfo> findActiveInnovationsInfoBySdgFilters(
            @Param("innovationId") Long innovationId,
            @Param("phase") Long phase,
            @Param("sdgId") Long sdgId,
            @Param("countryIds") List<Long> countryIds);
    
    // Find all active innovations info
    @Query(value = "SELECT DISTINCT pii.* FROM project_innovation_info pii " +
           "JOIN project_innovations p ON pii.project_innovation_id = p.id " +
           "WHERE p.is_active = true " +
           "ORDER BY pii.id DESC", nativeQuery = true)
    List<ProjectInnovationInfo> findAllActiveInnovationsInfo();
}
