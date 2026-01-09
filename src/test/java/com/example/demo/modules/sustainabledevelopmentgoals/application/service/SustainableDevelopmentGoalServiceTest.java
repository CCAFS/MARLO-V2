package com.example.demo.modules.sustainabledevelopmentgoals.application.service;

import com.example.demo.modules.sustainabledevelopmentgoals.application.port.outbound.SustainableDevelopmentGoalRepositoryPort;
import com.example.demo.modules.sustainabledevelopmentgoals.domain.model.SustainableDevelopmentGoal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SustainableDevelopmentGoalServiceTest {

    @Mock
    private SustainableDevelopmentGoalRepositoryPort repositoryPort;

    @InjectMocks
    private SustainableDevelopmentGoalService service;

    private SustainableDevelopmentGoal testSdg;

    @BeforeEach
    void setUp() {
        testSdg = new SustainableDevelopmentGoal();
        testSdg.setId(1L);
        testSdg.setSmoCode(1L);
        testSdg.setShortName("No Poverty");
        testSdg.setFullName("End poverty in all its forms everywhere");
    }

    @Test
    void findAllSustainableDevelopmentGoals_ShouldReturnList() {
        // Arrange
        List<SustainableDevelopmentGoal> expected = Arrays.asList(testSdg);
        when(repositoryPort.findAll()).thenReturn(expected);

        // Act
        List<SustainableDevelopmentGoal> result = service.findAllSustainableDevelopmentGoals();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testSdg.getId(), result.get(0).getId());
        verify(repositoryPort).findAll();
    }

    @Test
    void findAllSustainableDevelopmentGoals_WhenEmpty_ShouldReturnEmptyList() {
        // Arrange
        when(repositoryPort.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<SustainableDevelopmentGoal> result = service.findAllSustainableDevelopmentGoals();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(repositoryPort).findAll();
    }

    @Test
    void findSustainableDevelopmentGoalById_WhenExists_ShouldReturnOptional() {
        // Arrange
        when(repositoryPort.findById(1L)).thenReturn(Optional.of(testSdg));

        // Act
        Optional<SustainableDevelopmentGoal> result = service.findSustainableDevelopmentGoalById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(testSdg.getId(), result.get().getId());
        verify(repositoryPort).findById(1L);
    }

    @Test
    void findSustainableDevelopmentGoalById_WhenNotExists_ShouldReturnEmpty() {
        // Arrange
        when(repositoryPort.findById(999L)).thenReturn(Optional.empty());

        // Act
        Optional<SustainableDevelopmentGoal> result = service.findSustainableDevelopmentGoalById(999L);

        // Assert
        assertFalse(result.isPresent());
        verify(repositoryPort).findById(999L);
    }

    @Test
    void findSustainableDevelopmentGoalBySmoCode_WhenExists_ShouldReturnOptional() {
        // Arrange
        when(repositoryPort.findBySmoCode(1L)).thenReturn(Optional.of(testSdg));

        // Act
        Optional<SustainableDevelopmentGoal> result = service.findSustainableDevelopmentGoalBySmoCode(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(testSdg.getSmoCode(), result.get().getSmoCode());
        verify(repositoryPort).findBySmoCode(1L);
    }

    @Test
    void findSustainableDevelopmentGoalBySmoCode_WhenNotExists_ShouldReturnEmpty() {
        // Arrange
        when(repositoryPort.findBySmoCode(999L)).thenReturn(Optional.empty());

        // Act
        Optional<SustainableDevelopmentGoal> result = service.findSustainableDevelopmentGoalBySmoCode(999L);

        // Assert
        assertFalse(result.isPresent());
        verify(repositoryPort).findBySmoCode(999L);
    }
}
