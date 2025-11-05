package com.example.demo.modules.innovationcomments.adapters.infrastructure.persistence;

import com.example.demo.modules.innovationcomments.domain.model.InnovationCatalogComment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * JPA Repository for Innovation Catalog Comments
 * Handles data access operations for innovation comments
 */
@Repository
public interface InnovationCatalogCommentJpaRepository extends JpaRepository<InnovationCatalogComment, Long> {
    
    /**
     * Finds all active comments for a specific innovation
     * @param innovationId The innovation ID to search for
     * @return List of active comments ordered by creation date descending
     */
    @Query("SELECT c FROM InnovationCatalogComment c " +
           "WHERE c.innovationId = :innovationId " +
           "AND c.isActive = true " +
           "ORDER BY c.activeSince DESC")
    List<InnovationCatalogComment> findActiveCommentsByInnovationId(@Param("innovationId") Long innovationId);
    
    /**
     * Finds all active comments for a specific innovation ordered by creation date ascending
     * @param innovationId The innovation ID to search for
     * @return List of active comments ordered by creation date ascending
     */
    @Query("SELECT c FROM InnovationCatalogComment c " +
           "WHERE c.innovationId = :innovationId " +
           "AND c.isActive = true " +
           "ORDER BY c.activeSince ASC")
    List<InnovationCatalogComment> findActiveCommentsByInnovationIdOrderByCreatedAsc(@Param("innovationId") Long innovationId);
    
    /**
     * Counts active comments for a specific innovation
     * @param innovationId The innovation ID to count comments for
     * @return Number of active comments
     */
    @Query("SELECT COUNT(c) FROM InnovationCatalogComment c " +
           "WHERE c.innovationId = :innovationId " +
           "AND c.isActive = true")
    Long countActiveCommentsByInnovationId(@Param("innovationId") Long innovationId);
    
    /**
     * Finds all active comments by user email
     * @param userEmail The user email to search for
     * @return List of active comments by the user
     */
    @Query("SELECT c FROM InnovationCatalogComment c " +
           "WHERE c.userEmail = :userEmail " +
           "AND c.isActive = true " +
           "ORDER BY c.activeSince DESC")
    List<InnovationCatalogComment> findActiveCommentsByUserEmail(@Param("userEmail") String userEmail);
    
    /**
     * Finds recent active comments (last N comments) for a specific innovation
     * Uses Spring Data JPA Pageable approach instead of native LIMIT
     * @param innovationId The innovation ID to search for
     * @return List of recent active comments
     */
    @Query("SELECT c FROM InnovationCatalogComment c " +
           "WHERE c.innovationId = :innovationId " +
           "AND c.isActive = true " +
           "ORDER BY c.activeSince DESC")
    List<InnovationCatalogComment> findRecentActiveCommentsByInnovationId(@Param("innovationId") Long innovationId);
    
    /**
     * Finds all comments (active and inactive) for a specific innovation
     * @param innovationId The innovation ID to search for
     * @return List of all comments ordered by creation date descending
     */
    @Query("SELECT c FROM InnovationCatalogComment c " +
           "WHERE c.innovationId = :innovationId " +
           "ORDER BY c.activeSince DESC")
    List<InnovationCatalogComment> findAllCommentsByInnovationId(@Param("innovationId") Long innovationId);
    
    /**
     * Finds all comments ordered by active since descending.
     */
    List<InnovationCatalogComment> findAllByOrderByActiveSinceDesc();
    
    /**
     * Finds comments ordered by active since descending limited by pageable.
     */
    List<InnovationCatalogComment> findAllByOrderByActiveSinceDesc(Pageable pageable);
    
    /**
     * Soft delete - marks comment as inactive instead of deleting it
     * Note: For update queries, we need @Modifying annotation
     * @param commentId The comment ID to deactivate
     * @return Number of records updated
     */
    @Modifying
    @Query("UPDATE InnovationCatalogComment c " +
           "SET c.isActive = false, c.activeSince = CURRENT_TIMESTAMP " +
           "WHERE c.id = :commentId")
    int softDeleteComment(@Param("commentId") Long commentId);
}
