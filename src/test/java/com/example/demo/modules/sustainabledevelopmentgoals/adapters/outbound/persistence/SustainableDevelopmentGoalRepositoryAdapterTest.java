package com.example.demo.modules.sustainabledevelopmentgoals.adapters.outbound.persistence;

import com.example.demo.modules.sustainabledevelopmentgoals.domain.model.SustainableDevelopmentGoal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SustainableDevelopmentGoalRepositoryAdapterTest {

    private SustainableDevelopmentGoalJpaRepository jpaRepository;
    private SustainableDevelopmentGoalRepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        jpaRepository = mock(SustainableDevelopmentGoalJpaRepository.class);
        adapter = new SustainableDevelopmentGoalRepositoryAdapter(jpaRepository);
    }

    @Test
    void findAll_ShouldReturnList() {
        // Arrange
        SustainableDevelopmentGoal sdg1 = new SustainableDevelopmentGoal();
        sdg1.setId(1L);
        SustainableDevelopmentGoal sdg2 = new SustainableDevelopmentGoal();
        sdg2.setId(2L);
        when(jpaRepository.findAll()).thenReturn(Arrays.asList(sdg1, sdg2));

        // Act
        List<SustainableDevelopmentGoal> result = adapter.findAll();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(jpaRepository).findAll();
    }

    @Test
    void findById_WhenExists_ShouldReturnOptional() {
        // Arrange
        SustainableDevelopmentGoal sdg = new SustainableDevelopmentGoal();
        sdg.setId(1L);
        when(jpaRepository.findById(1L)).thenReturn(Optional.of(sdg));

        // Act
        Optional<SustainableDevelopmentGoal> result = adapter.findById(1L);

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
        Optional<SustainableDevelopmentGoal> result = adapter.findById(999L);

        // Assert
        assertFalse(result.isPresent());
        verify(jpaRepository).findById(999L);
    }

    @Test
    void findBySmoCode_WhenExists_ShouldReturnOptional() {
        // Arrange
        SustainableDevelopmentGoal sdg = new SustainableDevelopmentGoal();
        sdg.setSmoCode(1L);
        when(jpaRepository.findBySmoCode(1L)).thenReturn(Optional.of(sdg));

        // Act
        Optional<SustainableDevelopmentGoal> result = adapter.findBySmoCode(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getSmoCode());
        verify(jpaRepository).findBySmoCode(1L);
    }

    @Test
    void findBySmoCode_WhenNotExists_ShouldReturnEmpty() {
        // Arrange
        when(jpaRepository.findBySmoCode(999L)).thenReturn(Optional.empty());

        // Act
        Optional<SustainableDevelopmentGoal> result = adapter.findBySmoCode(999L);

        // Assert
        assertFalse(result.isPresent());
        verify(jpaRepository).findBySmoCode(999L);
    }
}
