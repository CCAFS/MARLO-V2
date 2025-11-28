package com.example.demo.modules.projectinnovation.adapters.outbound.persistence;

import com.example.demo.modules.projectinnovation.domain.model.Actor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JPA Repository for Actor entity.
 * Provides data access methods for actors.
 */
@Repository
public interface ActorJpaRepository extends JpaRepository<Actor, Long> {

    /**
     * Find an active actor by ID.
     *
     * @param id Actor ID
     * @return Optional Actor if found and active
     */
    @Query("SELECT a FROM Actor a WHERE a.id = :id AND a.isActive = true")
    Optional<Actor> findActiveById(@Param("id") Long id);

    /**
     * Find all active actors.
     *
     * @return List of active actors
     */
    @Query("SELECT a FROM Actor a WHERE a.isActive = true ORDER BY a.id")
    List<Actor> findAllActive();

    /**
     * Find actors by name pattern (case-insensitive).
     *
     * @param namePattern Name pattern to search
     * @return List of matching active actors
     */
    @Query("SELECT a FROM Actor a WHERE a.isActive = true AND LOWER(a.name) LIKE LOWER(CONCAT('%', :namePattern, '%')) ORDER BY a.name")
    List<Actor> findActiveByNameContaining(@Param("namePattern") String namePattern);

    /**
     * Find actors by multiple IDs that are active.
     *
     * @param ids List of actor IDs
     * @return List of active actors matching the IDs
     */
    @Query("SELECT a FROM Actor a WHERE a.id IN :ids AND a.isActive = true ORDER BY a.name")
    List<Actor> findActiveByIds(@Param("ids") List<Long> ids);
}