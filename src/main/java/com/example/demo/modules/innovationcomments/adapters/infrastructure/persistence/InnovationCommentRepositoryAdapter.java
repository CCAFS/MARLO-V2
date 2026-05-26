package com.example.demo.modules.innovationcomments.adapters.infrastructure.persistence;

import com.example.demo.modules.innovationcomments.domain.model.InnovationCatalogComment;
import com.example.demo.modules.innovationcomments.domain.port.out.InnovationCommentRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
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
        // If no limit specified, return all comments
        if (limit == null || limit <= 0) {
            return jpaRepository.findAllByOrderByActiveSinceDesc();
        }

        int sanitizedOffset = (offset != null && offset > 0) ? offset : 0;
        return jpaRepository.findAllByOrderByActiveSinceDesc(new OffsetLimitPageable(sanitizedOffset, limit));
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

    private record OffsetLimitPageable(int offset, int limit) implements Pageable, Serializable {

        private OffsetLimitPageable {
            if (offset < 0) {
                throw new IllegalArgumentException("Offset must not be negative");
            }
            if (limit <= 0) {
                throw new IllegalArgumentException("Limit must be greater than zero");
            }
        }

        @Override
        public int getPageNumber() {
            return offset / limit;
        }

        @Override
        public int getPageSize() {
            return limit;
        }

        @Override
        public long getOffset() {
            return offset;
        }

        @Override
        public Sort getSort() {
            return Sort.unsorted();
        }

        @Override
        public Pageable next() {
            return new OffsetLimitPageable(offset + limit, limit);
        }

        @Override
        public Pageable previousOrFirst() {
            return hasPrevious() ? new OffsetLimitPageable(Math.max(offset - limit, 0), limit) : first();
        }

        @Override
        public Pageable first() {
            return new OffsetLimitPageable(0, limit);
        }

        @Override
        public Pageable withPage(int pageNumber) {
            if (pageNumber < 0) {
                throw new IllegalArgumentException("Page index must not be negative");
            }
            return new OffsetLimitPageable(pageNumber * limit, limit);
        }

        @Override
        public boolean hasPrevious() {
            return offset > 0;
        }
    }
}
