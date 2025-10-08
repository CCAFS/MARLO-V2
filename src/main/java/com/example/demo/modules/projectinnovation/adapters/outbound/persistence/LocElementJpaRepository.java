package com.example.demo.modules.projectinnovation.adapters.outbound.persistence;

import com.example.demo.modules.projectinnovation.domain.model.LocElement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA Repository for LocElement entity
 * Provides data access methods for geographical locations
 */
@Repository
public interface LocElementJpaRepository extends JpaRepository<LocElement, Long> {
    
    /**
     * Find an active location element by ID
     */
    @Query("SELECT le FROM LocElement le WHERE le.id = :id AND le.isActive = true")
    Optional<LocElement> findActiveById(@Param("id") Long id);
    
    /**
     * Find location elements by IDs (batch query for performance)
     */
    @Query("SELECT le FROM LocElement le WHERE le.id IN :ids AND le.isActive = true")
    List<LocElement> findActiveByIds(@Param("ids") List<Long> ids);
    
    /**
     * Find countries (element_type_id = 2) by IDs
     */
    @Query("SELECT le FROM LocElement le WHERE le.id IN :ids AND le.elementTypeId = 2 AND le.isActive = true")
    List<LocElement> findCountriesByIds(@Param("ids") List<Long> ids);
    
    /**
     * Find regions by IDs (different element types for regions)
     */
    @Query("SELECT le FROM LocElement le WHERE le.id IN :ids AND le.elementTypeId != 2 AND le.isActive = true")
    List<LocElement> findRegionsByIds(@Param("ids") List<Long> ids);
}