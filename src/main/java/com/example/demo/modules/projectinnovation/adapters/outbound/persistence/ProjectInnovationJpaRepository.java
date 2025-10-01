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
    
    // Search with additional information (when relationships are reactivated)
    // @Query("SELECT p FROM ProjectInnovation p LEFT JOIN FETCH p.innovationInfo WHERE p.id = :id AND p.isActive = true")
    // Optional<ProjectInnovation> findByIdWithInfo(@Param("id") Long id);
    
    // @Query("SELECT p FROM ProjectInnovation p LEFT JOIN FETCH p.innovationInfo WHERE p.isActive = true")
    // List<ProjectInnovation> findAllActiveWithInfo();
    
    // @Query("SELECT p FROM ProjectInnovation p " +
    //        "LEFT JOIN FETCH p.innovationInfo " +
    //        "WHERE p.projectId = :projectId AND p.isActive = true")
    // List<ProjectInnovation> findActiveByProjectIdWithInfo(@Param("projectId") Long projectId);
}