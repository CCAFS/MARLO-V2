package com.example.demo.modules.projectinnovation.adapters.outbound.persistence;

import com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationInfo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
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
        Long phaseId = 428L;
        List<ProjectInnovationInfo> mockInnovations = Arrays.asList(
            createMockInnovationInfo(8),
            createMockInnovationInfo(9),
            createMockInnovationInfo(7),
            createMockInnovationInfo(10),
            createMockInnovationInfo(6)
        );
        
        when(projectInnovationInfoJpaRepository.findByIdPhase(phaseId))
                .thenReturn(mockInnovations);

        // Act
        Double result = repositoryAdapter.findAverageScalingReadinessByPhase(phaseId);

        // Assert
        assertEquals(8.0, result); // (8+9+7+10+6)/5 = 40/5 = 8.0
        verify(projectInnovationInfoJpaRepository).findByIdPhase(phaseId);
    }

    @Test
    void findAverageScalingReadinessByPhase_WithNullValues_IgnoresNulls() {
        // Arrange
        Long phaseId = 123L;
        List<ProjectInnovationInfo> mockInnovations = Arrays.asList(
            createMockInnovationInfo(8),
            createMockInnovationInfo(null), // Should be ignored
            createMockInnovationInfo(6),
            createMockInnovationInfo(null), // Should be ignored
            createMockInnovationInfo(10)
        );
        
        when(projectInnovationInfoJpaRepository.findByIdPhase(phaseId))
                .thenReturn(mockInnovations);

        // Act
        Double result = repositoryAdapter.findAverageScalingReadinessByPhase(phaseId);

        // Assert
        assertEquals(8.0, result); // (8+6+10)/3 = 24/3 = 8.0 (nulls ignored)
        verify(projectInnovationInfoJpaRepository).findByIdPhase(phaseId);
    }

    @Test
    void findAverageScalingReadinessByPhase_WithAllNullValues_ReturnsZero() {
        // Arrange
        Long phaseId = 999L;
        List<ProjectInnovationInfo> mockInnovations = Arrays.asList(
            createMockInnovationInfo(null),
            createMockInnovationInfo(null),
            createMockInnovationInfo(null)
        );
        
        when(projectInnovationInfoJpaRepository.findByIdPhase(phaseId))
                .thenReturn(mockInnovations);

        // Act
        Double result = repositoryAdapter.findAverageScalingReadinessByPhase(phaseId);

        // Assert
        assertEquals(0.0, result);
        verify(projectInnovationInfoJpaRepository).findByIdPhase(phaseId);
    }

    @Test
    void findAverageScalingReadinessByPhase_WithEmptyList_ReturnsZero() {
        // Arrange
        Long phaseId = 456L;
        List<ProjectInnovationInfo> mockInnovations = Collections.emptyList();
        
        when(projectInnovationInfoJpaRepository.findByIdPhase(phaseId))
                .thenReturn(mockInnovations);

        // Act
        Double result = repositoryAdapter.findAverageScalingReadinessByPhase(phaseId);

        // Assert
        assertEquals(0.0, result);
        verify(projectInnovationInfoJpaRepository).findByIdPhase(phaseId);
    }

    @Test
    void findAverageScalingReadinessByPhase_WithSingleValue_ReturnsThatValue() {
        // Arrange
        Long phaseId = 789L;
        List<ProjectInnovationInfo> mockInnovations = Arrays.asList(
            createMockInnovationInfo(7)
        );
        
        when(projectInnovationInfoJpaRepository.findByIdPhase(phaseId))
                .thenReturn(mockInnovations);

        // Act
        Double result = repositoryAdapter.findAverageScalingReadinessByPhase(phaseId);

        // Assert
        assertEquals(7.0, result);
        verify(projectInnovationInfoJpaRepository).findByIdPhase(phaseId);
    }

    @Test
    void findAverageScalingReadinessByPhase_WithRepositoryException_ReturnsZero() {
        // Arrange
        Long phaseId = 666L;
        
        when(projectInnovationInfoJpaRepository.findByIdPhase(phaseId))
                .thenThrow(new RuntimeException("Database connection error"));

        // Act
        Double result = repositoryAdapter.findAverageScalingReadinessByPhase(phaseId);

        // Assert
        assertEquals(0.0, result);
        verify(projectInnovationInfoJpaRepository).findByIdPhase(phaseId);
    }

    @Test
    void findAverageScalingReadinessByPhase_WithDecimalResult_ReturnsExactValue() {
        // Arrange
        Long phaseId = 333L;
        List<ProjectInnovationInfo> mockInnovations = Arrays.asList(
            createMockInnovationInfo(8),
            createMockInnovationInfo(9),
            createMockInnovationInfo(7)
        );
        
        when(projectInnovationInfoJpaRepository.findByIdPhase(phaseId))
                .thenReturn(mockInnovations);

        // Act
        Double result = repositoryAdapter.findAverageScalingReadinessByPhase(phaseId);

        // Assert
        assertEquals(8.0, result); // (8+9+7)/3 = 24/3 = 8.0
        verify(projectInnovationInfoJpaRepository).findByIdPhase(phaseId);
    }

    @Test
    void findAverageScalingReadinessByPhase_WithRealWorldScenario_MatchesExpectedCalculation() {
        // Arrange - Simulating real data similar to phase 428
        Long phaseId = 428L;
        List<ProjectInnovationInfo> mockInnovations = Arrays.asList(
            createMockInnovationInfo(8),
            createMockInnovationInfo(9),
            createMockInnovationInfo(8),
            createMockInnovationInfo(9),
            createMockInnovationInfo(8),
            createMockInnovationInfo(7),
            createMockInnovationInfo(10),
            createMockInnovationInfo(9),
            createMockInnovationInfo(8),
            createMockInnovationInfo(8)
        );
        
        when(projectInnovationInfoJpaRepository.findByIdPhase(phaseId))
                .thenReturn(mockInnovations);

        // Act
        Double result = repositoryAdapter.findAverageScalingReadinessByPhase(phaseId);

        // Assert
        // Sum: 8+9+8+9+8+7+10+9+8+8 = 84
        // Count: 10
        // Average: 84/10 = 8.4
        assertEquals(8.4, result);
        verify(projectInnovationInfoJpaRepository).findByIdPhase(phaseId);
    }
}