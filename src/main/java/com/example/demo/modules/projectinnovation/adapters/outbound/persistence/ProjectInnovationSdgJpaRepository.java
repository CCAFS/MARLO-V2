package com.example.demo.modules.projectinnovation.adapters.outbound.persistence;

import com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationSdg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProjectInnovationSdgJpaRepository extends JpaRepository<ProjectInnovationSdg, Long> {
    
    List<ProjectInnovationSdg> findByInnovationIdAndIsActive(Long innovationId, Boolean isActive);
    
    List<ProjectInnovationSdg> findByInnovationIdAndIdPhaseAndIsActive(Long innovationId, Long idPhase, Boolean isActive);
    
    @Query("SELECT pis FROM ProjectInnovationSdg pis " +
           "WHERE pis.isActive = true " +
           "AND (:innovationId IS NULL OR pis.innovationId = :innovationId) " +
           "AND (:phase IS NULL OR pis.idPhase = :phase) " +
           "AND (:sdgId IS NULL OR pis.sdgId = :sdgId)")
    List<ProjectInnovationSdg> findActiveSdgsByFilters(
            @Param("innovationId") Long innovationId,
            @Param("phase") Long phase,
            @Param("sdgId") Long sdgId);
    
    @Query("SELECT pis FROM ProjectInnovationSdg pis " +
           "WHERE pis.innovationId IN :innovationIds " +
           "AND pis.isActive = true")
    List<ProjectInnovationSdg> findActiveSdgsByInnovationIds(@Param("innovationIds") List<Long> innovationIds);
}