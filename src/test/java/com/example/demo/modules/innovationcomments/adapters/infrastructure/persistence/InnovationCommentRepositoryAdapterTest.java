package com.example.demo.modules.innovationcomments.adapters.infrastructure.persistence;

import com.example.demo.modules.innovationcomments.domain.model.InnovationCatalogComment;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InnovationCommentRepositoryAdapterTest {

    private InnovationCatalogCommentJpaRepository jpaRepository;
    private InnovationCommentRepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        jpaRepository = mock(InnovationCatalogCommentJpaRepository.class);
        adapter = new InnovationCommentRepositoryAdapter(jpaRepository);
    }

    @Test
    void save_ShouldDelegateToJpaRepository() {
        // Arrange
        InnovationCatalogComment comment = new InnovationCatalogComment();
        comment.setComment("Test comment");
        when(jpaRepository.save(comment)).thenReturn(comment);

        // Act
        InnovationCatalogComment result = adapter.save(comment);

        // Assert
        assertNotNull(result);
        assertEquals("Test comment", result.getComment());
        verify(jpaRepository).save(comment);
    }

    @Test
    void findById_WhenExists_ShouldReturnOptional() {
        // Arrange
        InnovationCatalogComment comment = new InnovationCatalogComment();
        comment.setId(1L);
        when(jpaRepository.findById(1L)).thenReturn(Optional.of(comment));

        // Act
        Optional<InnovationCatalogComment> result = adapter.findById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(jpaRepository).findById(1L);
    }

    @Test
    void findById_WhenNotExists_ShouldReturnEmpty() {
        // Arrange
        when(jpaRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        Optional<InnovationCatalogComment> result = adapter.findById(999L);

        // Assert
        assertFalse(result.isPresent());
        verify(jpaRepository).findById(999L);
    }

    @Test
    void findActiveCommentsByInnovationId_ShouldReturnList() {
        // Arrange
        InnovationCatalogComment comment = new InnovationCatalogComment();
        when(jpaRepository.findActiveCommentsByInnovationId(1L))
            .thenReturn(Arrays.asList(comment));

        // Act
        List<InnovationCatalogComment> result = adapter.findActiveCommentsByInnovationId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(jpaRepository).findActiveCommentsByInnovationId(1L);
    }

    @Test
    void findActiveCommentsByInnovationIdOrderByCreatedAsc_ShouldReturnOrderedList() {
        // Arrange
        InnovationCatalogComment comment = new InnovationCatalogComment();
        when(jpaRepository.findActiveCommentsByInnovationIdOrderByCreatedAsc(1L))
            .thenReturn(Arrays.asList(comment));

        // Act
        List<InnovationCatalogComment> result = adapter.findActiveCommentsByInnovationIdOrderByCreatedAsc(1L);

        // Assert
        assertNotNull(result);
        verify(jpaRepository).findActiveCommentsByInnovationIdOrderByCreatedAsc(1L);
    }

    @Test
    void countActiveCommentsByInnovationId_ShouldReturnCount() {
        // Arrange
        when(jpaRepository.countActiveCommentsByInnovationId(1L)).thenReturn(5L);

        // Act
        Long result = adapter.countActiveCommentsByInnovationId(1L);

        // Assert
        assertEquals(5L, result);
        verify(jpaRepository).countActiveCommentsByInnovationId(1L);
    }

    @Test
    void findActiveCommentsByUserEmail_ShouldReturnList() {
        // Arrange
        InnovationCatalogComment comment = new InnovationCatalogComment();
        when(jpaRepository.findActiveCommentsByUserEmail("test@example.com"))
            .thenReturn(Arrays.asList(comment));

        // Act
        List<InnovationCatalogComment> result = adapter.findActiveCommentsByUserEmail("test@example.com");

        // Assert
        assertNotNull(result);
        verify(jpaRepository).findActiveCommentsByUserEmail("test@example.com");
    }

    @Test
    void findRecentActiveCommentsByInnovationId_ShouldReturnList() {
        // Arrange
        InnovationCatalogComment comment = new InnovationCatalogComment();
        when(jpaRepository.findRecentActiveCommentsByInnovationId(1L))
            .thenReturn(Arrays.asList(comment));

        // Act
        List<InnovationCatalogComment> result = adapter.findRecentActiveCommentsByInnovationId(1L);

        // Assert
        assertNotNull(result);
        verify(jpaRepository).findRecentActiveCommentsByInnovationId(1L);
    }

    @Test
    void findAllCommentsByInnovationId_ShouldReturnList() {
        // Arrange
        InnovationCatalogComment comment = new InnovationCatalogComment();
        when(jpaRepository.findAllCommentsByInnovationId(1L))
            .thenReturn(Arrays.asList(comment));

        // Act
        List<InnovationCatalogComment> result = adapter.findAllCommentsByInnovationId(1L);

        // Assert
        assertNotNull(result);
        verify(jpaRepository).findAllCommentsByInnovationId(1L);
    }

    @Test
    void findAllCommentsOrderByActiveSinceDesc_WithOffsetAndLimit_ShouldReturnPaginated() {
        // Arrange
        InnovationCatalogComment comment1 = new InnovationCatalogComment();
        InnovationCatalogComment comment2 = new InnovationCatalogComment();
        InnovationCatalogComment comment3 = new InnovationCatalogComment();
        when(jpaRepository.findAllByOrderByActiveSinceDesc())
            .thenReturn(Arrays.asList(comment1, comment2, comment3));

        // Act
        List<InnovationCatalogComment> result = adapter.findAllCommentsOrderByActiveSinceDesc(1, 2);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(jpaRepository).findAllByOrderByActiveSinceDesc();
    }

    @Test
    void findAllCommentsOrderByActiveSinceDesc_WithNullLimit_ShouldReturnAll() {
        // Arrange
        InnovationCatalogComment comment = new InnovationCatalogComment();
        when(jpaRepository.findAllByOrderByActiveSinceDesc())
            .thenReturn(Arrays.asList(comment));

        // Act
        List<InnovationCatalogComment> result = adapter.findAllCommentsOrderByActiveSinceDesc(0, null);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
    }

    @Test
    void softDeleteComment_ShouldReturnCount() {
        // Arrange
        when(jpaRepository.softDeleteComment(1L)).thenReturn(1);

        // Act
        int result = adapter.softDeleteComment(1L);

        // Assert
        assertEquals(1, result);
        verify(jpaRepository).softDeleteComment(1L);
    }

    @Test
    void existsActiveComment_WhenActive_ShouldReturnTrue() {
        // Arrange
        InnovationCatalogComment comment = new InnovationCatalogComment();
        comment.setIsActive(true);
        when(jpaRepository.findById(1L)).thenReturn(Optional.of(comment));

        // Act
        boolean result = adapter.existsActiveComment(1L);

        // Assert
        assertTrue(result);
        verify(jpaRepository).findById(1L);
    }

    @Test
    void existsActiveComment_WhenInactive_ShouldReturnFalse() {
        // Arrange
        InnovationCatalogComment comment = new InnovationCatalogComment();
        comment.setIsActive(false);
        when(jpaRepository.findById(1L)).thenReturn(Optional.of(comment));

        // Act
        boolean result = adapter.existsActiveComment(1L);

        // Assert
        assertFalse(result);
    }

    @Test
    void existsActiveComment_WhenNotExists_ShouldReturnFalse() {
        // Arrange
        when(jpaRepository.findById(999L)).thenReturn(Optional.empty());

        // Act
        boolean result = adapter.existsActiveComment(999L);

        // Assert
        assertFalse(result);
    }
}
