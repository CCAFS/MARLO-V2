package com.example.demo.modules.projectinnovation.adapters.outbound.persistence;

import com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationActors;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA Repository for ProjectInnovationActors entity
 */
@Repository
public interface ProjectInnovationActorsJpaRepository extends JpaRepository<ProjectInnovationActors, Long> {
    
    /**
     * Find actors by innovation ID (use innovation_id instead of project_innovation_id)
     */
    List<ProjectInnovationActors> findByInnovationId(Long innovationId);
    
    /**
     * Find active actors by innovation ID with actor information
     */
    @Query("SELECT pia FROM ProjectInnovationActors pia " +
           "LEFT JOIN FETCH pia.actor " +
           "WHERE pia.innovationId = :innovationId AND pia.isActive = :isActive")
    List<ProjectInnovationActors> findByInnovationIdAndIsActive(@Param("innovationId") Long innovationId, @Param("isActive") Boolean isActive);
    
    /**
     * Find active actors by innovation ID with actor information
     */
    @Query("SELECT pia FROM ProjectInnovationActors pia " +
           "LEFT JOIN FETCH pia.actor " +
           "WHERE pia.innovationId = :innovationId AND pia.isActive = true")
    List<ProjectInnovationActors> findByInnovationIdAndIsActiveTrue(@Param("innovationId") Long innovationId);
    
    List<ProjectInnovationActors> findByIdPhaseAndIsActiveTrue(Integer idPhase);
    
    List<ProjectInnovationActors> findByInnovationIdAndIdPhaseAndIsActiveTrue(Long innovationId, Integer idPhase);
    
    /**
     * Find actors by phase
     */
    List<ProjectInnovationActors> findByIdPhase(Long idPhase);
    
    /**
     * Find active actors by phase with actor information
     */
    @Query("SELECT pia FROM ProjectInnovationActors pia " +
           "LEFT JOIN FETCH pia.actor " +
           "WHERE pia.idPhase = :idPhase " +
           "AND pia.isActive = true")
    List<ProjectInnovationActors> findActiveByPhase(@Param("idPhase") Long idPhase);
    
    /**
     * Find actors by actor_id
     */
    List<ProjectInnovationActors> findByActorId(Long actorId);
    
    /**
     * Find active actors by actor_id
     */
    @Query("SELECT pia FROM ProjectInnovationActors pia " +
           "WHERE pia.actorId = :actorId " +
           "AND pia.isActive = true")
    List<ProjectInnovationActors> findActiveByActorId(@Param("actorId") Long actorId);
    
    /**
     * Count actors by innovation
     */
    @Query("SELECT COUNT(pia) FROM ProjectInnovationActors pia " +
           "WHERE pia.innovationId = :innovationId " +
           "AND pia.isActive = true")
    Long countActiveByInnovationId(@Param("innovationId") Long innovationId);
    
    /**
     * Find actors with complete information (includes active innovations and actor details)
     * Note: We use innovation_id to relate with project_innovations.id
     */
    @Query("SELECT pia FROM ProjectInnovationActors pia " +
           "LEFT JOIN FETCH pia.actor " +
           "JOIN ProjectInnovation pi ON pia.innovationId = pi.id " +
           "WHERE pi.isActive = true AND pia.isActive = true " +
           "AND pia.idPhase = :idPhase")
    List<ProjectInnovationActors> findActiveActorsWithActiveInnovationsByPhase(@Param("idPhase") Long idPhase);
    
    /**
     * Find actors with demographic information
     */
    @Query("SELECT pia FROM ProjectInnovationActors pia " +
           "WHERE pia.innovationId = :innovationId " +
           "AND pia.isActive = true " +
           "AND (pia.total IS NOT NULL OR " +
           "     pia.womenYouthNumber IS NOT NULL OR " +
           "     pia.menYouthNumber IS NOT NULL)")
    List<ProjectInnovationActors> findActorsWithDemographicData(@Param("innovationId") Long innovationId);
}