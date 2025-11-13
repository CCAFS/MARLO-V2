package com.example.demo.modules.projectinnovation.adapters.outbound.persistence;

import com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationPartnership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA Repository for ProjectInnovationPartnership entity
 */
@Repository
public interface ProjectInnovationPartnershipJpaRepository extends JpaRepository<ProjectInnovationPartnership, Long> {
    
    List<ProjectInnovationPartnership> findByProjectInnovationIdAndIdPhaseAndIsActive(Long projectInnovationId, Long idPhase, Boolean isActive);
    
    @Query("SELECT pip FROM ProjectInnovationPartnership pip " +
           "WHERE pip.projectInnovationId IN :innovationIds " +
           "AND pip.isActive = true")
    List<ProjectInnovationPartnership> findActivePartnershipsByInnovationIds(@Param("innovationIds") List<Long> innovationIds);
}