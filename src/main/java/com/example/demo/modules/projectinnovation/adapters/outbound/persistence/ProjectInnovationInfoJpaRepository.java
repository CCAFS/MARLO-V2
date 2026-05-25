package com.example.demo.modules.projectinnovation.adapters.outbound.persistence;

import com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA Repository for ProjectInnovationInfo entity
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
    
    // OPTIMIZED: Using EXISTS instead of JOIN for better performance
    @Query("SELECT pii FROM ProjectInnovationInfo pii " +
           "WHERE pii.idPhase = :phaseId " +
           "AND EXISTS (SELECT 1 FROM ProjectInnovation pi " +
                       "WHERE pi.id = pii.projectInnovationId AND pi.isActive = true)")
    List<ProjectInnovationInfo> findByPhaseAndActiveInnovation(@Param("phaseId") Long phaseId);
    
    // NATIVE QUERY: Ultra-optimized for production
    @Query(value = "SELECT pii.* FROM project_innovation_info pii " +
                   "WHERE pii.id_phase = :phaseId " +
                   "AND pii.project_innovation_id IN (" +
                       "SELECT pi.id FROM project_innovations pi WHERE pi.is_active = 1)", 
           nativeQuery = true)
    List<ProjectInnovationInfo> findByPhaseAndActiveInnovationNative(@Param("phaseId") Long phaseId);
    
    // OPTIMIZED: Direct query for average calculation without loading full entities
    @Query("SELECT AVG(CAST(pii.readinessScale AS double)) FROM ProjectInnovationInfo pii " +
           "WHERE pii.idPhase = :phaseId " +
           "AND pii.readinessScale IS NOT NULL " +
           "AND EXISTS (SELECT 1 FROM ProjectInnovation pi " +
                       "WHERE pi.id = pii.projectInnovationId AND pi.isActive = true)")
    Double findAverageScalingReadinessByPhaseOptimized(@Param("phaseId") Long phaseId);

    @Query("SELECT pii FROM ProjectInnovationInfo pii " +
           "WHERE pii.projectInnovationId IN :projectInnovationIds " +
           "AND pii.idPhase = :phaseId " +
           "AND EXISTS (SELECT 1 FROM ProjectInnovation pi " +
                       "WHERE pi.id = pii.projectInnovationId AND pi.isActive = true)")
    List<ProjectInnovationInfo> findActiveByProjectInnovationIdsAndPhase(
            @Param("projectInnovationIds") List<Long> projectInnovationIds,
            @Param("phaseId") Long phaseId);

    @Query("SELECT pii.title FROM ProjectInnovationInfo pii " +
           "JOIN ProjectInnovation pi ON pi.id = pii.projectInnovationId " +
           "WHERE pii.projectInnovationId = :projectInnovationId " +
           "AND pii.idPhase = :phaseId " +
           "AND pi.isActive = true")
    Optional<String> findActiveTitleByProjectInnovationIdAndPhase(
            @Param("projectInnovationId") Long projectInnovationId,
            @Param("phaseId") Long phaseId);

    Optional<ProjectInnovationInfo> findTop1ByProjectInnovationIdOrderByIdDesc(Long projectInnovationId);
    
    // Advanced filtering methods that return complete ProjectInnovationInfo (only active records)
    @Query(value = "SELECT DISTINCT pii.* FROM project_innovation_info pii " +
           "JOIN project_innovations p ON pii.project_innovation_id = p.id " +
           "WHERE p.is_active = true " +
           "AND (:phase IS NULL OR pii.id_phase = :phase) " +
           "AND (:readinessScale IS NULL OR pii.readiness_scale = :readinessScale) " +
           "AND (:innovationTypeId IS NULL OR pii.innovation_type_id = :innovationTypeId) " +
           "AND (:hasCountryFilter = false OR ( " +
               "SELECT COUNT(DISTINCT pic.id_country) " +
               "FROM project_innovation_countries pic " +
               "WHERE pic.project_innovation_id = pii.project_innovation_id " +
               "AND pic.id_phase = pii.id_phase " +
               "AND pic.id_country IN (:countryIds)) = :countryIdsCount) " +
           "AND (:hasActorFilter = false OR ( " +
               "SELECT COUNT(DISTINCT pia.actor_id) " +
               "FROM project_innovation_actors pia " +
               "WHERE pia.innovation_id = pii.project_innovation_id " +
               "AND pia.id_phase = pii.id_phase " +
               "AND pia.is_active = true " +
               "AND pia.actor_id IN (:actorIds)) = :actorIdsCount) " +
           "ORDER BY pii.project_innovation_id DESC", nativeQuery = true)
    List<ProjectInnovationInfo> findActiveInnovationsInfoWithFilters(
            @Param("phase") Long phase,
            @Param("readinessScale") Integer readinessScale,
            @Param("innovationTypeId") Long innovationTypeId,
            @Param("countryIds") List<Long> countryIds,
            @Param("countryIdsCount") int countryIdsCount,
            @Param("hasCountryFilter") boolean hasCountryFilter,
            @Param("actorIds") List<Long> actorIds,
            @Param("actorIdsCount") int actorIdsCount,
            @Param("hasActorFilter") boolean hasActorFilter);

    @Query(value = "SELECT DISTINCT pii.* FROM project_innovation_info pii " +
           "JOIN project_innovations p ON pii.project_innovation_id = p.id " +
           "WHERE p.is_active = true " +
           "AND (:phase IS NULL OR pii.id_phase = :phase) " +
           "AND (:readinessScale IS NULL OR pii.readiness_scale = :readinessScale) " +
           "AND (:innovationTypeId IS NULL OR pii.innovation_type_id = :innovationTypeId) " +
           "AND (:hasCountryFilter = false OR ( " +
               "SELECT COUNT(DISTINCT pic.id_country) " +
               "FROM project_innovation_countries pic " +
               "WHERE pic.project_innovation_id = pii.project_innovation_id " +
               "AND pic.id_phase = pii.id_phase " +
               "AND pic.id_country IN (:countryIds)) = :countryIdsCount) " +
           "AND (:hasActorFilter = false OR ( " +
               "SELECT COUNT(DISTINCT pia.actor_id) " +
               "FROM project_innovation_actors pia " +
               "WHERE pia.innovation_id = pii.project_innovation_id " +
               "AND pia.id_phase = pii.id_phase " +
               "AND pia.is_active = true " +
               "AND pia.actor_id IN (:actorIds)) = :actorIdsCount) " +
           "AND (:hasSearch = false OR ( " +
               "LOWER(COALESCE(pii.title, '')) LIKE :searchTerm " +
               "OR LOWER(COALESCE(pii.short_title, '')) LIKE :searchTerm " +
               "OR LOWER(COALESCE(pii.narrative, '')) LIKE :searchTerm " +
               "OR CAST(pii.project_innovation_id AS CHAR) LIKE :searchTerm " +
               "OR CAST(pii.year AS CHAR) LIKE :searchTerm " +
               "OR EXISTS (SELECT 1 FROM rep_ind_innovation_types rit " +
                   "WHERE rit.id = pii.innovation_type_id " +
                   "AND LOWER(COALESCE(rit.name, '')) LIKE :searchTerm) " +
               "OR EXISTS (SELECT 1 FROM project_innovation_countries pic_search " +
                   "JOIN loc_elements le_country ON le_country.id = pic_search.id_country " +
                   "WHERE pic_search.project_innovation_id = pii.project_innovation_id " +
                   "AND pic_search.id_phase = pii.id_phase " +
                   "AND LOWER(COALESCE(le_country.name, '')) LIKE :searchTerm) " +
               "OR EXISTS (SELECT 1 FROM project_innovation_regions pir_search " +
                   "JOIN loc_elements le_region ON le_region.id = pir_search.id_region " +
                   "WHERE pir_search.project_innovation_id = pii.project_innovation_id " +
                   "AND pir_search.id_phase = pii.id_phase " +
                   "AND LOWER(COALESCE(le_region.name, '')) LIKE :searchTerm) " +
               "OR EXISTS (SELECT 1 FROM project_innovation_actors pia_search " +
                   "JOIN actors a_search ON a_search.id = pia_search.actor_id " +
                   "WHERE pia_search.innovation_id = pii.project_innovation_id " +
                   "AND pia_search.id_phase = pii.id_phase " +
                   "AND pia_search.is_active = true " +
                   "AND LOWER(COALESCE(a_search.name, '')) LIKE :searchTerm))) " +
           "ORDER BY pii.project_innovation_id DESC", nativeQuery = true)
    List<ProjectInnovationInfo> findActiveInnovationsInfoWithSearchFilters(
            @Param("phase") Long phase,
            @Param("readinessScale") Integer readinessScale,
            @Param("innovationTypeId") Long innovationTypeId,
            @Param("countryIds") List<Long> countryIds,
            @Param("countryIdsCount") int countryIdsCount,
            @Param("hasCountryFilter") boolean hasCountryFilter,
            @Param("actorIds") List<Long> actorIds,
            @Param("actorIdsCount") int actorIdsCount,
            @Param("hasActorFilter") boolean hasActorFilter,
            @Param("searchTerm") String searchTerm,
            @Param("hasSearch") boolean hasSearch);
    
    // Find innovation info by SDG relationship
    @Query(value = "SELECT DISTINCT pii.* FROM project_innovation_info pii " +
           "JOIN project_innovations p ON pii.project_innovation_id = p.id " +
           "JOIN project_innovation_sdgs pis ON p.id = pis.innovation_id AND pii.id_phase = pis.id_phase " +
           "WHERE p.is_active = true " +
           "AND pis.is_active = true " +
           "AND (:innovationId IS NULL OR pis.innovation_id = :innovationId) " +
           "AND (:phase IS NULL OR pis.id_phase = :phase) " +
           "AND (:sdgId IS NULL OR pis.sdg_id = :sdgId) " +
           "AND (:hasCountryFilter = false OR ( " +
               "SELECT COUNT(DISTINCT pic.id_country) " +
               "FROM project_innovation_countries pic " +
               "WHERE pic.project_innovation_id = pii.project_innovation_id " +
               "AND pic.id_phase = pii.id_phase " +
               "AND pic.id_country IN (:countryIds)) = :countryIdsCount) " +
           "AND (:hasActorFilter = false OR ( " +
               "SELECT COUNT(DISTINCT pia.actor_id) " +
               "FROM project_innovation_actors pia " +
               "WHERE pia.innovation_id = pii.project_innovation_id " +
               "AND pia.id_phase = pii.id_phase " +
               "AND pia.is_active = true " +
               "AND pia.actor_id IN (:actorIds)) = :actorIdsCount) " +
           "ORDER BY pii.project_innovation_id DESC", nativeQuery = true)
    List<ProjectInnovationInfo> findActiveInnovationsInfoBySdgFilters(
            @Param("innovationId") Long innovationId,
            @Param("phase") Long phase,
            @Param("sdgId") Long sdgId,
            @Param("countryIds") List<Long> countryIds,
            @Param("countryIdsCount") int countryIdsCount,
            @Param("hasCountryFilter") boolean hasCountryFilter,
            @Param("actorIds") List<Long> actorIds,
            @Param("actorIdsCount") int actorIdsCount,
            @Param("hasActorFilter") boolean hasActorFilter);

    @Query(value = "SELECT DISTINCT pii.* FROM project_innovation_info pii " +
           "JOIN project_innovations p ON pii.project_innovation_id = p.id " +
           "JOIN project_innovation_sdgs pis ON p.id = pis.innovation_id AND pii.id_phase = pis.id_phase " +
           "WHERE p.is_active = true " +
           "AND pis.is_active = true " +
           "AND (:innovationId IS NULL OR pis.innovation_id = :innovationId) " +
           "AND (:phase IS NULL OR pis.id_phase = :phase) " +
           "AND (:sdgId IS NULL OR pis.sdg_id = :sdgId) " +
           "AND (:hasCountryFilter = false OR ( " +
               "SELECT COUNT(DISTINCT pic.id_country) " +
               "FROM project_innovation_countries pic " +
               "WHERE pic.project_innovation_id = pii.project_innovation_id " +
               "AND pic.id_phase = pii.id_phase " +
               "AND pic.id_country IN (:countryIds)) = :countryIdsCount) " +
           "AND (:hasActorFilter = false OR ( " +
               "SELECT COUNT(DISTINCT pia.actor_id) " +
               "FROM project_innovation_actors pia " +
               "WHERE pia.innovation_id = pii.project_innovation_id " +
               "AND pia.id_phase = pii.id_phase " +
               "AND pia.is_active = true " +
               "AND pia.actor_id IN (:actorIds)) = :actorIdsCount) " +
           "AND (:hasSearch = false OR ( " +
               "LOWER(COALESCE(pii.title, '')) LIKE :searchTerm " +
               "OR LOWER(COALESCE(pii.short_title, '')) LIKE :searchTerm " +
               "OR LOWER(COALESCE(pii.narrative, '')) LIKE :searchTerm " +
               "OR CAST(pii.project_innovation_id AS CHAR) LIKE :searchTerm " +
               "OR CAST(pii.year AS CHAR) LIKE :searchTerm " +
               "OR EXISTS (SELECT 1 FROM rep_ind_innovation_types rit " +
                   "WHERE rit.id = pii.innovation_type_id " +
                   "AND LOWER(COALESCE(rit.name, '')) LIKE :searchTerm) " +
               "OR EXISTS (SELECT 1 FROM project_innovation_countries pic_search " +
                   "JOIN loc_elements le_country ON le_country.id = pic_search.id_country " +
                   "WHERE pic_search.project_innovation_id = pii.project_innovation_id " +
                   "AND pic_search.id_phase = pii.id_phase " +
                   "AND LOWER(COALESCE(le_country.name, '')) LIKE :searchTerm) " +
               "OR EXISTS (SELECT 1 FROM project_innovation_regions pir_search " +
                   "JOIN loc_elements le_region ON le_region.id = pir_search.id_region " +
                   "WHERE pir_search.project_innovation_id = pii.project_innovation_id " +
                   "AND pir_search.id_phase = pii.id_phase " +
                   "AND LOWER(COALESCE(le_region.name, '')) LIKE :searchTerm) " +
               "OR EXISTS (SELECT 1 FROM project_innovation_actors pia_search " +
                   "JOIN actors a_search ON a_search.id = pia_search.actor_id " +
                   "WHERE pia_search.innovation_id = pii.project_innovation_id " +
                   "AND pia_search.id_phase = pii.id_phase " +
                   "AND pia_search.is_active = true " +
                   "AND LOWER(COALESCE(a_search.name, '')) LIKE :searchTerm))) " +
           "ORDER BY pii.project_innovation_id DESC", nativeQuery = true)
    List<ProjectInnovationInfo> findActiveInnovationsInfoBySdgSearchFilters(
            @Param("innovationId") Long innovationId,
            @Param("phase") Long phase,
            @Param("sdgId") Long sdgId,
            @Param("countryIds") List<Long> countryIds,
            @Param("countryIdsCount") int countryIdsCount,
            @Param("hasCountryFilter") boolean hasCountryFilter,
            @Param("actorIds") List<Long> actorIds,
            @Param("actorIdsCount") int actorIdsCount,
            @Param("hasActorFilter") boolean hasActorFilter,
            @Param("searchTerm") String searchTerm,
            @Param("hasSearch") boolean hasSearch);
    
    // Find all active innovations info
    @Query(value = "SELECT DISTINCT pii.* FROM project_innovation_info pii " +
           "JOIN project_innovations p ON pii.project_innovation_id = p.id " +
           "WHERE p.is_active = true " +
           "ORDER BY pii.project_innovation_id DESC", nativeQuery = true)
    List<ProjectInnovationInfo> findAllActiveInnovationsInfo();
}
