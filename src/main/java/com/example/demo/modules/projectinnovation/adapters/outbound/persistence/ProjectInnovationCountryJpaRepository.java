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
}