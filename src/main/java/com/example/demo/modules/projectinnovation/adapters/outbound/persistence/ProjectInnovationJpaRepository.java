package com.example.demo.modules.projectinnovation.adapters.outbound.persistence;

import com.example.demo.modules.projectinnovation.domain.model.ProjectInnovation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA Repository for ProjectInnovation entity
 */
@Repository
public interface ProjectInnovationJpaRepository extends JpaRepository<ProjectInnovation, Long> {
    
    List<ProjectInnovation> findByIsActive(Boolean isActive);
    
    // Default query that only returns active records
    @Query("SELECT p FROM ProjectInnovation p WHERE p.isActive = true")
    List<ProjectInnovation> findAllActive();
    
    List<ProjectInnovation> findByProjectId(Long projectId);
    
    // Search by project ID only active records
    List<ProjectInnovation> findByProjectIdAndIsActive(Long projectId, Boolean isActive);
    
    @Query("SELECT COUNT(p) FROM ProjectInnovation p WHERE p.projectId = :projectId AND p.isActive = true")
    Long countActiveByProjectId(@Param("projectId") Long projectId);
    
    // Custom methods for specific searches
    Optional<ProjectInnovation> findByIdAndIsActive(Long id, Boolean isActive);
    
    // Find innovations by phase
    @Query("SELECT DISTINCT p FROM ProjectInnovation p " +
           "WHERE p.isActive = true " +
           "AND EXISTS (SELECT 1 FROM ProjectInnovationActors pia WHERE pia.innovationId = p.id AND pia.idPhase = :phaseId AND pia.isActive = true)")
    List<ProjectInnovation> findActiveInnovationsByPhase(@Param("phaseId") Integer phaseId);
    
    // Advanced filtering methods for complete innovation search
    
    // Find all active innovations with complex filters
    @Query(value = "SELECT DISTINCT p.* FROM project_innovations p " +
           "JOIN project_innovation_info pii ON p.id = pii.project_innovation_id " +
           "WHERE p.is_active = true " +
           "AND (:phase IS NULL OR pii.id_phase = :phase) " +
           "AND (:readinessScale IS NULL OR pii.readiness_scale = :readinessScale) " +
           "AND (:innovationTypeId IS NULL OR pii.innovation_type_id = :innovationTypeId) " +
           "ORDER BY p.id DESC", nativeQuery = true)
    List<ProjectInnovation> findActiveInnovationsWithFilters(
            @Param("phase") Long phase,
            @Param("readinessScale") Integer readinessScale,
            @Param("innovationTypeId") Long innovationTypeId);
    
    // Find innovations by SDG relationship
    @Query(value = "SELECT DISTINCT p.* FROM project_innovations p " +
           "JOIN project_innovation_sdgs pis ON p.id = pis.innovation_id " +
           "WHERE p.is_active = true " +
           "AND pis.is_active = true " +
           "AND (:innovationId IS NULL OR pis.innovation_id = :innovationId) " +
           "AND (:phase IS NULL OR pis.id_phase = :phase) " +
           "AND (:sdgId IS NULL OR pis.sdg_id = :sdgId) " +
           "ORDER BY p.id DESC", nativeQuery = true)
    List<ProjectInnovation> findActiveInnovationsBySdgFilters(
            @Param("innovationId") Long innovationId,
            @Param("phase") Long phase,
            @Param("sdgId") Long sdgId);
    
    // Find all active innovations with complete information
    @Query("SELECT DISTINCT p FROM ProjectInnovation p " +
           "LEFT JOIN ProjectInnovationInfo pii ON p.id = pii.projectInnovationId " +
           "WHERE p.isActive = true " +
           "ORDER BY p.id DESC")
    List<ProjectInnovation> findAllActiveInnovationsComplete();
}