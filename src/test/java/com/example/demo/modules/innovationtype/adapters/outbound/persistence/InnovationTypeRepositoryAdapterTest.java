package com.example.demo.modules.innovationtype.adapters.outbound.persistence;

import com.example.demo.modules.innovationtype.domain.model.InnovationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InnovationTypeRepositoryAdapterTest {

    private InnovationTypeJpaRepository jpaRepository;
    private InnovationTypeRepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        jpaRepository = mock(InnovationTypeJpaRepository.class);
        adapter = new InnovationTypeRepositoryAdapter(jpaRepository);
    }

    @Test
    void findAll_ShouldReturnList() {
        // Arrange
        InnovationType type1 = new InnovationType();
        type1.setId(1L);
        InnovationType type2 = new InnovationType();
        type2.setId(2L);
        when(jpaRepository.findAll()).thenReturn(Arrays.asList(type1, type2));

        // Act
        List<InnovationType> result = adapter.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(jpaRepository).findAll();
    }

    @Test
    void findById_WhenExists_ShouldReturnOptional() {
        // Arrange
        InnovationType type = new InnovationType();
        type.setId(1L);
        when(jpaRepository.findById(1L)).thenReturn(Optional.of(type));

        // Act
        Optional<InnovationType> result = adapter.findById(1L);

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
        Optional<InnovationType> result = adapter.findById(999L);

        // Assert
        assertFalse(result.isPresent());
        verify(jpaRepository).findById(999L);
    }
}
