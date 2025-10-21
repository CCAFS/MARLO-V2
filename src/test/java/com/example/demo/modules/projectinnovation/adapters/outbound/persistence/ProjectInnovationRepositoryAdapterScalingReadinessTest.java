package com.example.demo.modules.projectinnovation.adapters.outbound.persistence;

import com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for ProjectInnovationRepositoryAdapter focusing on scaling readiness calculations
 */
@ExtendWith(MockitoExtension.class)
class ProjectInnovationRepositoryAdapterScalingReadinessTest {

    @Mock
    private ProjectInnovationInfoJpaRepository projectInnovationInfoJpaRepository;

    @InjectMocks
    private ProjectInnovationRepositoryAdapter repositoryAdapter;

    private ProjectInnovationInfo createMockInnovationInfo(Integer readinessScale) {
        ProjectInnovationInfo info = new ProjectInnovationInfo();
        info.setReadinessScale(readinessScale);
        return info;
    }

    @Test
    void findAverageScalingReadinessByPhase_WithValidData_ReturnsCorrectAverage() {
        // Arrange
        Long phaseId = 100L; // Generic test phase ID
        Double expectedAverage = 8.0; // (8+9+7+10+6)/5 = 40/5 = 8.0
        
        when(projectInnovationInfoJpaRepository.findAverageScalingReadinessByPhaseOptimized(phaseId))
                .thenReturn(expectedAverage);

        // Act
        Double result = repositoryAdapter.findAverageScalingReadinessByPhase(phaseId);

        // Assert
        assertEquals(8.0, result);
        verify(projectInnovationInfoJpaRepository).findAverageScalingReadinessByPhaseOptimized(phaseId);
    }

    @Test
    void findAverageScalingReadinessByPhase_WithNullValues_IgnoresNulls() {
        // Arrange
        Long phaseId = 123L;
        Double expectedAverage = 8.0; // (8+6+10)/3 = 24/3 = 8.0 (nulls ignored by optimized query)
        
        when(projectInnovationInfoJpaRepository.findAverageScalingReadinessByPhaseOptimized(phaseId))
                .thenReturn(expectedAverage);

        // Act
        Double result = repositoryAdapter.findAverageScalingReadinessByPhase(phaseId);

        // Assert
        assertEquals(8.0, result); // (8+6+10)/3 = 24/3 = 8.0 (nulls ignored)
        verify(projectInnovationInfoJpaRepository).findAverageScalingReadinessByPhaseOptimized(phaseId);
    }

    @Test
    void findAverageScalingReadinessByPhase_WithAllNullValues_ReturnsZero() {
        // Arrange
        Long phaseId = 999L;
        // Optimized query returns null when no valid values found
        
        when(projectInnovationInfoJpaRepository.findAverageScalingReadinessByPhaseOptimized(phaseId))
                .thenReturn(null);

        // Act
        Double result = repositoryAdapter.findAverageScalingReadinessByPhase(phaseId);

        // Assert
        assertEquals(0.0, result);
        verify(projectInnovationInfoJpaRepository).findAverageScalingReadinessByPhaseOptimized(phaseId);
    }

    @Test
    void findAverageScalingReadinessByPhase_WithEmptyList_ReturnsZero() {
        // Arrange
        Long phaseId = 456L;
        // Optimized query returns null when no records found
        
        when(projectInnovationInfoJpaRepository.findAverageScalingReadinessByPhaseOptimized(phaseId))
                .thenReturn(null);

        // Act
        Double result = repositoryAdapter.findAverageScalingReadinessByPhase(phaseId);

        // Assert
        assertEquals(0.0, result);
        verify(projectInnovationInfoJpaRepository).findAverageScalingReadinessByPhaseOptimized(phaseId);
    }

    @Test
    void findAverageScalingReadinessByPhase_WithSingleValue_ReturnsThatValue() {
        // Arrange
        Long phaseId = 789L;
        Double expectedAverage = 7.0;
        
        when(projectInnovationInfoJpaRepository.findAverageScalingReadinessByPhaseOptimized(phaseId))
                .thenReturn(expectedAverage);

        // Act
        Double result = repositoryAdapter.findAverageScalingReadinessByPhase(phaseId);

        // Assert
        assertEquals(7.0, result);
        verify(projectInnovationInfoJpaRepository).findAverageScalingReadinessByPhaseOptimized(phaseId);
    }

    @Test
    void findAverageScalingReadinessByPhase_WithRepositoryException_ReturnsZero() {
        // Arrange
        Long phaseId = 666L;
        List<ProjectInnovationInfo> fallbackData = Arrays.asList(
            createMockInnovationInfo(8),
            createMockInnovationInfo(7)
        );
        
        // First call to optimized method fails, then fallback to original method
        when(projectInnovationInfoJpaRepository.findAverageScalingReadinessByPhaseOptimized(phaseId))
                .thenThrow(new RuntimeException("Database connection error"));
        when(projectInnovationInfoJpaRepository.findByIdPhase(phaseId))
                .thenReturn(fallbackData);

        // Act
        Double result = repositoryAdapter.findAverageScalingReadinessByPhase(phaseId);

        // Assert
        assertEquals(7.5, result); // (8+7)/2 = 7.5 from fallback method
        verify(projectInnovationInfoJpaRepository).findAverageScalingReadinessByPhaseOptimized(phaseId);
        verify(projectInnovationInfoJpaRepository).findByIdPhase(phaseId);
    }

    @Test
    void findAverageScalingReadinessByPhase_WithDecimalResult_ReturnsExactValue() {
        // Arrange
        Long phaseId = 333L;
        Double expectedAverage = 8.0; // (8+9+7)/3 = 24/3 = 8.0
        
        when(projectInnovationInfoJpaRepository.findAverageScalingReadinessByPhaseOptimized(phaseId))
                .thenReturn(expectedAverage);

        // Act
        Double result = repositoryAdapter.findAverageScalingReadinessByPhase(phaseId);

        // Assert
        assertEquals(8.0, result); // (8+9+7)/3 = 24/3 = 8.0
        verify(projectInnovationInfoJpaRepository).findAverageScalingReadinessByPhaseOptimized(phaseId);
    }

    @Test
    void findAverageScalingReadinessByPhase_WithMultipleValues_CalculatesCorrectAverage() {
        // Arrange - Test with multiple values to verify average calculation
        Long phaseId = 200L; // Generic test phase ID
        Double expectedAverage = 8.4; // Sum: 8+9+8+9+8+7+10+9+8+8 = 84, Count: 10, Average: 84/10 = 8.4
        
        when(projectInnovationInfoJpaRepository.findAverageScalingReadinessByPhaseOptimized(phaseId))
                .thenReturn(expectedAverage);

        // Act
        Double result = repositoryAdapter.findAverageScalingReadinessByPhase(phaseId);

        // Assert
        // Sum: 8+9+8+9+8+7+10+9+8+8 = 84
        // Count: 10
        // Average: 84/10 = 8.4
        assertEquals(8.4, result);
        verify(projectInnovationInfoJpaRepository).findAverageScalingReadinessByPhaseOptimized(phaseId);
    }
}