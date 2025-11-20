package com.example.demo.modules.innovationcomments.adapters.infrastructure.persistence;

import com.example.demo.modules.innovationcomments.domain.model.InnovationCatalogComment;
import com.example.demo.modules.innovationcomments.domain.port.out.InnovationCommentRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Repository adapter implementation for Innovation Catalog Comments
 * Bridges the domain port with JPA repository implementation
 */
@Repository
@Transactional
public class InnovationCommentRepositoryAdapter implements InnovationCommentRepository {
    
    private final InnovationCatalogCommentJpaRepository jpaRepository;
    
    public InnovationCommentRepositoryAdapter(InnovationCatalogCommentJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }
    
    @Override
    public InnovationCatalogComment save(InnovationCatalogComment comment) {
        return jpaRepository.save(comment);
    }
    
    @Override
    public Optional<InnovationCatalogComment> findById(Long id) {
        return jpaRepository.findById(id);
    }
    
    @Override
    public List<InnovationCatalogComment> findActiveCommentsByInnovationId(Long innovationId) {
        return jpaRepository.findActiveCommentsByInnovationId(innovationId);
    }
    
    @Override
    public List<InnovationCatalogComment> findActiveCommentsByInnovationIdOrderByCreatedAsc(Long innovationId) {
        return jpaRepository.findActiveCommentsByInnovationIdOrderByCreatedAsc(innovationId);
    }
    
    @Override
    public Long countActiveCommentsByInnovationId(Long innovationId) {
        return jpaRepository.countActiveCommentsByInnovationId(innovationId);
    }
    
    @Override
    public List<InnovationCatalogComment> findActiveCommentsByUserEmail(String userEmail) {
        return jpaRepository.findActiveCommentsByUserEmail(userEmail);
    }
    
    @Override
    public List<InnovationCatalogComment> findRecentActiveCommentsByInnovationId(Long innovationId) {
        return jpaRepository.findRecentActiveCommentsByInnovationId(innovationId);
    }
    
    @Override
    public List<InnovationCatalogComment> findAllCommentsByInnovationId(Long innovationId) {
        return jpaRepository.findAllCommentsByInnovationId(innovationId);
    }
    
    @Override
    public List<InnovationCatalogComment> findAllCommentsOrderByActiveSinceDesc(Integer offset, Integer limit) {
        // Get all comments ordered by activeSince descending
        List<InnovationCatalogComment> allComments = jpaRepository.findAllByOrderByActiveSinceDesc();
        
        // If no limit specified, return all comments
        if (limit == null || limit <= 0) {
            return allComments;
        }
        
        // Apply offset and limit using Stream (same pattern as search-complete)
        int sanitizedOffset = (offset != null && offset > 0) ? offset : 0;
        
        return allComments.stream()
                .skip(sanitizedOffset)  // Skip exactly 'offset' records
                .limit(limit)           // Take maximum 'limit' records
                .toList();
    }
    
    @Override
    @Transactional
    public int softDeleteComment(Long commentId) {
        return jpaRepository.softDeleteComment(commentId);
    }
    
    @Override
    public boolean existsActiveComment(Long commentId) {
        Optional<InnovationCatalogComment> comment = jpaRepository.findById(commentId);
        return comment.isPresent() && comment.get().getIsActive();
    }
}
