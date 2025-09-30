package com.example.demo.modules.projectinnovation.adapters.outbound.persistence;

import com.example.demo.modules.projectinnovation.domain.model.ProjectInnovation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositorio JPA para la entidad ProjectInnovation
 */
@Repository
public interface ProjectInnovationJpaRepository extends JpaRepository<ProjectInnovation, Long> {
    
    List<ProjectInnovation> findByIsActive(Boolean isActive);
    
    // Consulta por defecto que solo devuelve registros activos
    @Query("SELECT p FROM ProjectInnovation p WHERE p.isActive = true")
    List<ProjectInnovation> findAllActive();
    
    List<ProjectInnovation> findByProjectId(Long projectId);
    
    // Buscar por project ID solo registros activos
    List<ProjectInnovation> findByProjectIdAndIsActive(Long projectId, Boolean isActive);
    
    @Query("SELECT COUNT(p) FROM ProjectInnovation p WHERE p.projectId = :projectId AND p.isActive = true")
    Long countActiveByProjectId(@Param("projectId") Long projectId);
    
    // Métodos personalizados para búsquedas específicas
    Optional<ProjectInnovation> findByIdAndIsActive(Long id, Boolean isActive);
    
    // Búsqueda con información adicional (cuando se reactiven las relaciones)
    // @Query("SELECT p FROM ProjectInnovation p LEFT JOIN FETCH p.innovationInfo WHERE p.id = :id AND p.isActive = true")
    // Optional<ProjectInnovation> findByIdWithInfo(@Param("id") Long id);
    
    // @Query("SELECT p FROM ProjectInnovation p LEFT JOIN FETCH p.innovationInfo WHERE p.isActive = true")
    // List<ProjectInnovation> findAllActiveWithInfo();
    
    // @Query("SELECT p FROM ProjectInnovation p " +
    //        "LEFT JOIN FETCH p.innovationInfo " +
    //        "WHERE p.projectId = :projectId AND p.isActive = true")
    // List<ProjectInnovation> findActiveByProjectIdWithInfo(@Param("projectId") Long projectId);
}