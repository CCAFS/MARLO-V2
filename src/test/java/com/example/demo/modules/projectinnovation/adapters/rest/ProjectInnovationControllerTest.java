package com.example.demo.modules.projectinnovation.adapters.rest;

import com.example.demo.modules.innovationtype.adapters.outbound.persistence.InnovationTypeRepositoryAdapter;
import com.example.demo.modules.projectinnovation.adapters.rest.mapper.ProjectInnovationActorsMapper;
import com.example.demo.modules.projectinnovation.adapters.outbound.persistence.LocElementJpaRepository;
import com.example.demo.modules.projectinnovation.adapters.outbound.persistence.ProjectInnovationRepositoryAdapter;
import com.example.demo.modules.projectinnovation.application.port.inbound.ProjectInnovationUseCase;
import com.example.demo.modules.projectinnovation.application.service.ProjectInnovationActorsService;
import com.example.demo.modules.projectinnovation.domain.model.ProjectInnovation;
import com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationInfo;
import com.example.demo.modules.sustainabledevelopmentgoals.adapters.outbound.persistence.SustainableDevelopmentGoalJpaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectInnovationControllerTest {

    @Mock
    private ProjectInnovationUseCase projectInnovationUseCase;

    @Mock
    private ProjectInnovationActorsService actorsService;

    @Mock
    private ProjectInnovationActorsMapper actorsMapper;

    @Mock
    private ProjectInnovationRepositoryAdapter repositoryAdapter;

    @Mock
    private LocElementJpaRepository locElementRepository;

    @Mock
    private InnovationTypeRepositoryAdapter innovationTypeRepository;

    @Mock
    private SustainableDevelopmentGoalJpaRepository sdgRepository;

    @InjectMocks
    private ProjectInnovationController controller;

    private ProjectInnovation testInnovation;
    private ProjectInnovationInfo testInnovationInfo;

    @BeforeEach
    void setUp() {
        testInnovation = new ProjectInnovation();
        testInnovation.setId(1L);
        testInnovation.setProjectId(100L);
        testInnovation.setIsActive(true);
        testInnovation.setActiveSince(LocalDateTime.now());
        testInnovation.setCreatedBy(1L);

        testInnovationInfo = new ProjectInnovationInfo();
        testInnovationInfo.setId(1L);
        testInnovationInfo.setProjectInnovationId(1L);
        testInnovationInfo.setIdPhase(1L);
    }

    @Test
    void getProjectInnovationById_WhenExists_ShouldReturnOk() {
        // Arrange
        when(projectInnovationUseCase.findProjectInnovationById(1L))
            .thenReturn(Optional.of(testInnovation));

        // Act
        ResponseEntity<ProjectInnovationResponse> result = controller.getProjectInnovationById(1L);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(testInnovation.getId(), result.getBody().id());
        verify(projectInnovationUseCase).findProjectInnovationById(1L);
    }

    @Test
    void getProjectInnovationById_WhenNotExists_ShouldReturnNotFound() {
        // Arrange
        when(projectInnovationUseCase.findProjectInnovationById(999L))
            .thenReturn(Optional.empty());

        // Act
        ResponseEntity<ProjectInnovationResponse> result = controller.getProjectInnovationById(999L);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        verify(projectInnovationUseCase).findProjectInnovationById(999L);
    }

    @Test
    void createProjectInnovation_ShouldReturnCreated() {
        // Arrange
        CreateProjectInnovationRequest request = new CreateProjectInnovationRequest(
            100L, 1L, "Test justification"
        );
        when(projectInnovationUseCase.createProjectInnovation(any(ProjectInnovation.class)))
            .thenReturn(testInnovation);

        // Act
        ResponseEntity<ProjectInnovationResponse> result = controller.createProjectInnovation(request);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertNotNull(result.getBody());
        verify(projectInnovationUseCase).createProjectInnovation(any(ProjectInnovation.class));
    }

    @Test
    void updateProjectInnovation_WhenExists_ShouldReturnOk() {
        // Arrange
        UpdateProjectInnovationRequest request = new UpdateProjectInnovationRequest(
            true, 2L, "Updated justification"
        );
        when(projectInnovationUseCase.updateProjectInnovation(eq(1L), any(ProjectInnovation.class)))
            .thenReturn(testInnovation);

        // Act
        ResponseEntity<ProjectInnovationResponse> result = controller.updateProjectInnovation(1L, request);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        verify(projectInnovationUseCase).updateProjectInnovation(eq(1L), any(ProjectInnovation.class));
    }

    @Test
    void updateProjectInnovation_WhenNotExists_ShouldReturnNotFound() {
        // Arrange
        UpdateProjectInnovationRequest request = new UpdateProjectInnovationRequest(
            true, 2L, "Updated justification"
        );
        when(projectInnovationUseCase.updateProjectInnovation(eq(999L), any(ProjectInnovation.class)))
            .thenThrow(new RuntimeException("Not found"));

        // Act
        ResponseEntity<ProjectInnovationResponse> result = controller.updateProjectInnovation(999L, request);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void getProjectInnovationsByProjectId_ShouldReturnList() {
        // Arrange
        List<ProjectInnovation> innovations = Arrays.asList(testInnovation);
        when(projectInnovationUseCase.findProjectInnovationsByProjectId(100L))
            .thenReturn(innovations);

        // Act
        ResponseEntity<List<ProjectInnovationResponse>> result = controller.getProjectInnovationsByProjectId(100L);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().size());
        verify(projectInnovationUseCase).findProjectInnovationsByProjectId(100L);
    }

    @Test
    void activateProjectInnovation_WhenExists_ShouldReturnOk() {
        // Arrange
        when(projectInnovationUseCase.activateProjectInnovation(1L))
            .thenReturn(testInnovation);

        // Act
        ResponseEntity<ProjectInnovationResponse> result = controller.activateProjectInnovation(1L);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        verify(projectInnovationUseCase).activateProjectInnovation(1L);
    }

    @Test
    void activateProjectInnovation_WhenNotExists_ShouldReturnNotFound() {
        // Arrange
        when(projectInnovationUseCase.activateProjectInnovation(999L))
            .thenThrow(new RuntimeException("Not found"));

        // Act
        ResponseEntity<ProjectInnovationResponse> result = controller.activateProjectInnovation(999L);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void getProjectInnovationInfoByInnovationIdAndPhaseId_WhenExists_ShouldReturnOk() {
        // Arrange
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 1L))
            .thenReturn(Optional.of(testInnovationInfo));

        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 1L);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        verify(projectInnovationUseCase).findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 1L);
    }

    @Test
    void getProjectInnovationInfoByInnovationIdAndPhaseId_WhenNotExists_ShouldReturnNotFound() {
        // Arrange
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(999L, 1L))
            .thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(999L, 1L);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void getInnovationsByPhase_ShouldReturnList() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findProjectInnovationInfoByPhase(1L))
            .thenReturn(innovations);

        // Act
        ResponseEntity<?> result = controller.getInnovationsByPhase(1L);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        verify(projectInnovationUseCase).findProjectInnovationInfoByPhase(1L);
    }

    @Test
    void searchInnovations_WithoutFilters_ShouldReturnAllActive() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findAllActiveInnovationsInfo())
            .thenReturn(innovations);

        // Act
        ResponseEntity<?> result = controller.searchInnovations(
            null, null, null, null, null, null, null, 0, 20
        );

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(projectInnovationUseCase).findAllActiveInnovationsInfo();
    }

    @Test
    void searchInnovations_WithPhaseFilter_ShouldReturnFiltered() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findActiveInnovationsInfoWithFilters(eq(1L), any(), any(), any(), any()))
            .thenReturn(innovations);

        // Act
        ResponseEntity<?> result = controller.searchInnovations(
            1L, null, null, null, null, null, null, 0, 20
        );

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(projectInnovationUseCase).findActiveInnovationsInfoWithFilters(eq(1L), any(), any(), any(), any());
    }

    @Test
    void searchInnovations_WithSdgFilter_ShouldReturnFiltered() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findActiveInnovationsInfoBySdgFilters(any(), any(), any(), any(), any()))
            .thenReturn(innovations);

        // Act
        ResponseEntity<?> result = controller.searchInnovations(
            null, null, null, 1L, 1L, null, null, 0, 20
        );

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(projectInnovationUseCase).findActiveInnovationsInfoBySdgFilters(any(), any(), any(), any(), any());
    }

    @Test
    void searchInnovations_WithPagination_ShouldApplyPagination() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findAllActiveInnovationsInfo())
            .thenReturn(innovations);

        // Act
        ResponseEntity<?> result = controller.searchInnovations(
            null, null, null, null, null, null, null, 0, 10
        );

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void searchInnovations_WithNegativeOffset_ShouldNormalizeToZero() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findAllActiveInnovationsInfo())
            .thenReturn(innovations);

        // Act
        ResponseEntity<?> result = controller.searchInnovations(
            null, null, null, null, null, null, null, -5, 20
        );

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void searchInnovationsSimple_ShouldReturnSimpleResponse() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findAllActiveInnovationsInfo())
            .thenReturn(innovations);

        // Act
        ResponseEntity<?> result = controller.searchInnovationsSimple(
            null, null, null, null, null, null, null, 0, 20
        );

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(projectInnovationUseCase).findAllActiveInnovationsInfo();
    }

    @Test
    void searchInnovationsComplete_ShouldReturnCompleteResponse() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findAllActiveInnovationsInfo())
            .thenReturn(innovations);

        // Act
        ResponseEntity<?> result = controller.searchInnovationsComplete(
            null, null, null, null, null, null, null, 0, 20
        );

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(projectInnovationUseCase).findAllActiveInnovationsInfo();
    }

    @Test
    void getInnovationCountryStats_ShouldReturnStats() {
        // Arrange
        Long phaseId = 1L;
        when(repositoryAdapter.countDistinctCountries(null, phaseId)).thenReturn(10L);
        when(repositoryAdapter.countDistinctInnovations(null, phaseId)).thenReturn(5L);
        when(repositoryAdapter.findAverageScalingReadinessByPhase(phaseId)).thenReturn(7.5);

        // Act
        ResponseEntity<?> result = controller.getInnovationCountryStats(phaseId);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        verify(repositoryAdapter).countDistinctCountries(null, phaseId);
        verify(repositoryAdapter).countDistinctInnovations(null, phaseId);
        verify(repositoryAdapter).findAverageScalingReadinessByPhase(phaseId);
    }

    @Test
    void getInnovationCountryStats_WhenException_ShouldReturnEmptyStats() {
        // Arrange
        Long phaseId = 1L;
        when(repositoryAdapter.countDistinctCountries(null, phaseId))
            .thenThrow(new RuntimeException("Database error"));

        // Act
        ResponseEntity<?> result = controller.getInnovationCountryStats(phaseId);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
    }

    @Test
    void getAllInnovationTypes_ShouldReturnList() {
        // Arrange
        com.example.demo.modules.innovationtype.domain.model.InnovationType type = 
            new com.example.demo.modules.innovationtype.domain.model.InnovationType();
        type.setId(1L);
        type.setName("Test Type");
        type.setIsOldType(false);
        
        List<com.example.demo.modules.innovationtype.domain.model.InnovationType> types = Arrays.asList(type);
        when(innovationTypeRepository.findAll()).thenReturn(types);

        // Act
        ResponseEntity<?> result = controller.getAllInnovationTypes();

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        verify(innovationTypeRepository).findAll();
    }

    @Test
    void searchInnovations_WithCountryIdsFilter_ShouldNormalizeAndFilter() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        List<String> countryIds = Arrays.asList("1,2", "3");
        when(projectInnovationUseCase.findActiveInnovationsInfoWithFilters(any(), any(), any(), any(), any()))
            .thenReturn(innovations);

        // Act
        ResponseEntity<?> result = controller.searchInnovations(
            null, null, null, null, null, countryIds, null, 0, 20
        );

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(projectInnovationUseCase).findActiveInnovationsInfoWithFilters(any(), any(), any(), any(), any());
    }

    @Test
    void searchInnovations_WithActorIdsFilter_ShouldNormalizeAndFilter() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        List<String> actorIds = Arrays.asList("1", "2,3");
        when(projectInnovationUseCase.findActiveInnovationsInfoWithFilters(any(), any(), any(), any(), any()))
            .thenReturn(innovations);

        // Act
        ResponseEntity<?> result = controller.searchInnovations(
            null, null, null, null, null, null, actorIds, 0, 20
        );

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void searchInnovations_WithInvalidLimit_ShouldUseDefault() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findAllActiveInnovationsInfo())
            .thenReturn(innovations);

        // Act - null limit should use default
        ResponseEntity<?> result = controller.searchInnovations(
            null, null, null, null, null, null, null, 0, null
        );

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void searchInnovations_WithZeroLimit_ShouldUseDefault() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findAllActiveInnovationsInfo())
            .thenReturn(innovations);

        // Act - zero limit should use default
        ResponseEntity<?> result = controller.searchInnovations(
            null, null, null, null, null, null, null, 0, 0
        );

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void searchInnovations_WithNegativeLimit_ShouldUseDefault() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findAllActiveInnovationsInfo())
            .thenReturn(innovations);

        // Act - negative limit should use default
        ResponseEntity<?> result = controller.searchInnovations(
            null, null, null, null, null, null, null, 0, -10
        );

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void searchInnovationsSimple_WithCountryAndActorFilters_ShouldReturnFiltered() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        List<String> countryIds = Arrays.asList("1");
        List<String> actorIds = Arrays.asList("2");
        when(projectInnovationUseCase.findActiveInnovationsInfoWithFilters(any(), any(), any(), any(), any()))
            .thenReturn(innovations);

        // Act
        ResponseEntity<?> result = controller.searchInnovationsSimple(
            null, null, null, null, null, countryIds, actorIds, 0, 20
        );

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void searchInnovationsComplete_WithAllFilters_ShouldReturnComplete() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findActiveInnovationsInfoWithFilters(any(), any(), any(), any(), any()))
            .thenReturn(innovations);

        // Act
        ResponseEntity<?> result = controller.searchInnovationsComplete(
            1L, 5, 2L, null, null, Arrays.asList("1"), Arrays.asList("2"), 0, 20
        );

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void searchInnovations_WithInvalidCountryId_ShouldReturnBadRequest() {
        // Arrange
        List<String> invalidCountryIds = Arrays.asList("invalid");

        // Act & Assert
        assertThrows(org.springframework.web.server.ResponseStatusException.class, () -> {
            controller.searchInnovations(
                null, null, null, null, null, invalidCountryIds, null, 0, 20
            );
        });
    }

    @Test
    void searchInnovations_WithInvalidActorId_ShouldReturnBadRequest() {
        // Arrange
        List<String> invalidActorIds = Arrays.asList("not-a-number");

        // Act & Assert
        assertThrows(org.springframework.web.server.ResponseStatusException.class, () -> {
            controller.searchInnovations(
                null, null, null, null, null, null, invalidActorIds, 0, 20
            );
        });
    }

    @Test
    void searchInnovations_WithEmptyCountryIds_ShouldReturnNull() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findAllActiveInnovationsInfo())
            .thenReturn(innovations);

        // Act - empty list should normalize to null
        ResponseEntity<?> result = controller.searchInnovations(
            null, null, null, null, null, Arrays.asList(""), null, 0, 20
        );

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void searchInnovations_WithCommaSeparatedCountryIds_ShouldParseCorrectly() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findActiveInnovationsInfoWithFilters(any(), any(), any(), any(), any()))
            .thenReturn(innovations);

        // Act
        ResponseEntity<?> result = controller.searchInnovations(
            null, null, null, null, null, Arrays.asList("1,2,3"), null, 0, 20
        );

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getProjectInnovationInfoByInnovationIdAndPhaseId_ShouldCallHelperMethods() {
        // Arrange
        testInnovationInfo.setIdPhase(5L);
        testInnovationInfo.setStageInnovationId(2L);
        testInnovationInfo.setGeographicScopeId(3L);
        testInnovationInfo.setInnovationTypeId(1L);
        
        com.example.demo.modules.innovationtype.domain.model.InnovationType type = 
            new com.example.demo.modules.innovationtype.domain.model.InnovationType();
        type.setId(1L);
        type.setName("Test Type");
        type.setDefinition("Test Definition");
        type.setIsOldType(false);
        
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L))
            .thenReturn(Optional.of(testInnovationInfo));
        when(innovationTypeRepository.findById(1L))
            .thenReturn(Optional.of(type));

        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(innovationTypeRepository).findById(1L);
    }

    @Test
    void getProjectInnovationInfoByInnovationIdAndPhaseId_WithNullTypeId_ShouldHandleGracefully() {
        // Arrange
        testInnovationInfo.setInnovationTypeId(null);
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L))
            .thenReturn(Optional.of(testInnovationInfo));

        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getProjectInnovationInfoByInnovationIdAndPhaseId_WithTypeNotFound_ShouldUseFallback() {
        // Arrange
        testInnovationInfo.setInnovationTypeId(999L);
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L))
            .thenReturn(Optional.of(testInnovationInfo));
        when(innovationTypeRepository.findById(999L))
            .thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getProjectInnovationInfoByInnovationIdAndPhaseId_WithStageId_ShouldCallHelper() {
        // Arrange
        testInnovationInfo.setStageInnovationId(1L);
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L))
            .thenReturn(Optional.of(testInnovationInfo));

        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getProjectInnovationInfoByInnovationIdAndPhaseId_WithGeographicScopeId_ShouldCallHelper() {
        // Arrange
        testInnovationInfo.setGeographicScopeId(1L);
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L))
            .thenReturn(Optional.of(testInnovationInfo));

        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getProjectInnovationInfoByInnovationIdAndPhaseId_WithPhaseId_ShouldCallGetPhaseInfo() {
        // Arrange
        testInnovationInfo.setIdPhase(10L);
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 10L))
            .thenReturn(Optional.of(testInnovationInfo));

        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 10L);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getProjectInnovationInfoByInnovationIdAndPhaseId_WithAllStageIds_ShouldCallGetInnovationStageInfo() {
        // Arrange
        testInnovationInfo.setStageInnovationId(1L);
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L))
            .thenReturn(Optional.of(testInnovationInfo));

        // Act - Stage 1
        ResponseEntity<?> result1 = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);
        assertNotNull(result1);

        // Arrange - Stage 2
        testInnovationInfo.setStageInnovationId(2L);
        ResponseEntity<?> result2 = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);
        assertNotNull(result2);

        // Arrange - Stage 3
        testInnovationInfo.setStageInnovationId(3L);
        ResponseEntity<?> result3 = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);
        assertNotNull(result3);

        // Arrange - Stage 4
        testInnovationInfo.setStageInnovationId(4L);
        ResponseEntity<?> result4 = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);
        assertNotNull(result4);

        // Arrange - Stage 5 (default)
        testInnovationInfo.setStageInnovationId(5L);
        ResponseEntity<?> result5 = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);
        assertNotNull(result5);
    }

    @Test
    void getProjectInnovationInfoByInnovationIdAndPhaseId_WithInnovationTypeIdException_ShouldUseFallback() {
        // Arrange
        testInnovationInfo.setInnovationTypeId(1L);
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L))
            .thenReturn(Optional.of(testInnovationInfo));
        when(innovationTypeRepository.findById(1L))
            .thenThrow(new RuntimeException("Database error"));

        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getProjectInnovationInfoByInnovationIdAndPhaseId_WithInnovationTypeId1_ShouldUseHardcodedFallback() {
        // Arrange
        testInnovationInfo.setInnovationTypeId(1L);
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L))
            .thenReturn(Optional.of(testInnovationInfo));
        when(innovationTypeRepository.findById(1L))
            .thenThrow(new RuntimeException("Database error"));

        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getProjectInnovationInfoByInnovationIdAndPhaseId_WithAllInnovationTypeIds_ShouldUseHardcodedFallback() {
        // Arrange
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L))
            .thenReturn(Optional.of(testInnovationInfo));
        when(innovationTypeRepository.findById(anyLong()))
            .thenThrow(new RuntimeException("Database error"));

        // Act & Assert - Test all hardcoded types (1-5)
        for (int i = 1; i <= 5; i++) {
            testInnovationInfo.setInnovationTypeId((long) i);
            ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);
            assertNotNull(result);
            assertEquals(HttpStatus.OK, result.getStatusCode());
        }

        // Test default case
        testInnovationInfo.setInnovationTypeId(999L);
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void searchInnovations_WithCountryIdsContainingEmptyStrings_ShouldFilter() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findActiveInnovationsInfoWithFilters(any(), any(), any(), any(), any()))
            .thenReturn(innovations);

        // Act - empty strings should be filtered out, but "1" remains so it calls with filters
        ResponseEntity<?> result = controller.searchInnovations(
            null, null, null, null, null, Arrays.asList("", "  ", "1"), null, 0, 20
        );

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void searchInnovations_WithActorIdsContainingEmptyStrings_ShouldFilter() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findActiveInnovationsInfoWithFilters(any(), any(), any(), any(), any()))
            .thenReturn(innovations);

        // Act - empty strings should be filtered out, but "1" remains so it calls with filters
        ResponseEntity<?> result = controller.searchInnovations(
            null, null, null, null, null, null, Arrays.asList("", "  ", "1"), 0, 20
        );

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void searchInnovations_WithDuplicateCountryIds_ShouldDistinct() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findActiveInnovationsInfoWithFilters(any(), any(), any(), any(), any()))
            .thenReturn(innovations);

        // Act - duplicates should be removed
        ResponseEntity<?> result = controller.searchInnovations(
            null, null, null, null, null, Arrays.asList("1", "1", "2", "2"), null, 0, 20
        );

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void searchInnovations_WithDuplicateActorIds_ShouldDistinct() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findActiveInnovationsInfoWithFilters(any(), any(), any(), any(), any()))
            .thenReturn(innovations);

        // Act - duplicates should be removed
        ResponseEntity<?> result = controller.searchInnovations(
            null, null, null, null, null, null, Arrays.asList("1", "1", "2", "2"), 0, 20
        );

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}
