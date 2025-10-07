package com.example.demo.modules.projectinnovation.adapters.outbound.persistence;

import com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationPartnershipPerson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA Repository for ProjectInnovationPartnershipPerson entity
 */
@Repository
public interface ProjectInnovationPartnershipPersonJpaRepository extends JpaRepository<ProjectInnovationPartnershipPerson, Long> {
    
    List<ProjectInnovationPartnershipPerson> findByPartnershipIdAndIsActive(Long partnershipId, Boolean isActive);
    
    @Query("SELECT pipp FROM ProjectInnovationPartnershipPerson pipp " +
           "WHERE pipp.partnershipId IN :partnershipIds " +
           "AND pipp.isActive = true")
    List<ProjectInnovationPartnershipPerson> findActivePersonsByPartnershipIds(@Param("partnershipIds") List<Long> partnershipIds);
}