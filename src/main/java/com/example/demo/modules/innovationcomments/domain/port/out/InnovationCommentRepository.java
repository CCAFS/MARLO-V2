package com.example.demo.modules.innovationcomments.domain.port.out;

import com.example.demo.modules.innovationcomments.domain.model.InnovationCatalogComment;

import java.util.List;
import java.util.Optional;

/**
 * Output port for Innovation Catalog Comment operations
 * Defines the contract for data persistence operations
 */
public interface InnovationCommentRepository {
    
    /**
     * Save a new comment or update an existing one
     * @param comment The comment to save
     * @return The saved comment with generated ID
     */
    InnovationCatalogComment save(InnovationCatalogComment comment);
    
    /**
     * Find a comment by its ID
     * @param id The comment ID
     * @return Optional containing the comment if found
     */
    Optional<InnovationCatalogComment> findById(Long id);
    
    /**
     * Find all active comments for a specific innovation
     * @param innovationId The innovation ID
     * @return List of active comments ordered by creation date descending
     */
    List<InnovationCatalogComment> findActiveCommentsByInnovationId(Long innovationId);
    
    /**
     * Find all active comments for a specific innovation ordered by creation date ascending
     * @param innovationId The innovation ID
     * @return List of active comments ordered by creation date ascending
     */
    List<InnovationCatalogComment> findActiveCommentsByInnovationIdOrderByCreatedAsc(Long innovationId);
    
    /**
     * Count active comments for a specific innovation
     * @param innovationId The innovation ID
     * @return Number of active comments
     */
    Long countActiveCommentsByInnovationId(Long innovationId);
    
    /**
     * Find all active comments by user email
     * @param userEmail The user email
     * @return List of active comments by the user
     */
    List<InnovationCatalogComment> findActiveCommentsByUserEmail(String userEmail);
    
    /**
     * Find recent active comments for a specific innovation
     * @param innovationId The innovation ID
     * @return List of recent active comments
     */
    List<InnovationCatalogComment> findRecentActiveCommentsByInnovationId(Long innovationId);
    
    /**
     * Find all comments (active and inactive) for a specific innovation
     * @param innovationId The innovation ID
     * @return List of all comments
     */
    List<InnovationCatalogComment> findAllCommentsByInnovationId(Long innovationId);
    
    /**
     * Find all comments ordered by activeSince descending, optionally limited.
     * @param limit Maximum number of comments to return. If null, returns all.
     * @return List of comments ordered by newest first.
     */
    List<InnovationCatalogComment> findAllCommentsOrderByActiveSinceDesc(Integer limit);
    
    /**
     * Soft delete a comment by marking it as inactive
     * @param commentId The comment ID to deactivate
     * @return Number of records updated
     */
    int softDeleteComment(Long commentId);
    
    /**
     * Check if a comment exists and is active
     * @param commentId The comment ID
     * @return true if comment exists and is active
     */
    boolean existsActiveComment(Long commentId);
}
