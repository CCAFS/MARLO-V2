package com.example.demo.modules.innovationcomments.application.service;

import com.example.demo.modules.innovationcomments.domain.model.InnovationCatalogComment;
import com.example.demo.modules.innovationcomments.domain.port.in.InnovationCommentUseCase;
import com.example.demo.modules.innovationcomments.domain.port.out.InnovationCommentRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Application service for Innovation Comment operations
 * Implements the use case interface and coordinates business logic
 */
@Service
@Transactional
public class InnovationCommentService implements InnovationCommentUseCase {
    
    private static final Logger logger = LoggerFactory.getLogger(InnovationCommentService.class);
    private final InnovationCommentRepository commentRepository;
    private final CommentModerationService commentModerationService;

    public InnovationCommentService(InnovationCommentRepository commentRepository,
                                    CommentModerationService commentModerationService) {
        this.commentRepository = commentRepository;
        this.commentModerationService = commentModerationService;
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<InnovationCatalogComment> getActiveCommentsByInnovationId(Long innovationId) {
        if (innovationId == null) {
            throw new IllegalArgumentException("Innovation ID cannot be null");
        }
        
        try {
            return commentRepository.findActiveCommentsByInnovationId(innovationId);
        } catch (DataAccessException e) {
            logger.error("Database error while fetching comments for innovation {}: {}", innovationId, e.getMessage());
            if (isTableNotExistsError(e)) {
                logger.warn("Table 'innovation_catalog_comments' does not exist in database");
                throw new RuntimeException("Comments table not found. Please ensure the database schema is properly initialized.", e);
            }
            throw new RuntimeException("Database error occurred while fetching comments", e);
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<InnovationCatalogComment> getActiveCommentsByInnovationIdOrderByCreatedAsc(Long innovationId) {
        if (innovationId == null) {
            throw new IllegalArgumentException("Innovation ID cannot be null");
        }
        
        try {
            return commentRepository.findActiveCommentsByInnovationIdOrderByCreatedAsc(innovationId);
        } catch (DataAccessException e) {
            logger.error("Database error while fetching comments for innovation {}: {}", innovationId, e.getMessage());
            if (isTableNotExistsError(e)) {
                logger.warn("Table 'innovation_catalog_comments' does not exist in database");
                throw new RuntimeException("Comments table not found. Please ensure the database schema is properly initialized.", e);
            }
            throw new RuntimeException("Database error occurred while fetching comments", e);
        }
    }
    
    @Override
    public InnovationCatalogComment createComment(Long innovationId, String userName, String userLastname, 
                                                String userEmail, String commentText) {
        // Validate input parameters
        validateCommentParameters(innovationId, userName, userLastname, userEmail);
        commentModerationService.validateComment(innovationId, userEmail, commentText);

        try {
            // Create new comment
            InnovationCatalogComment comment = new InnovationCatalogComment(
                innovationId, userName, userLastname, userEmail, commentText
            );
            
            return commentRepository.save(comment);
        } catch (DataAccessException e) {
            logger.error("Database error while creating comment for innovation {}: {}", innovationId, e.getMessage());
            if (isTableNotExistsError(e)) {
                logger.warn("Table 'innovation_catalog_comments' does not exist in database");
                throw new RuntimeException("Comments table not found. Please ensure the database schema is properly initialized.", e);
            }
            throw new RuntimeException("Database error occurred while creating comment", e);
        }
    }
    
    @Override
    public InnovationCatalogComment createCommentWithAudit(Long innovationId, String userName, String userLastname, 
                                                         String userEmail, String commentText, String createdBy) {
        // Validate input parameters
        validateCommentParameters(innovationId, userName, userLastname, userEmail);
        commentModerationService.validateComment(innovationId, userEmail, commentText);
        
        try {
            // Create new comment with audit information
            InnovationCatalogComment comment = new InnovationCatalogComment(
                innovationId, userName, userLastname, userEmail, commentText
            );
            comment.setModificationJustification(createdBy);
            
            return commentRepository.save(comment);
        } catch (DataAccessException e) {
            logger.error("Database error while creating comment with audit for innovation {}: {}", innovationId, e.getMessage());
            if (isTableNotExistsError(e)) {
                logger.warn("Table 'innovation_catalog_comments' does not exist in database");
                throw new RuntimeException("Comments table not found. Please ensure the database schema is properly initialized.", e);
            }
            throw new RuntimeException("Database error occurred while creating comment", e);
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public Long getActiveCommentsCount(Long innovationId) {
        if (innovationId == null) {
            throw new IllegalArgumentException("Innovation ID cannot be null");
        }
        
        try {
            return commentRepository.countActiveCommentsByInnovationId(innovationId);
        } catch (DataAccessException e) {
            logger.error("Database error while counting comments for innovation {}: {}", innovationId, e.getMessage());
            if (isTableNotExistsError(e)) {
                logger.warn("Table 'innovation_catalog_comments' does not exist in database");
                throw new RuntimeException("Comments table not found. Please ensure the database schema is properly initialized.", e);
            }
            throw new RuntimeException("Database error occurred while counting comments", e);
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<InnovationCatalogComment> getActiveCommentsByUserEmail(String userEmail) {
        if (userEmail == null || userEmail.trim().isEmpty()) {
            throw new IllegalArgumentException("User email cannot be null or empty");
        }
        
        try {
            return commentRepository.findActiveCommentsByUserEmail(userEmail);
        } catch (DataAccessException e) {
            logger.error("Database error while fetching comments for user {}: {}", userEmail, e.getMessage());
            if (isTableNotExistsError(e)) {
                logger.warn("Table 'innovation_catalog_comments' does not exist in database");
                throw new RuntimeException("Comments table not found. Please ensure the database schema is properly initialized.", e);
            }
            throw new RuntimeException("Database error occurred while fetching user comments", e);
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<InnovationCatalogComment> getAllComments(Integer offset, Integer limit) {
        Integer sanitizedOffset = (offset != null && offset > 0) ? offset : 0;
        Integer sanitizedLimit = null;
        
        if (limit != null) {
            if (limit <= 0) {
                throw new IllegalArgumentException("Limit must be greater than zero");
            }
            sanitizedLimit = limit;
        }
        
        try {
            return commentRepository.findAllCommentsOrderByActiveSinceDesc(sanitizedOffset, sanitizedLimit);
        } catch (DataAccessException e) {
            logger.error("Database error while fetching comments: {}", e.getMessage());
            if (isTableNotExistsError(e)) {
                logger.warn("Table 'innovation_catalog_comments' does not exist in database");
                throw new RuntimeException("Comments table not found. Please ensure the database schema is properly initialized.", e);
            }
            throw new RuntimeException("Database error occurred while fetching comments", e);
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public List<InnovationCatalogComment> getRecentActiveCommentsByInnovationId(Long innovationId) {
        if (innovationId == null) {
            throw new IllegalArgumentException("Innovation ID cannot be null");
        }
        
        try {
            return commentRepository.findRecentActiveCommentsByInnovationId(innovationId);
        } catch (DataAccessException e) {
            logger.error("Database error while fetching recent comments for innovation {}: {}", innovationId, e.getMessage());
            if (isTableNotExistsError(e)) {
                logger.warn("Table 'innovation_catalog_comments' does not exist in database");
                throw new RuntimeException("Comments table not found. Please ensure the database schema is properly initialized.", e);
            }
            throw new RuntimeException("Database error occurred while fetching recent comments", e);
        }
    }
    
    @Override
    public boolean deactivateComment(Long commentId) {
        if (commentId == null) {
            throw new IllegalArgumentException("Comment ID cannot be null");
        }
        
        try {
            // Check if comment exists and is active
            if (!commentRepository.existsActiveComment(commentId)) {
                return false;
            }
            
            int updatedRows = commentRepository.softDeleteComment(commentId);
            return updatedRows > 0;
        } catch (DataAccessException e) {
            logger.error("Database error while deactivating comment {}: {}", commentId, e.getMessage());
            if (isTableNotExistsError(e)) {
                logger.warn("Table 'innovation_catalog_comments' does not exist in database");
                throw new RuntimeException("Comments table not found. Please ensure the database schema is properly initialized.", e);
            }
            throw new RuntimeException("Database error occurred while deactivating comment", e);
        }
    }
    
    @Override
    @Transactional(readOnly = true)
    public boolean isCommentActive(Long commentId) {
        if (commentId == null) {
            return false;
        }
        
        try {
            return commentRepository.existsActiveComment(commentId);
        } catch (DataAccessException e) {
            logger.error("Database error while checking comment {} status: {}", commentId, e.getMessage());
            if (isTableNotExistsError(e)) {
                logger.warn("Table 'innovation_catalog_comments' does not exist in database");
                throw new RuntimeException("Comments table not found. Please ensure the database schema is properly initialized.", e);
            }
            throw new RuntimeException("Database error occurred while checking comment status", e);
        }
    }
    
    /**
     * Validates common comment parameters
     * @param innovationId The innovation ID
     * @param userName The user name
     * @param userLastname The user lastname
     * @param userEmail The user email
     */
    private void validateCommentParameters(Long innovationId, String userName, String userLastname, String userEmail) {
        if (innovationId == null) {
            throw new IllegalArgumentException("Innovation ID cannot be null");
        }
        if (userName == null || userName.trim().isEmpty()) {
            throw new IllegalArgumentException("User name cannot be null or empty");
        }
        if (userEmail == null || userEmail.trim().isEmpty()) {
            throw new IllegalArgumentException("User email cannot be null or empty");
        }
        if (!isValidEmail(userEmail)) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }
    
    /**
     * Basic email validation
     * @param email The email to validate
     * @return true if email format is valid
     */
    private boolean isValidEmail(String email) {
        return email != null && 
               email.contains("@") && 
               email.contains(".") && 
               email.length() > 5;
    }
    
    /**
     * Checks if the exception is related to a missing table
     * @param e The DataAccessException to check
     * @return true if the error indicates a missing table
     */
    private boolean isTableNotExistsError(DataAccessException e) {
        String message = e.getMessage();
        if (message == null) {
            return false;
        }
        
        String lowerCaseMessage = message.toLowerCase();
        return lowerCaseMessage.contains("table") && 
               (lowerCaseMessage.contains("doesn't exist") || 
                lowerCaseMessage.contains("not found") ||
                lowerCaseMessage.contains("unknown table") ||
                lowerCaseMessage.contains("innovation_catalog_comments"));
    }
}
