package com.example.demo.modules.innovationcomments.domain.port.in;

import com.example.demo.modules.innovationcomments.domain.model.InnovationCatalogComment;

import java.util.List;

/**
 * Input port for Innovation Comment use cases
 * Defines the contract for business operations related to innovation comments
 */
public interface InnovationCommentUseCase {
    
    /**
     * Get all active comments for a specific innovation
     * @param innovationId The innovation ID to get comments for
     * @return List of active comments ordered by creation date descending
     */
    List<InnovationCatalogComment> getActiveCommentsByInnovationId(Long innovationId);
    
    /**
     * Get all active comments for a specific innovation ordered by creation date ascending
     * @param innovationId The innovation ID to get comments for
     * @return List of active comments ordered by creation date ascending
     */
    List<InnovationCatalogComment> getActiveCommentsByInnovationIdOrderByCreatedAsc(Long innovationId);
    
    /**
     * Create a new comment for an innovation
     * @param innovationId The innovation ID
     * @param userName The user's first name
     * @param userLastname The user's last name
     * @param userEmail The user's email
     * @param commentText The comment text
     * @return The created comment
     */
    InnovationCatalogComment createComment(Long innovationId, String userName, String userLastname, 
                                         String userEmail, String commentText);
    
    /**
     * Create a new comment with audit information
     * @param innovationId The innovation ID
     * @param userName The user's first name
     * @param userLastname The user's last name
     * @param userEmail The user's email
     * @param commentText The comment text
     * @param createdBy Who created the comment (for audit)
     * @return The created comment
     */
    InnovationCatalogComment createCommentWithAudit(Long innovationId, String userName, String userLastname, 
                                                   String userEmail, String commentText, String createdBy);
    
    /**
     * Get the count of active comments for a specific innovation
     * @param innovationId The innovation ID
     * @return Number of active comments
     */
    Long getActiveCommentsCount(Long innovationId);
    
    /**
     * Get all active comments by a specific user
     * @param userEmail The user's email
     * @return List of active comments by the user
     */
    List<InnovationCatalogComment> getActiveCommentsByUserEmail(String userEmail);
    
    /**
     * Get recent active comments for a specific innovation
     * @param innovationId The innovation ID
     * @return List of recent active comments
     */
    List<InnovationCatalogComment> getRecentActiveCommentsByInnovationId(Long innovationId);
    
    /**
     * Get all comments ordered by newest first, with pagination support.
     * @param offset Number of records to skip. Null or 0 means start from beginning.
     * @param limit Maximum number of comments to return. Null means all.
     * @return List of comments ordered by activeSince descending.
     */
    List<InnovationCatalogComment> getAllComments(Integer offset, Integer limit);
    
    /**
     * Soft delete a comment (mark as inactive)
     * @param commentId The comment ID to deactivate
     * @return true if the comment was successfully deactivated
     */
    boolean deactivateComment(Long commentId);
    
    /**
     * Check if a comment exists and is active
     * @param commentId The comment ID
     * @return true if comment exists and is active
     */
    boolean isCommentActive(Long commentId);
}
