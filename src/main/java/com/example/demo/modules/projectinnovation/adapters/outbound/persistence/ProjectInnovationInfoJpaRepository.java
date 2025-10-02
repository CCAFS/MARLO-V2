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
}