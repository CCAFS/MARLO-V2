package com.example.demo.modules.projectinnovation.application.service;

import com.example.demo.modules.projectinnovation.application.port.outbound.ProjectInnovationRepositoryPort;
import com.example.demo.modules.projectinnovation.domain.model.ProjectInnovation;
import com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationInfo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectInnovationServiceTest {

    private ProjectInnovationRepositoryPort repositoryPort;
    private ProjectInnovationService service;
    private List<ProjectInnovationInfo> infoWithFiltersResult;
    private List<ProjectInnovationInfo> infoBySdgResult;

    private ProjectInnovation testInnovation;
    private ProjectInnovationInfo testInnovationInfo;

    @BeforeEach
    void setUp() {
        infoWithFiltersResult = Collections.emptyList();
        infoBySdgResult = Collections.emptyList();
        repositoryPort = mock(ProjectInnovationRepositoryPort.class, invocation -> {
            String methodName = invocation.getMethod().getName();
            if ("findActiveInnovationsInfoWithFilters".equals(methodName)) {
                return infoWithFiltersResult;
            }
            if ("findActiveInnovationsInfoBySdgFilters".equals(methodName)) {
                return infoBySdgResult;
            }
            return RETURNS_DEFAULTS.answer(invocation);
        });
        service = new ProjectInnovationService(repositoryPort);

        testInnovation = new ProjectInnovation();
        testInnovation.setId(1L);
        testInnovation.setProjectId(100L);
        testInnovation.setIsActive(true);
        testInnovation.setActiveSince(LocalDateTime.now());
        testInnovation.setCreatedBy(1L);

        testInnovationInfo = new ProjectInnovationInfo();
        testInnovationInfo.setId(1L);
        testInnovationInfo.setProjectInnovationId(1L);
    }

    @Test
    void findAllProjectInnovations_ShouldReturnList() {
        // Arrange
        List<ProjectInnovation> expected = Arrays.asList(testInnovation);
        when(repositoryPort.findAll()).thenReturn(expected);

        // Act
        List<ProjectInnovation> result = service.findAllProjectInnovations();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testInnovation.getId(), result.get(0).getId());
        verify(repositoryPort).findAll();
    }

    @Test
    void findProjectInnovationById_WhenExists_ShouldReturnOptional() {
        // Arrange
        when(repositoryPort.findById(1L)).thenReturn(Optional.of(testInnovation));

        // Act
        Optional<ProjectInnovation> result = service.findProjectInnovationById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(testInnovation.getId(), result.get().getId());
        verify(repositoryPort).findById(1L);
    }

    @Test
    void findProjectInnovationById_WhenNotExists_ShouldReturnEmpty() {
        // Arrange
        when(repositoryPort.findById(999L)).thenReturn(Optional.empty());

        // Act
        Optional<ProjectInnovation> result = service.findProjectInnovationById(999L);

        // Assert
        assertFalse(result.isPresent());
        verify(repositoryPort).findById(999L);
    }

    @Test
    void createProjectInnovation_ShouldSaveAndReturn() {
        // Arrange
        ProjectInnovation newInnovation = new ProjectInnovation();
        newInnovation.setProjectId(200L);
        newInnovation.setCreatedBy(2L);
        when(repositoryPort.save(any(ProjectInnovation.class))).thenReturn(testInnovation);

        // Act
        ProjectInnovation result = service.createProjectInnovation(newInnovation);

        // Assert
        assertNotNull(result);
        verify(repositoryPort).save(newInnovation);
    }

    @Test
    void updateProjectInnovation_WhenExists_ShouldUpdateAndReturn() {
        // Arrange
        ProjectInnovation updatedInnovation = new ProjectInnovation();
        updatedInnovation.setProjectId(300L);
        when(repositoryPort.findById(1L)).thenReturn(Optional.of(testInnovation));
        when(repositoryPort.save(any(ProjectInnovation.class))).thenReturn(updatedInnovation);

        // Act
        ProjectInnovation result = service.updateProjectInnovation(1L, updatedInnovation);

        // Assert
        assertNotNull(result);
        assertEquals(1L, updatedInnovation.getId());
        verify(repositoryPort).findById(1L);
        verify(repositoryPort).save(updatedInnovation);
    }

    @Test
    void updateProjectInnovation_WhenNotExists_ShouldThrowException() {
        // Arrange
        ProjectInnovation updatedInnovation = new ProjectInnovation();
        when(repositoryPort.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> service.updateProjectInnovation(999L, updatedInnovation));
        assertTrue(exception.getMessage().contains("not found"));
        verify(repositoryPort).findById(999L);
        verify(repositoryPort, never()).save(any());
    }

    @Test
    void deleteProjectInnovation_WhenExists_ShouldDelete() {
        // Arrange
        when(repositoryPort.findById(1L)).thenReturn(Optional.of(testInnovation));
        doNothing().when(repositoryPort).deleteById(1L);

        // Act
        service.deleteProjectInnovation(1L);

        // Assert
        verify(repositoryPort).findById(1L);
        verify(repositoryPort).deleteById(1L);
    }

    @Test
    void deleteProjectInnovation_WhenNotExists_ShouldThrowException() {
        // Arrange
        when(repositoryPort.findById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> service.deleteProjectInnovation(999L));
        assertTrue(exception.getMessage().contains("not found"));
        verify(repositoryPort).findById(999L);
        verify(repositoryPort, never()).deleteById(any());
    }

    @Test
    void findAllProjectInnovationsIncludingInactive_ShouldReturnList() {
        // Arrange
        List<ProjectInnovation> expected = Arrays.asList(testInnovation);
        when(repositoryPort.findAll()).thenReturn(expected);

        // Act
        List<ProjectInnovation> result = service.findAllProjectInnovationsIncludingInactive();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repositoryPort).findAll();
    }

    @Test
    void findProjectInnovationsByProjectId_ShouldReturnList() {
        // Arrange
        List<ProjectInnovation> expected = Arrays.asList(testInnovation);
        when(repositoryPort.findByProjectId(100L)).thenReturn(expected);

        // Act
        List<ProjectInnovation> result = service.findProjectInnovationsByProjectId(100L);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repositoryPort).findByProjectId(100L);
    }

    @Test
    void findProjectInnovationsByProjectIdAndActive_ShouldReturnList() {
        // Arrange
        List<ProjectInnovation> expected = Arrays.asList(testInnovation);
        when(repositoryPort.findByProjectIdAndActive(100L, true)).thenReturn(expected);

        // Act
        List<ProjectInnovation> result = service.findProjectInnovationsByProjectIdAndActive(100L, true);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repositoryPort).findByProjectIdAndActive(100L, true);
    }

    @Test
    void activateProjectInnovation_WhenExists_ShouldActivate() {
        // Arrange
        ProjectInnovation inactiveInnovation = new ProjectInnovation();
        inactiveInnovation.setId(1L);
        inactiveInnovation.setIsActive(false);
        when(repositoryPort.findByIdIncludingInactive(1L)).thenReturn(Optional.of(inactiveInnovation));
        when(repositoryPort.save(any(ProjectInnovation.class))).thenReturn(inactiveInnovation);

        // Act
        ProjectInnovation result = service.activateProjectInnovation(1L);

        // Assert
        assertTrue(result.getIsActive());
        verify(repositoryPort).findByIdIncludingInactive(1L);
        verify(repositoryPort).save(inactiveInnovation);
    }

    @Test
    void activateProjectInnovation_WhenNotExists_ShouldThrowException() {
        // Arrange
        when(repositoryPort.findByIdIncludingInactive(999L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> service.activateProjectInnovation(999L));
        assertTrue(exception.getMessage().contains("not found"));
        verify(repositoryPort).findByIdIncludingInactive(999L);
        verify(repositoryPort, never()).save(any());
    }

    @Test
    void deactivateProjectInnovation_WhenExists_ShouldDeactivate() {
        // Arrange
        when(repositoryPort.findByIdIncludingInactive(1L)).thenReturn(Optional.of(testInnovation));
        when(repositoryPort.save(any(ProjectInnovation.class))).thenReturn(testInnovation);

        // Act
        ProjectInnovation result = service.deactivateProjectInnovation(1L);

        // Assert
        assertFalse(result.getIsActive());
        verify(repositoryPort).findByIdIncludingInactive(1L);
        verify(repositoryPort).save(testInnovation);
    }

    @Test
    void deactivateProjectInnovation_WhenNotExists_ShouldThrowException() {
        // Arrange
        when(repositoryPort.findByIdIncludingInactive(999L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> service.deactivateProjectInnovation(999L));
        assertTrue(exception.getMessage().contains("not found"));
        verify(repositoryPort).findByIdIncludingInactive(999L);
        verify(repositoryPort, never()).save(any());
    }

    @Test
    void findProjectInnovationInfoByProjectInnovationId_ShouldReturnList() {
        // Arrange
        List<ProjectInnovationInfo> expected = Arrays.asList(testInnovationInfo);
        when(repositoryPort.findProjectInnovationInfoByProjectInnovationId(1L)).thenReturn(expected);

        // Act
        List<ProjectInnovationInfo> result = service.findProjectInnovationInfoByProjectInnovationId(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repositoryPort).findProjectInnovationInfoByProjectInnovationId(1L);
    }

    @Test
    void findProjectInnovationInfoById_WhenExists_ShouldReturnOptional() {
        // Arrange
        when(repositoryPort.findProjectInnovationInfoById(1L)).thenReturn(Optional.of(testInnovationInfo));

        // Act
        Optional<ProjectInnovationInfo> result = service.findProjectInnovationInfoById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(testInnovationInfo.getId(), result.get().getId());
        verify(repositoryPort).findProjectInnovationInfoById(1L);
    }

    @Test
    void findProjectInnovationInfoByInnovationIdAndPhaseId_WhenInnovationExists_ShouldReturnOptional() {
        // Arrange
        when(repositoryPort.findById(1L)).thenReturn(Optional.of(testInnovation));
        when(repositoryPort.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 1L))
            .thenReturn(Optional.of(testInnovationInfo));

        // Act
        Optional<ProjectInnovationInfo> result = service.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 1L);

        // Assert
        assertTrue(result.isPresent());
        verify(repositoryPort).findById(1L);
        verify(repositoryPort).findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 1L);
    }

    @Test
    void findProjectInnovationInfoByInnovationIdAndPhaseId_WhenInnovationNotExists_ShouldReturnEmpty() {
        // Arrange
        when(repositoryPort.findById(999L)).thenReturn(Optional.empty());

        // Act
        Optional<ProjectInnovationInfo> result = service.findProjectInnovationInfoByInnovationIdAndPhaseId(999L, 1L);

        // Assert
        assertFalse(result.isPresent());
        verify(repositoryPort).findById(999L);
        verify(repositoryPort, never()).findProjectInnovationInfoByInnovationIdAndPhaseId(any(), any());
    }

    @Test
    void createProjectInnovationInfo_ShouldSaveAndReturn() {
        // Arrange
        when(repositoryPort.saveProjectInnovationInfo(any(ProjectInnovationInfo.class)))
            .thenReturn(testInnovationInfo);

        // Act
        ProjectInnovationInfo result = service.createProjectInnovationInfo(testInnovationInfo);

        // Assert
        assertNotNull(result);
        verify(repositoryPort).saveProjectInnovationInfo(testInnovationInfo);
    }

    @Test
    void updateProjectInnovationInfo_WhenExists_ShouldUpdateAndReturn() {
        // Arrange
        ProjectInnovationInfo updatedInfo = new ProjectInnovationInfo();
        updatedInfo.setProjectInnovationId(1L);
        when(repositoryPort.findProjectInnovationInfoById(1L)).thenReturn(Optional.of(testInnovationInfo));
        when(repositoryPort.saveProjectInnovationInfo(any(ProjectInnovationInfo.class)))
            .thenReturn(updatedInfo);

        // Act
        ProjectInnovationInfo result = service.updateProjectInnovationInfo(1L, updatedInfo);

        // Assert
        assertNotNull(result);
        assertEquals(1L, updatedInfo.getId());
        verify(repositoryPort).findProjectInnovationInfoById(1L);
        verify(repositoryPort).saveProjectInnovationInfo(updatedInfo);
    }

    @Test
    void updateProjectInnovationInfo_WhenNotExists_ShouldThrowException() {
        // Arrange
        ProjectInnovationInfo updatedInfo = new ProjectInnovationInfo();
        when(repositoryPort.findProjectInnovationInfoById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> service.updateProjectInnovationInfo(999L, updatedInfo));
        assertTrue(exception.getMessage().contains("not found"));
        verify(repositoryPort).findProjectInnovationInfoById(999L);
        verify(repositoryPort, never()).saveProjectInnovationInfo(any());
    }

    @Test
    void deleteProjectInnovationInfo_WhenExists_ShouldDelete() {
        // Arrange
        when(repositoryPort.findProjectInnovationInfoById(1L)).thenReturn(Optional.of(testInnovationInfo));
        doNothing().when(repositoryPort).deleteProjectInnovationInfoById(1L);

        // Act
        service.deleteProjectInnovationInfo(1L);

        // Assert
        verify(repositoryPort).findProjectInnovationInfoById(1L);
        verify(repositoryPort).deleteProjectInnovationInfoById(1L);
    }

    @Test
    void deleteProjectInnovationInfo_WhenNotExists_ShouldThrowException() {
        // Arrange
        when(repositoryPort.findProjectInnovationInfoById(999L)).thenReturn(Optional.empty());

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, 
            () -> service.deleteProjectInnovationInfo(999L));
        assertTrue(exception.getMessage().contains("not found"));
        verify(repositoryPort).findProjectInnovationInfoById(999L);
        verify(repositoryPort, never()).deleteProjectInnovationInfoById(any());
    }

    @Test
    void findProjectInnovationInfoByPhase_ShouldReturnList() {
        // Arrange
        List<ProjectInnovationInfo> expected = Arrays.asList(testInnovationInfo);
        when(repositoryPort.findProjectInnovationInfoByPhase(1L)).thenReturn(expected);

        // Act
        List<ProjectInnovationInfo> result = service.findProjectInnovationInfoByPhase(1L);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repositoryPort).findProjectInnovationInfoByPhase(1L);
    }

    @Test
    void findActiveInnovationsByPhase_ShouldReturnList() {
        // Arrange
        List<ProjectInnovation> expected = Arrays.asList(testInnovation);
        when(repositoryPort.findActiveInnovationsByPhase(1)).thenReturn(expected);

        // Act
        List<ProjectInnovation> result = service.findActiveInnovationsByPhase(1);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repositoryPort).findActiveInnovationsByPhase(1);
    }

    @Test
    void findActiveInnovationsWithFilters_ShouldReturnList() {
        // Arrange
        List<ProjectInnovation> expected = Arrays.asList(testInnovation);
        when(repositoryPort.findActiveInnovationsWithFilters(1L, 5, 2L)).thenReturn(expected);

        // Act
        List<ProjectInnovation> result = service.findActiveInnovationsWithFilters(1L, 5, 2L);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repositoryPort).findActiveInnovationsWithFilters(1L, 5, 2L);
    }

    @Test
    void findActiveInnovationsBySdgFilters_ShouldReturnList() {
        // Arrange
        List<ProjectInnovation> expected = Arrays.asList(testInnovation);
        when(repositoryPort.findActiveInnovationsBySdgFilters(1L, 1L, 3L)).thenReturn(expected);

        // Act
        List<ProjectInnovation> result = service.findActiveInnovationsBySdgFilters(1L, 1L, 3L);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repositoryPort).findActiveInnovationsBySdgFilters(1L, 1L, 3L);
    }

    @Test
    void findAllActiveInnovationsComplete_ShouldReturnList() {
        // Arrange
        List<ProjectInnovation> expected = Arrays.asList(testInnovation);
        when(repositoryPort.findAllActiveInnovationsComplete()).thenReturn(expected);

        // Act
        List<ProjectInnovation> result = service.findAllActiveInnovationsComplete();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repositoryPort).findAllActiveInnovationsComplete();
    }

    @Test
    void findActiveInnovationsInfoWithFilters_ShouldReturnList() {
        // Arrange
        List<ProjectInnovationInfo> expected = Arrays.asList(testInnovationInfo);
        List<Long> countryIds = Arrays.asList(1L, 2L);
        List<Long> actorIds = Arrays.asList(3L, 4L);
        List<String> actorNames = Arrays.asList("Researcher");
        infoWithFiltersResult = expected;

        // Act
        List<ProjectInnovationInfo> result = invokeFindActiveInnovationsInfoWithFilters(
            1L, 5, 2L, countryIds, actorIds, actorNames);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(wasMethodInvoked(repositoryPort, "findActiveInnovationsInfoWithFilters"));
    }

    @Test
    void findActiveInnovationsInfoBySdgFilters_ShouldReturnList() {
        // Arrange
        List<ProjectInnovationInfo> expected = Arrays.asList(testInnovationInfo);
        List<Long> countryIds = Arrays.asList(1L, 2L);
        List<Long> actorIds = Arrays.asList(3L, 4L);
        List<String> actorNames = Arrays.asList("Farmer");
        infoBySdgResult = expected;

        // Act
        List<ProjectInnovationInfo> result = invokeFindActiveInnovationsInfoBySdgFilters(
            1L, 1L, 3L, countryIds, actorIds, actorNames);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertTrue(wasMethodInvoked(repositoryPort, "findActiveInnovationsInfoBySdgFilters"));
    }

    @Test
    void findAllActiveInnovationsInfo_ShouldReturnList() {
        // Arrange
        List<ProjectInnovationInfo> expected = Arrays.asList(testInnovationInfo);
        when(repositoryPort.findAllActiveInnovationsInfo()).thenReturn(expected);

        // Act
        List<ProjectInnovationInfo> result = service.findAllActiveInnovationsInfo();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        verify(repositoryPort).findAllActiveInnovationsInfo();
    }

    @SuppressWarnings("unchecked")
    private List<ProjectInnovationInfo> invokeFindActiveInnovationsInfoWithFilters(
            Long phase,
            Integer readinessScale,
            Long innovationTypeId,
            List<Long> countryIds,
            List<Long> actorIds,
            List<String> actorNames) {
        try {
            Method method = ProjectInnovationService.class.getMethod(
                "findActiveInnovationsInfoWithFilters",
                Long.class, Integer.class, Long.class, List.class, List.class, List.class);
            return (List<ProjectInnovationInfo>) method.invoke(
                service, phase, readinessScale, innovationTypeId, countryIds, actorIds, actorNames);
        } catch (NoSuchMethodException e) {
            try {
                Method method = ProjectInnovationService.class.getMethod(
                    "findActiveInnovationsInfoWithFilters",
                    Long.class, Integer.class, Long.class, List.class, List.class);
                return (List<ProjectInnovationInfo>) method.invoke(
                    service, phase, readinessScale, innovationTypeId, countryIds, actorIds);
            } catch (ReflectiveOperationException inner) {
                throw new RuntimeException(inner);
            }
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private List<ProjectInnovationInfo> invokeFindActiveInnovationsInfoBySdgFilters(
            Long innovationId,
            Long phase,
            Long sdgId,
            List<Long> countryIds,
            List<Long> actorIds,
            List<String> actorNames) {
        try {
            Method method = ProjectInnovationService.class.getMethod(
                "findActiveInnovationsInfoBySdgFilters",
                Long.class, Long.class, Long.class, List.class, List.class, List.class);
            return (List<ProjectInnovationInfo>) method.invoke(
                service, innovationId, phase, sdgId, countryIds, actorIds, actorNames);
        } catch (NoSuchMethodException e) {
            try {
                Method method = ProjectInnovationService.class.getMethod(
                    "findActiveInnovationsInfoBySdgFilters",
                    Long.class, Long.class, Long.class, List.class, List.class);
                return (List<ProjectInnovationInfo>) method.invoke(
                    service, innovationId, phase, sdgId, countryIds, actorIds);
            } catch (ReflectiveOperationException inner) {
                throw new RuntimeException(inner);
            }
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean wasMethodInvoked(Object mock, String methodName) {
        return mockingDetails(mock).getInvocations().stream()
            .anyMatch(invocation -> invocation.getMethod().getName().equals(methodName));
    }
}
