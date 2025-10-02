package com.example.demo.modules.projectinnovation.adapters.outbound.persistence;

import com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositorio JPA para la entidad ProjectInnovationInfo
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
    
    @Query("SELECT pii FROM ProjectInnovationInfo pii " +
           "JOIN ProjectInnovation pi ON pii.projectInnovationId = pi.id " +
           "WHERE pii.idPhase = :phaseId AND pi.isActive = true")
    List<ProjectInnovationInfo> findByPhaseAndActiveInnovation(@Param("phaseId") Long phaseId);
    
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
           "ORDER BY pii.id DESC", nativeQuery = true)
    List<ProjectInnovationInfo> findActiveInnovationsInfoWithFilters(
            @Param("phase") Long phase,
            @Param("readinessScale") Integer readinessScale,
            @Param("innovationTypeId") Long innovationTypeId);
    
    // Find innovation info by SDG relationship
    @Query(value = "SELECT DISTINCT pii.* FROM project_innovation_info pii " +
           "JOIN project_innovations p ON pii.project_innovation_id = p.id " +
           "JOIN project_innovation_sdgs pis ON p.id = pis.innovation_id AND pii.id_phase = pis.id_phase " +
           "WHERE p.is_active = true " +
           "AND pis.is_active = true " +
           "AND (:innovationId IS NULL OR pis.innovation_id = :innovationId) " +
           "AND (:phase IS NULL OR pis.id_phase = :phase) " +
           "AND (:sdgId IS NULL OR pis.sdg_id = :sdgId) " +
           "ORDER BY pii.id DESC", nativeQuery = true)
    List<ProjectInnovationInfo> findActiveInnovationsInfoBySdgFilters(
            @Param("innovationId") Long innovationId,
            @Param("phase") Long phase,
            @Param("sdgId") Long sdgId);
    
    // Find all active innovations info
    @Query(value = "SELECT DISTINCT pii.* FROM project_innovation_info pii " +
           "JOIN project_innovations p ON pii.project_innovation_id = p.id " +
           "WHERE p.is_active = true " +
           "ORDER BY pii.id DESC", nativeQuery = true)
    List<ProjectInnovationInfo> findAllActiveInnovationsInfo();
}