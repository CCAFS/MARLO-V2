package com.example.demo.modules.projectinnovation.adapters.outbound.persistence;

import com.example.demo.modules.projectinnovation.domain.model.Institution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA Repository for Institution entity
 */
@Repository
public interface InstitutionJpaRepository extends JpaRepository<Institution, Long> {
    
    List<Institution> findByIsActive(Boolean isActive);
    
    @Query("SELECT i FROM Institution i " +
           "WHERE i.id IN :institutionIds " +
           "AND i.isActive = true")
    List<Institution> findActiveInstitutionsByIds(@Param("institutionIds") List<Long> institutionIds);
}