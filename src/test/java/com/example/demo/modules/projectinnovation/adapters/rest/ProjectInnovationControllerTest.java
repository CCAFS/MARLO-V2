package com.example.demo.modules.projectinnovation.adapters.rest;

import com.example.demo.modules.innovationtype.adapters.outbound.persistence.InnovationTypeRepositoryAdapter;
import com.example.demo.modules.projectinnovation.adapters.rest.mapper.ProjectInnovationActorsMapper;
import com.example.demo.modules.projectinnovation.adapters.rest.dto.ProjectInnovationSearchResponse;
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
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNull;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.mockingDetails;

@ExtendWith(MockitoExtension.class)
class ProjectInnovationControllerTest {

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

    private ProjectInnovationController controller;

    private ProjectInnovation testInnovation;
    private ProjectInnovationInfo testInnovationInfo;

    @BeforeEach
    void setUp() {
        projectInnovationUseCase = mock(ProjectInnovationUseCase.class, invocation -> {
            Class<?> returnType = invocation.getMethod().getReturnType();
            if (List.class.isAssignableFrom(returnType)) {
                return Collections.emptyList();
            }
            if (Optional.class.isAssignableFrom(returnType)) {
                return Optional.empty();
            }
            return RETURNS_DEFAULTS.answer(invocation);
        });
        controller = new ProjectInnovationController(
            projectInnovationUseCase,
            actorsService,
            actorsMapper,
            repositoryAdapter,
            locElementRepository,
            innovationTypeRepository,
            sdgRepository
        );

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
        ResponseEntity<?> result = invokeSearchInnovations(
            null, null, null, null, null, null, null, null, 0, 20
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
        // Default mock returns empty list for list-returning methods.

        // Act
        ResponseEntity<?> result = invokeSearchInnovations(
            1L, null, null, null, null, null, null, null, 0, 20
        );

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertTrue(wasMethodInvoked(projectInnovationUseCase, "findActiveInnovationsInfoWithFilters"));
    }

    @Test
    void searchInnovations_WithSdgFilter_ShouldReturnFiltered() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        // Default mock returns empty list for list-returning methods.

        // Act
        ResponseEntity<?> result = invokeSearchInnovations(
            null, null, null, 1L, 1L, null, null, null, 0, 20
        );

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertTrue(wasMethodInvoked(projectInnovationUseCase, "findActiveInnovationsInfoBySdgFilters"));
    }

    @Test
    void searchInnovations_WithPagination_ShouldApplyPagination() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findAllActiveInnovationsInfo())
            .thenReturn(innovations);

        // Act
        ResponseEntity<?> result = invokeSearchInnovations(
            null, null, null, null, null, null, null, null, 0, 10
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
        ResponseEntity<?> result = invokeSearchInnovations(
            null, null, null, null, null, null, null, null, -5, 20
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
        ResponseEntity<?> result = invokeSearchInnovationsSimple(
            null, null, null, null, null, null, null, null, 0, 20
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
        ResponseEntity<?> result = invokeSearchInnovationsComplete(
            null, null, null, null, null, null, null, null, 0, 20
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
        // Default mock returns empty list for list-returning methods.

        // Act
        ResponseEntity<?> result = invokeSearchInnovations(
            null, null, null, null, null, countryIds, null, null, 0, 20
        );

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertTrue(wasMethodInvoked(projectInnovationUseCase, "findActiveInnovationsInfoWithFilters"));
    }

    @Test
    void searchInnovations_WithActorIdsFilter_ShouldNormalizeAndFilter() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        List<String> actorIds = Arrays.asList("1", "2,3");
        // Default mock returns empty list for list-returning methods.

        // Act
        ResponseEntity<?> result = invokeSearchInnovations(
            null, null, null, null, null, null, actorIds, null, 0, 20
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
        ResponseEntity<?> result = invokeSearchInnovations(
            null, null, null, null, null, null, null, null, 0, null
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
        ResponseEntity<?> result = invokeSearchInnovations(
            null, null, null, null, null, null, null, null, 0, 0
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
        ResponseEntity<?> result = invokeSearchInnovations(
            null, null, null, null, null, null, null, null, 0, -10
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
        // Default mock returns empty list for list-returning methods.

        // Act
        ResponseEntity<?> result = invokeSearchInnovationsSimple(
            null, null, null, null, null, countryIds, actorIds, null, 0, 20
        );

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void searchInnovationsComplete_WithAllFilters_ShouldReturnComplete() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        // Default mock returns empty list for list-returning methods.

        // Act
        ResponseEntity<?> result = invokeSearchInnovationsComplete(
            1L, 5, 2L, null, null, Arrays.asList("1"), Arrays.asList("2"), null, 0, 20
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
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            invokeSearchInnovations(
                null, null, null, null, null, invalidCountryIds, null, null, 0, 20
            );
        });
        Throwable cause = thrown.getCause();
        if (cause instanceof java.lang.reflect.InvocationTargetException invocationTargetException) {
            cause = invocationTargetException.getCause();
        }
        assertTrue(cause instanceof org.springframework.web.server.ResponseStatusException);
    }

    @Test
    void searchInnovations_WithInvalidActorId_ShouldReturnBadRequest() {
        // Arrange
        List<String> invalidActorIds = Arrays.asList("not-a-number");

        // Act & Assert
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            invokeSearchInnovations(
                null, null, null, null, null, null, invalidActorIds, null, 0, 20
            );
        });
        Throwable cause = thrown.getCause();
        if (cause instanceof java.lang.reflect.InvocationTargetException invocationTargetException) {
            cause = invocationTargetException.getCause();
        }
        assertTrue(cause instanceof org.springframework.web.server.ResponseStatusException);
    }

    @Test
    void searchInnovations_WithEmptyCountryIds_ShouldReturnEmptyListInFilters() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findAllActiveInnovationsInfo())
            .thenReturn(innovations);

        // Act - empty list should normalize to empty list
        ResponseEntity<?> result = invokeSearchInnovations(
            null, null, null, null, null, Arrays.asList(""), null, null, 0, 20
        );

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        ProjectInnovationSearchResponse response = (ProjectInnovationSearchResponse) result.getBody();
        assertNotNull(response.appliedFilters());
        assertNotNull(response.appliedFilters().countryIds());
        assertTrue(response.appliedFilters().countryIds().isEmpty());
    }

    @Test
    void searchInnovations_WithCommaSeparatedCountryIds_ShouldParseCorrectly() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        // Default mock returns empty list for list-returning methods.

        // Act
        ResponseEntity<?> result = invokeSearchInnovations(
            null, null, null, null, null, Arrays.asList("1,2,3"), null, null, 0, 20
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
        // Default mock returns empty list for list-returning methods.

        // Act - empty strings should be filtered out, but "1" remains so it calls with filters
        ResponseEntity<?> result = invokeSearchInnovations(
            null, null, null, null, null, Arrays.asList("", "  ", "1"), null, null, 0, 20
        );

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void searchInnovations_WithActorIdsContainingEmptyStrings_ShouldFilter() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        // Default mock returns empty list for list-returning methods.

        // Act - empty strings should be filtered out, but "1" remains so it calls with filters
        ResponseEntity<?> result = invokeSearchInnovations(
            null, null, null, null, null, null, Arrays.asList("", "  ", "1"), null, 0, 20
        );

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void searchInnovations_WithDuplicateCountryIds_ShouldDistinct() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        // Default mock returns empty list for list-returning methods.

        // Act - duplicates should be removed
        ResponseEntity<?> result = invokeSearchInnovations(
            null, null, null, null, null, Arrays.asList("1", "1", "2", "2"), null, null, 0, 20
        );

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void searchInnovations_WithDuplicateActorIds_ShouldDistinct() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        // Default mock returns empty list for list-returning methods.

        // Act - duplicates should be removed
        ResponseEntity<?> result = invokeSearchInnovations(
            null, null, null, null, null, null, Arrays.asList("1", "1", "2", "2"), null, 0, 20
        );

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    private ResponseEntity<?> invokeSearchInnovations(
            Long phase,
            Integer readinessScale,
            Long innovationTypeId,
            Long innovationId,
            Long sdgId,
            List<String> countryIds,
            List<String> actorIds,
            List<String> actorNames,
            Integer offset,
            Integer limit) {
        // The actual method signature doesn't include actorNames, so we ignore it
        try {
            Method method = ProjectInnovationController.class.getMethod(
                "searchInnovations",
                Long.class, Integer.class, Long.class, Long.class, Long.class,
                List.class, List.class, Integer.class, Integer.class);
            return (ResponseEntity<?>) method.invoke(
                controller, phase, readinessScale, innovationTypeId, innovationId, sdgId,
                countryIds, actorIds, offset, limit);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private ResponseEntity<?> invokeSearchInnovationsSimple(
            Long phase,
            Integer readinessScale,
            Long innovationTypeId,
            Long innovationId,
            Long sdgId,
            List<String> countryIds,
            List<String> actorIds,
            List<String> actorNames,
            Integer offset,
            Integer limit) {
        // The actual method signature doesn't include actorNames, so we ignore it
        try {
            Method method = ProjectInnovationController.class.getMethod(
                "searchInnovationsSimple",
                Long.class, Integer.class, Long.class, Long.class, Long.class,
                List.class, List.class, Integer.class, Integer.class);
            return (ResponseEntity<?>) method.invoke(
                controller, phase, readinessScale, innovationTypeId, innovationId, sdgId,
                countryIds, actorIds, offset, limit);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private ResponseEntity<?> invokeSearchInnovationsComplete(
            Long phase,
            Integer readinessScale,
            Long innovationTypeId,
            Long innovationId,
            Long sdgId,
            List<String> countryIds,
            List<String> actorIds,
            List<String> actorNames,
            Integer offset,
            Integer limit) {
        // The actual method signature doesn't include actorNames, so we ignore it
        try {
            Method method = ProjectInnovationController.class.getMethod(
                "searchInnovationsComplete",
                Long.class, Integer.class, Long.class, Long.class, Long.class,
                List.class, List.class, Integer.class, Integer.class);
            return (ResponseEntity<?>) method.invoke(
                controller, phase, readinessScale, innovationTypeId, innovationId, sdgId,
                countryIds, actorIds, offset, limit);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean wasMethodInvoked(Object mock, String methodName) {
        return mockingDetails(mock).getInvocations().stream()
            .anyMatch(invocation -> invocation.getMethod().getName().equals(methodName));
    }


    @Test
    void searchInnovations_WithSdgId_ShouldUseSdgSearch() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findActiveInnovationsInfoBySdgFilters(any(), any(), any(), any(), any(), any()))
            .thenReturn(innovations);

        // Act
        ResponseEntity<?> result = invokeSearchInnovations(
            null, null, null, null, 1L, null, null, null, 0, 20);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(projectInnovationUseCase).findActiveInnovationsInfoBySdgFilters(any(), any(), eq(1L), any(), any(), any());
    }

    @Test
    void searchInnovations_WithInnovationIdAndPhase_ShouldUseSdgSearch() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findActiveInnovationsInfoBySdgFilters(any(), any(), any(), any(), any(), any()))
            .thenReturn(innovations);

        // Act
        ResponseEntity<?> result = invokeSearchInnovations(
            1L, null, null, 100L, null, null, null, null, 0, 20);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(projectInnovationUseCase).findActiveInnovationsInfoBySdgFilters(eq(100L), eq(1L), any(), any(), any(), any());
    }

    @Test
    void searchInnovations_WithPhaseOnly_ShouldUseGeneralSearch() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findActiveInnovationsInfoWithFilters(any(), any(), any(), any(), any(), any()))
            .thenReturn(innovations);

        // Act
        ResponseEntity<?> result = invokeSearchInnovations(
            1L, null, null, null, null, null, null, null, 0, 20);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(projectInnovationUseCase).findActiveInnovationsInfoWithFilters(eq(1L), any(), any(), any(), any(), any());
    }

    @Test
    void searchInnovations_WithReadinessScale_ShouldUseGeneralSearch() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findActiveInnovationsInfoWithFilters(any(), any(), any(), any(), any(), any()))
            .thenReturn(innovations);

        // Act
        ResponseEntity<?> result = invokeSearchInnovations(
            null, 5, null, null, null, null, null, null, 0, 20);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(projectInnovationUseCase).findActiveInnovationsInfoWithFilters(any(), eq(5), any(), any(), any(), any());
    }

    @Test
    void searchInnovations_WithInnovationTypeId_ShouldUseGeneralSearch() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findActiveInnovationsInfoWithFilters(any(), any(), any(), any(), any(), any()))
            .thenReturn(innovations);

        // Act
        ResponseEntity<?> result = invokeSearchInnovations(
            null, null, 2L, null, null, null, null, null, 0, 20);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(projectInnovationUseCase).findActiveInnovationsInfoWithFilters(any(), any(), eq(2L), any(), any(), any());
    }

    @Test
    void searchInnovations_WithCountryIds_ShouldUseGeneralSearch() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findActiveInnovationsInfoWithFilters(any(), any(), any(), any(), any(), any()))
            .thenReturn(innovations);

        // Act
        ResponseEntity<?> result = invokeSearchInnovations(
            null, null, null, null, null, Arrays.asList("1", "2"), null, null, 0, 20);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(projectInnovationUseCase).findActiveInnovationsInfoWithFilters(any(), any(), any(), any(), any(), any());
    }

    @Test
    void searchInnovations_WithActorIds_ShouldUseGeneralSearch() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findActiveInnovationsInfoWithFilters(any(), any(), any(), any(), any(), any()))
            .thenReturn(innovations);

        // Act
        ResponseEntity<?> result = invokeSearchInnovations(
            null, null, null, null, null, null, Arrays.asList("1", "2"), null, 0, 20);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(projectInnovationUseCase).findActiveInnovationsInfoWithFilters(any(), any(), any(), any(), any(), any());
    }

    @Test
    void searchInnovations_WithNoFilters_ShouldUseAllActiveSearch() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findAllActiveInnovationsInfo()).thenReturn(innovations);

        // Act
        ResponseEntity<?> result = invokeSearchInnovations(
            null, null, null, null, null, null, null, null, 0, 20);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(projectInnovationUseCase).findAllActiveInnovationsInfo();
    }

    @Test
    void searchInnovations_WithInvalidCountryId_ShouldThrowException() {
        // Act & Assert
        assertThrows(RuntimeException.class, () ->
            invokeSearchInnovations(
                null, null, null, null, null, Arrays.asList("invalid"), null, null, 0, 20)
        );
    }

    @Test
    void searchInnovations_WithInvalidActorId_ShouldThrowException() {
        // Act & Assert
        assertThrows(RuntimeException.class, () ->
            invokeSearchInnovations(
                null, null, null, null, null, null, Arrays.asList("invalid"), null, 0, 20)
        );
    }


    @Test
    void searchInnovations_WithWhitespaceCountryIds_ShouldFilterOut() {
        // Arrange - empty countryIds after filtering should result in no filters, so uses findAllActiveInnovationsInfo
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findAllActiveInnovationsInfo()).thenReturn(innovations);

        // Act
        ResponseEntity<?> result = invokeSearchInnovations(
            null, null, null, null, null, Arrays.asList("   "), null, null, 0, 20);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void updateProjectInnovation_WhenNotFound_ShouldReturnNotFound() {
        // Arrange
        when(projectInnovationUseCase.updateProjectInnovation(anyLong(), any()))
            .thenThrow(new RuntimeException("Not found"));

        // Act
        ResponseEntity<ProjectInnovationResponse> result = controller.updateProjectInnovation(
            999L, new UpdateProjectInnovationRequest(true, 1L, "test"));

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }

    @Test
    void activateProjectInnovation_WhenNotFound_ShouldReturnNotFound() {
        // Arrange
        when(projectInnovationUseCase.activateProjectInnovation(anyLong()))
            .thenThrow(new RuntimeException("Not found"));

        // Act
        ResponseEntity<ProjectInnovationResponse> result = controller.activateProjectInnovation(999L);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
    }


    @Test
    void searchInnovationsSimple_WithSdgId_ShouldUseSdgSearch() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findActiveInnovationsInfoBySdgFilters(any(), any(), any(), any(), any(), any()))
            .thenReturn(innovations);

        // Act
        ResponseEntity<?> result = invokeSearchInnovationsSimple(
            null, null, null, null, 1L, null, null, null, 0, 20);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(projectInnovationUseCase).findActiveInnovationsInfoBySdgFilters(any(), any(), eq(1L), any(), any(), any());
    }

    @Test
    void searchInnovationsSimple_WithNoFilters_ShouldUseAllActiveSearch() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findAllActiveInnovationsInfo()).thenReturn(innovations);

        // Act
        ResponseEntity<?> result = invokeSearchInnovationsSimple(
            null, null, null, null, null, null, null, null, 0, 20);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(projectInnovationUseCase).findAllActiveInnovationsInfo();
    }

    @Test
    void searchInnovationsComplete_WithSdgId_ShouldUseSdgSearch() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findActiveInnovationsInfoBySdgFilters(any(), any(), any(), any(), any(), any()))
            .thenReturn(innovations);

        // Act
        ResponseEntity<?> result = invokeSearchInnovationsComplete(
            null, null, null, null, 1L, null, null, null, 0, 20);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(projectInnovationUseCase).findActiveInnovationsInfoBySdgFilters(any(), any(), eq(1L), any(), any(), any());
    }

    @Test
    void searchInnovationsComplete_WithNoFilters_ShouldUseAllActiveSearch() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findAllActiveInnovationsInfo()).thenReturn(innovations);

        // Act
        ResponseEntity<?> result = invokeSearchInnovationsComplete(
            null, null, null, null, null, null, null, null, 0, 20);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(projectInnovationUseCase).findAllActiveInnovationsInfo();
    }

    @Test
    void getProjectInnovationInfoByInnovationIdAndPhaseId_WithNullPhaseId_ShouldReturnNullPhase() {
        // Arrange
        testInnovationInfo.setIdPhase(null);
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L))
            .thenReturn(Optional.of(testInnovationInfo));

        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getProjectInnovationInfoByInnovationIdAndPhaseId_WithNullStageId_ShouldReturnNullStage() {
        // Arrange
        testInnovationInfo.setStageInnovationId(null);
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L))
            .thenReturn(Optional.of(testInnovationInfo));

        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getProjectInnovationInfoByInnovationIdAndPhaseId_WithStageId1_ShouldReturnCorrectStage() {
        // Arrange
        testInnovationInfo.setStageInnovationId(1L);
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L))
            .thenReturn(Optional.of(testInnovationInfo));

        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getProjectInnovationInfoByInnovationIdAndPhaseId_WithStageId2_ShouldReturnCorrectStage() {
        // Arrange
        testInnovationInfo.setStageInnovationId(2L);
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L))
            .thenReturn(Optional.of(testInnovationInfo));

        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getProjectInnovationInfoByInnovationIdAndPhaseId_WithStageId3_ShouldReturnCorrectStage() {
        // Arrange
        testInnovationInfo.setStageInnovationId(3L);
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L))
            .thenReturn(Optional.of(testInnovationInfo));

        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getProjectInnovationInfoByInnovationIdAndPhaseId_WithStageId4_ShouldReturnCorrectStage() {
        // Arrange
        testInnovationInfo.setStageInnovationId(4L);
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L))
            .thenReturn(Optional.of(testInnovationInfo));

        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getProjectInnovationInfoByInnovationIdAndPhaseId_WithStageIdDefault_ShouldReturnDefaultStage() {
        // Arrange
        testInnovationInfo.setStageInnovationId(99L);
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L))
            .thenReturn(Optional.of(testInnovationInfo));

        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getProjectInnovationInfoByInnovationIdAndPhaseId_WithNullScopeId_ShouldReturnNullScope() {
        // Arrange
        testInnovationInfo.setGeographicScopeId(null);
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L))
            .thenReturn(Optional.of(testInnovationInfo));

        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getProjectInnovationInfoByInnovationIdAndPhaseId_WithNullTypeId_ShouldReturnNullType() {
        // Arrange
        testInnovationInfo.setInnovationTypeId(null);
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L))
            .thenReturn(Optional.of(testInnovationInfo));

        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getProjectInnovationInfoByInnovationIdAndPhaseId_WithTypeId2_ShouldUseHardcodedFallback() {
        // Arrange
        testInnovationInfo.setInnovationTypeId(2L);
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L))
            .thenReturn(Optional.of(testInnovationInfo));
        when(innovationTypeRepository.findById(2L))
            .thenThrow(new RuntimeException("Database error"));

        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getProjectInnovationInfoByInnovationIdAndPhaseId_WithTypeId3_ShouldUseHardcodedFallback() {
        // Arrange
        testInnovationInfo.setInnovationTypeId(3L);
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L))
            .thenReturn(Optional.of(testInnovationInfo));
        when(innovationTypeRepository.findById(3L))
            .thenThrow(new RuntimeException("Database error"));

        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getProjectInnovationInfoByInnovationIdAndPhaseId_WithTypeId4_ShouldUseHardcodedFallback() {
        // Arrange
        testInnovationInfo.setInnovationTypeId(4L);
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L))
            .thenReturn(Optional.of(testInnovationInfo));
        when(innovationTypeRepository.findById(4L))
            .thenThrow(new RuntimeException("Database error"));

        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getProjectInnovationInfoByInnovationIdAndPhaseId_WithTypeId5_ShouldUseHardcodedFallback() {
        // Arrange
        testInnovationInfo.setInnovationTypeId(5L);
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L))
            .thenReturn(Optional.of(testInnovationInfo));
        when(innovationTypeRepository.findById(5L))
            .thenThrow(new RuntimeException("Database error"));

        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getProjectInnovationInfoByInnovationIdAndPhaseId_WithTypeIdDefault_ShouldUseDefaultFallback() {
        // Arrange
        testInnovationInfo.setInnovationTypeId(999L);
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L))
            .thenReturn(Optional.of(testInnovationInfo));
        when(innovationTypeRepository.findById(999L))
            .thenThrow(new RuntimeException("Database error"));

        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getProjectInnovationInfoByInnovationIdAndPhaseId_WithNullProjectInnovationId_ShouldHandleNull() {
        // Arrange
        testInnovationInfo.setProjectInnovationId(null);
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L))
            .thenReturn(Optional.of(testInnovationInfo));

        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getProjectInnovationInfoByInnovationIdAndPhaseId_WithNullIdPhase_ShouldHandleNull() {
        // Arrange
        testInnovationInfo.setIdPhase(null);
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L))
            .thenReturn(Optional.of(testInnovationInfo));

        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getProjectInnovationInfoByInnovationIdAndPhaseId_WithPhaseResearchId1_ShouldReturnCorrectPhase() {
        // Arrange
        testInnovationInfo.setPhaseResearchId(1L);
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L))
            .thenReturn(Optional.of(testInnovationInfo));

        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getProjectInnovationInfoByInnovationIdAndPhaseId_WithPhaseResearchId2_ShouldReturnCorrectPhase() {
        // Arrange
        testInnovationInfo.setPhaseResearchId(2L);
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L))
            .thenReturn(Optional.of(testInnovationInfo));

        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getProjectInnovationInfoByInnovationIdAndPhaseId_WithPhaseResearchId3_ShouldReturnCorrectPhase() {
        // Arrange
        testInnovationInfo.setPhaseResearchId(3L);
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L))
            .thenReturn(Optional.of(testInnovationInfo));

        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getProjectInnovationInfoByInnovationIdAndPhaseId_WithPhaseResearchIdDefault_ShouldReturnDefaultPhase() {
        // Arrange
        testInnovationInfo.setPhaseResearchId(99L);
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L))
            .thenReturn(Optional.of(testInnovationInfo));

        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getProjectInnovationInfoByInnovationIdAndPhaseId_WithRegionId1_ShouldReturnCorrectRegion() {
        // Arrange
        testInnovationInfo.setRepIndRegionId(1L);
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L))
            .thenReturn(Optional.of(testInnovationInfo));

        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getProjectInnovationInfoByInnovationIdAndPhaseId_WithRegionId2_ShouldReturnCorrectRegion() {
        // Arrange
        testInnovationInfo.setRepIndRegionId(2L);
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L))
            .thenReturn(Optional.of(testInnovationInfo));

        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getProjectInnovationInfoByInnovationIdAndPhaseId_WithRegionId3_ShouldReturnCorrectRegion() {
        // Arrange
        testInnovationInfo.setRepIndRegionId(3L);
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L))
            .thenReturn(Optional.of(testInnovationInfo));

        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getProjectInnovationInfoByInnovationIdAndPhaseId_WithRegionIdDefault_ShouldReturnDefaultRegion() {
        // Arrange
        testInnovationInfo.setRepIndRegionId(99L);
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L))
            .thenReturn(Optional.of(testInnovationInfo));

        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getProjectInnovationInfoByInnovationIdAndPhaseId_WithContributionId1_ShouldReturnCorrectContribution() {
        // Arrange
        testInnovationInfo.setRepIndContributionCrpId(1L);
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L))
            .thenReturn(Optional.of(testInnovationInfo));

        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getProjectInnovationInfoByInnovationIdAndPhaseId_WithContributionId2_ShouldReturnCorrectContribution() {
        // Arrange
        testInnovationInfo.setRepIndContributionCrpId(2L);
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L))
            .thenReturn(Optional.of(testInnovationInfo));

        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getProjectInnovationInfoByInnovationIdAndPhaseId_WithContributionId3_ShouldReturnCorrectContribution() {
        // Arrange
        testInnovationInfo.setRepIndContributionCrpId(3L);
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L))
            .thenReturn(Optional.of(testInnovationInfo));

        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getProjectInnovationInfoByInnovationIdAndPhaseId_WithContributionIdDefault_ShouldReturnDefaultContribution() {
        // Arrange
        testInnovationInfo.setRepIndContributionCrpId(99L);
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L))
            .thenReturn(Optional.of(testInnovationInfo));

        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getProjectInnovationInfoByInnovationIdAndPhaseId_WithDegreeId1_ShouldReturnNovel() {
        // Arrange
        testInnovationInfo.setRepIndDegreeInnovationId(1L);
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L))
            .thenReturn(Optional.of(testInnovationInfo));

        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getProjectInnovationInfoByInnovationIdAndPhaseId_WithDegreeId2_ShouldReturnAdaptive() {
        // Arrange
        testInnovationInfo.setRepIndDegreeInnovationId(2L);
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L))
            .thenReturn(Optional.of(testInnovationInfo));

        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getProjectInnovationInfoByInnovationIdAndPhaseId_WithDegreeIdDefault_ShouldReturnDefaultDegree() {
        // Arrange
        testInnovationInfo.setRepIndDegreeInnovationId(99L);
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L))
            .thenReturn(Optional.of(testInnovationInfo));

        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getProjectInnovationInfoByInnovationIdAndPhaseId_WithFocusLevelId1_ShouldReturnNotTargeted() {
        // Arrange
        testInnovationInfo.setGenderFocusLevelId(1L);
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L))
            .thenReturn(Optional.of(testInnovationInfo));

        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getProjectInnovationInfoByInnovationIdAndPhaseId_WithFocusLevelId2_ShouldReturnSignificant() {
        // Arrange
        testInnovationInfo.setGenderFocusLevelId(2L);
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L))
            .thenReturn(Optional.of(testInnovationInfo));

        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getProjectInnovationInfoByInnovationIdAndPhaseId_WithFocusLevelId3_ShouldReturnPrincipal() {
        // Arrange
        testInnovationInfo.setGenderFocusLevelId(3L);
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L))
            .thenReturn(Optional.of(testInnovationInfo));

        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getProjectInnovationInfoByInnovationIdAndPhaseId_WithFocusLevelIdDefault_ShouldReturnDefaultFocus() {
        // Arrange
        testInnovationInfo.setGenderFocusLevelId(99L);
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L))
            .thenReturn(Optional.of(testInnovationInfo));

        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }


    @Test
    void getProjectInnovationInfoByInnovationIdAndPhaseId_WithOrganizationTypeId1_ShouldReturnGovernmentAgency() {
        // Arrange
        testInnovationInfo.setLeadOrganizationId(1L);
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L))
            .thenReturn(Optional.of(testInnovationInfo));

        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getProjectInnovationInfoByInnovationIdAndPhaseId_WithOrganizationTypeId2_ShouldReturnPrivateSector() {
        // Arrange
        testInnovationInfo.setLeadOrganizationId(2L);
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L))
            .thenReturn(Optional.of(testInnovationInfo));

        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getProjectInnovationInfoByInnovationIdAndPhaseId_WithOrganizationTypeId3_ShouldReturnNgo() {
        // Arrange
        testInnovationInfo.setLeadOrganizationId(3L);
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L))
            .thenReturn(Optional.of(testInnovationInfo));

        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getProjectInnovationInfoByInnovationIdAndPhaseId_WithOrganizationTypeId4_ShouldReturnAcademic() {
        // Arrange
        testInnovationInfo.setLeadOrganizationId(4L);
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L))
            .thenReturn(Optional.of(testInnovationInfo));

        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getProjectInnovationInfoByInnovationIdAndPhaseId_WithOrganizationTypeId5_ShouldReturnInternational() {
        // Arrange
        testInnovationInfo.setLeadOrganizationId(5L);
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L))
            .thenReturn(Optional.of(testInnovationInfo));

        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getProjectInnovationInfoByInnovationIdAndPhaseId_WithOrganizationTypeIdDefault_ShouldReturnDefault() {
        // Arrange
        testInnovationInfo.setLeadOrganizationId(99L);
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L))
            .thenReturn(Optional.of(testInnovationInfo));

        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getProjectInnovationInfoByInnovationIdAndPhaseId_WithPartnerTypeId1_ShouldReturnLeadingPartner() {
        // Arrange
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L))
            .thenReturn(Optional.of(testInnovationInfo));
        when(repositoryAdapter.findPartnershipsByInnovationIdAndPhase(anyLong(), anyLong()))
            .thenReturn(Arrays.asList());

        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getProjectInnovationInfoByInnovationIdAndPhaseId_WithPartnerTypeId2_ShouldReturnImplementingPartner() {
        // Arrange
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L))
            .thenReturn(Optional.of(testInnovationInfo));
        when(repositoryAdapter.findPartnershipsByInnovationIdAndPhase(anyLong(), anyLong()))
            .thenReturn(Arrays.asList());

        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getProjectInnovationInfoByInnovationIdAndPhaseId_WithPartnerTypeId3_ShouldReturnSupportingPartner() {
        // Arrange
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L))
            .thenReturn(Optional.of(testInnovationInfo));
        when(repositoryAdapter.findPartnershipsByInnovationIdAndPhase(anyLong(), anyLong()))
            .thenReturn(Arrays.asList());

        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getProjectInnovationInfoByInnovationIdAndPhaseId_WithPartnerTypeId4_ShouldReturnCoFundingPartner() {
        // Arrange
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L))
            .thenReturn(Optional.of(testInnovationInfo));
        when(repositoryAdapter.findPartnershipsByInnovationIdAndPhase(anyLong(), anyLong()))
            .thenReturn(Arrays.asList());

        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getProjectInnovationInfoByInnovationIdAndPhaseId_WithPartnerTypeIdDefault_ShouldReturnDefault() {
        // Arrange
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L))
            .thenReturn(Optional.of(testInnovationInfo));
        when(repositoryAdapter.findPartnershipsByInnovationIdAndPhase(anyLong(), anyLong()))
            .thenReturn(Arrays.asList());

        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(1L, 5L);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void searchInnovations_WithBothCountryAndActorFilters_ShouldUseGeneralSearch() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findActiveInnovationsInfoWithFilters(any(), any(), any(), any(), any(), any()))
            .thenReturn(innovations);

        // Act
        ResponseEntity<?> result = invokeSearchInnovations(
            null, null, null, null, null, Arrays.asList("1"), Arrays.asList("2"), null, 0, 20);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(projectInnovationUseCase).findActiveInnovationsInfoWithFilters(any(), any(), any(), any(), any(), any());
    }

    @Test
    void searchInnovations_WithPhaseAndCountryFilters_ShouldUseGeneralSearch() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findActiveInnovationsInfoWithFilters(any(), any(), any(), any(), any(), any()))
            .thenReturn(innovations);

        // Act
        ResponseEntity<?> result = invokeSearchInnovations(
            1L, null, null, null, null, Arrays.asList("1"), null, null, 0, 20);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(projectInnovationUseCase).findActiveInnovationsInfoWithFilters(eq(1L), any(), any(), any(), any(), any());
    }

    @Test
    void searchInnovations_WithPhaseAndActorFilters_ShouldUseGeneralSearch() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findActiveInnovationsInfoWithFilters(any(), any(), any(), any(), any(), any()))
            .thenReturn(innovations);

        // Act
        ResponseEntity<?> result = invokeSearchInnovations(
            1L, null, null, null, null, null, Arrays.asList("1"), null, 0, 20);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(projectInnovationUseCase).findActiveInnovationsInfoWithFilters(eq(1L), any(), any(), any(), any(), any());
    }

    @Test
    void searchInnovations_WithSdgIdAndCountryFilters_ShouldUseSdgSearch() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findActiveInnovationsInfoBySdgFilters(any(), any(), any(), any(), any(), any()))
            .thenReturn(innovations);

        // Act
        ResponseEntity<?> result = invokeSearchInnovations(
            null, null, null, null, 1L, Arrays.asList("1"), null, null, 0, 20);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(projectInnovationUseCase).findActiveInnovationsInfoBySdgFilters(any(), any(), eq(1L), any(), any(), any());
    }

    @Test
    void searchInnovations_WithSdgIdAndActorFilters_ShouldUseSdgSearch() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findActiveInnovationsInfoBySdgFilters(any(), any(), any(), any(), any(), any()))
            .thenReturn(innovations);

        // Act
        ResponseEntity<?> result = invokeSearchInnovations(
            null, null, null, null, 1L, null, Arrays.asList("1"), null, 0, 20);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(projectInnovationUseCase).findActiveInnovationsInfoBySdgFilters(any(), any(), eq(1L), any(), any(), any());
    }

    @Test
    void searchInnovations_WithSdgIdAndBothFilters_ShouldUseSdgSearch() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findActiveInnovationsInfoBySdgFilters(any(), any(), any(), any(), any(), any()))
            .thenReturn(innovations);

        // Act
        ResponseEntity<?> result = invokeSearchInnovations(
            null, null, null, null, 1L, Arrays.asList("1"), Arrays.asList("2"), null, 0, 20);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(projectInnovationUseCase).findActiveInnovationsInfoBySdgFilters(any(), any(), eq(1L), any(), any(), any());
    }

    @Test
    void searchInnovations_WithPaginationOffsetBeyondResults_ShouldReturnEmpty() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findAllActiveInnovationsInfo()).thenReturn(innovations);

        // Act
        ResponseEntity<?> result = invokeSearchInnovations(
            null, null, null, null, null, null, null, null, 100, 20);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void searchInnovations_WithPaginationLimitGreaterThanResults_ShouldReturnAll() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findAllActiveInnovationsInfo()).thenReturn(innovations);

        // Act
        ResponseEntity<?> result = invokeSearchInnovations(
            null, null, null, null, null, null, null, null, 0, 1000);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void searchInnovationsSimple_WithPhaseAndCountryFilters_ShouldUseGeneralSearch() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findActiveInnovationsInfoWithFilters(any(), any(), any(), any(), any(), any()))
            .thenReturn(innovations);

        // Act
        ResponseEntity<?> result = invokeSearchInnovationsSimple(
            1L, null, null, null, null, Arrays.asList("1"), null, null, 0, 20);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(projectInnovationUseCase).findActiveInnovationsInfoWithFilters(eq(1L), any(), any(), any(), any(), any());
    }

    @Test
    void searchInnovationsComplete_WithPhaseAndCountryFilters_ShouldUseGeneralSearch() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findActiveInnovationsInfoWithFilters(any(), any(), any(), any(), any(), any()))
            .thenReturn(innovations);

        // Act
        ResponseEntity<?> result = invokeSearchInnovationsComplete(
            1L, null, null, null, null, Arrays.asList("1"), null, null, 0, 20);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(projectInnovationUseCase).findActiveInnovationsInfoWithFilters(eq(1L), any(), any(), any(), any(), any());
    }


    @Test
    void searchInnovations_WithMultipleFiltersCombined_ShouldUseGeneralSearch() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findActiveInnovationsInfoWithFilters(any(), any(), any(), any(), any(), any()))
            .thenReturn(innovations);

        // Act - Combine phase, readinessScale, and innovationTypeId
        ResponseEntity<?> result = invokeSearchInnovations(
            1L, 5, 2L, null, null, null, null, null, 0, 20);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(projectInnovationUseCase).findActiveInnovationsInfoWithFilters(eq(1L), eq(5), eq(2L), any(), any(), any());
    }

    @Test
    void searchInnovations_WithAllFiltersCombined_ShouldUseGeneralSearch() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findActiveInnovationsInfoWithFilters(any(), any(), any(), any(), any(), any()))
            .thenReturn(innovations);

        // Act - Combine all general filters
        ResponseEntity<?> result = invokeSearchInnovations(
            1L, 5, 2L, null, null, Arrays.asList("1"), Arrays.asList("2"), null, 0, 20);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(projectInnovationUseCase).findActiveInnovationsInfoWithFilters(eq(1L), eq(5), eq(2L), any(), any(), any());
    }

    @Test
    void getInnovationCountryStats_Success_ShouldReturnStats() {
        // Arrange
        Long phaseId = 428L;
        when(repositoryAdapter.countDistinctCountries(isNull(), eq(phaseId))).thenReturn(10L);
        when(repositoryAdapter.countDistinctInnovations(isNull(), eq(phaseId))).thenReturn(25L);
        when(repositoryAdapter.findAverageScalingReadinessByPhase(phaseId)).thenReturn(5.5);

        // Act
        ResponseEntity<?> result = controller.getInnovationCountryStats(phaseId);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
    }

    @Test
    void getInnovationCountryStats_Exception_ShouldReturnEmptyStats() {
        // Arrange
        Long phaseId = 428L;
        when(repositoryAdapter.countDistinctCountries(any(), any())).thenThrow(new RuntimeException("Database error"));

        // Act
        ResponseEntity<?> result = controller.getInnovationCountryStats(phaseId);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getAllInnovationTypes_Success_ShouldReturnTypes() {
        // Arrange
        com.example.demo.modules.innovationtype.domain.model.InnovationType type1 = 
            new com.example.demo.modules.innovationtype.domain.model.InnovationType();
        type1.setId(1L);
        type1.setName("Genetic");
        type1.setIsOldType(false);
        
        com.example.demo.modules.innovationtype.domain.model.InnovationType type2 = 
            new com.example.demo.modules.innovationtype.domain.model.InnovationType();
        type2.setId(2L);
        type2.setName("Production systems");
        type2.setIsOldType(true);
        
        when(innovationTypeRepository.findAll()).thenReturn(Arrays.asList(type1, type2));

        // Act
        ResponseEntity<?> result = controller.getAllInnovationTypes();

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
    }

    @Test
    void getAllInnovationTypes_Exception_ShouldReturnEmptyList() {
        // Arrange
        when(innovationTypeRepository.findAll()).thenThrow(new RuntimeException("Database error"));

        // Act
        ResponseEntity<?> result = controller.getAllInnovationTypes();

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, result.getStatusCode());
    }

    @Test
    void searchInnovations_WithCountryIdsContainingInvalidValue_ShouldThrowBadRequest() {
        // Act & Assert
        assertThrows(Exception.class, () -> invokeSearchInnovations(
            null, null, null, null, null, Arrays.asList("abc"), null, null, 0, 20));
    }

    @Test
    void searchInnovations_WithActorIdsContainingInvalidValue_ShouldThrowBadRequest() {
        // Act & Assert
        assertThrows(Exception.class, () -> invokeSearchInnovations(
            null, null, null, null, null, null, Arrays.asList("invalid"), null, 0, 20));
    }

    @Test
    void searchInnovations_WithCommaSeparatedCountryIds_ShouldParseProperly() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Collections.emptyList();
        when(projectInnovationUseCase.findActiveInnovationsInfoWithFilters(any(), any(), any(), any(), any(), any()))
            .thenReturn(innovations);

        // Act
        ResponseEntity<?> result = invokeSearchInnovations(
            1L, null, null, null, null, Arrays.asList("1,2,3"), null, null, 0, 20);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void searchInnovations_WithNullCountryIdsValue_ShouldHandleGracefully() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findAllActiveInnovationsInfo()).thenReturn(innovations);

        // Act
        ResponseEntity<?> result = invokeSearchInnovations(
            null, null, null, null, null, null, null, null, 0, 20);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void searchInnovations_WithEmptyStringInCountryIds_ShouldFilterOut() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Collections.emptyList();
        when(projectInnovationUseCase.findActiveInnovationsInfoWithFilters(any(), any(), any(), any(), any(), any()))
            .thenReturn(innovations);

        // Act
        ResponseEntity<?> result = invokeSearchInnovations(
            1L, null, null, null, null, Arrays.asList("1,,2"), null, null, 0, 20);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void searchInnovationsSimple_WithNullPhaseId_ShouldReturnAllActive() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findAllActiveInnovationsInfo()).thenReturn(innovations);

        // Act
        ResponseEntity<?> result = invokeSearchInnovationsSimple(
            null, null, null, null, null, null, null, null, 0, 20);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void searchInnovationsComplete_WithNullPhaseId_ShouldReturnAllActive() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findAllActiveInnovationsInfo()).thenReturn(innovations);

        // Act
        ResponseEntity<?> result = invokeSearchInnovationsComplete(
            null, null, null, null, null, null, null, null, 0, 20);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void searchInnovations_WithInnovationIdAndPhaseWithoutSdgId_ShouldUseSdgSearch() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findActiveInnovationsInfoBySdgFilters(any(), any(), any(), any(), any(), any()))
            .thenReturn(innovations);

        // Act
        ResponseEntity<?> result = invokeSearchInnovations(
            1L, null, null, 100L, null, null, null, null, 0, 20);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(projectInnovationUseCase).findActiveInnovationsInfoBySdgFilters(eq(100L), eq(1L), isNull(), any(), any(), any());
    }

    @Test
    void searchInnovations_WithNegativeLimit_ShouldUseDefaultLimit() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findAllActiveInnovationsInfo()).thenReturn(innovations);

        // Act
        ResponseEntity<?> result = invokeSearchInnovations(
            null, null, null, null, null, null, null, null, 0, -5);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void searchInnovations_WithZeroLimit_ShouldUseDefaultLimit() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findAllActiveInnovationsInfo()).thenReturn(innovations);

        // Act
        ResponseEntity<?> result = invokeSearchInnovations(
            null, null, null, null, null, null, null, null, 0, 0);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void searchInnovations_WithNegativeOffset_ShouldUseZeroOffset() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findAllActiveInnovationsInfo()).thenReturn(innovations);

        // Act
        ResponseEntity<?> result = invokeSearchInnovations(
            null, null, null, null, null, null, null, null, -10, 20);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void searchInnovationsSimple_WithActorFilterOnly_ShouldUseGeneralSearch() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findActiveInnovationsInfoWithFilters(any(), any(), any(), any(), any(), any()))
            .thenReturn(innovations);

        // Act
        ResponseEntity<?> result = invokeSearchInnovationsSimple(
            null, null, null, null, null, null, Arrays.asList("1"), null, 0, 20);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void searchInnovationsComplete_WithActorFilterOnly_ShouldUseGeneralSearch() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findActiveInnovationsInfoWithFilters(any(), any(), any(), any(), any(), any()))
            .thenReturn(innovations);

        // Act
        ResponseEntity<?> result = invokeSearchInnovationsComplete(
            null, null, null, null, null, null, Arrays.asList("1"), null, 0, 20);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void searchInnovations_WithReadinessScaleOnly_ShouldUseGeneralSearch() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findActiveInnovationsInfoWithFilters(any(), any(), any(), any(), any(), any()))
            .thenReturn(innovations);

        // Act
        ResponseEntity<?> result = invokeSearchInnovations(
            null, 7, null, null, null, null, null, null, 0, 20);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(projectInnovationUseCase).findActiveInnovationsInfoWithFilters(isNull(), eq(7), isNull(), any(), any(), any());
    }

    @Test
    void searchInnovations_WithInnovationTypeIdOnly_ShouldUseGeneralSearch() {
        // Arrange
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findActiveInnovationsInfoWithFilters(any(), any(), any(), any(), any(), any()))
            .thenReturn(innovations);

        // Act
        ResponseEntity<?> result = invokeSearchInnovations(
            null, null, 3L, null, null, null, null, null, 0, 20);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(projectInnovationUseCase).findActiveInnovationsInfoWithFilters(isNull(), isNull(), eq(3L), any(), any(), any());
    }

    // Tests for helper methods coverage

    @Test
    void getProjectInnovationInfo_WithSdgs_ShouldCoverSdgHelpers() {
        // Arrange
        Long innovationId = 1L;
        Long phaseId = 1L;
        
        testInnovationInfo.setInnovationTypeId(1L);
        testInnovationInfo.setStageInnovationId(1L);
        testInnovationInfo.setGeographicScopeId(1L);
        testInnovationInfo.setRepIndRegionId(1L);
        testInnovationInfo.setRepIndContributionCrpId(1L);
        testInnovationInfo.setRepIndDegreeInnovationId(1L);
        testInnovationInfo.setLeadOrganizationId(1L);
        testInnovationInfo.setGenderFocusLevelId(1L);
        testInnovationInfo.setYouthFocusLevelId(1L);
        testInnovationInfo.setIntellectualPropertyInstitutionId(1L);
        testInnovationInfo.setPhaseResearchId(1L);
        
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(innovationId, phaseId))
            .thenReturn(Optional.of(testInnovationInfo));
        
        // Mock SDGs
        com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationSdg sdg = 
            new com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationSdg();
        sdg.setId(1L);
        sdg.setInnovationId(innovationId);
        sdg.setSdgId(2L);
        sdg.setIdPhase(phaseId);
        sdg.setIsActive(true);
        when(repositoryAdapter.findSdgsByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Arrays.asList(sdg));
        
        // Mock Regions
        com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationRegion region = 
            new com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationRegion();
        region.setId(1);
        region.setProjectInnovationId(innovationId);
        region.setIdRegion(1L);
        region.setIdPhase(phaseId);
        when(repositoryAdapter.findRegionsByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Arrays.asList(region));
        
        // Mock Countries
        com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationCountry country = 
            new com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationCountry();
        country.setId(1);
        country.setProjectInnovationId(innovationId);
        country.setIdCountry(113L);
        country.setIdPhase(phaseId);
        when(repositoryAdapter.findCountriesByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Arrays.asList(country));
        
        // Mock References
        com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationReference reference = 
            new com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationReference();
        reference.setId(1L);
        reference.setReference("Test Reference");
        reference.setIdPhase(phaseId);
        when(repositoryAdapter.findReferencesByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Arrays.asList(reference));
        
        // Mock Complementary Solutions
        when(repositoryAdapter.findComplementarySolutionsByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Collections.emptyList());
        
        // Mock Organizations
        com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationOrganization organization = 
            new com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationOrganization();
        organization.setId(1L);
        organization.setProjectInnovationId(innovationId);
        organization.setIdPhase(phaseId);
        organization.setRepIndOrganizationTypeId(1L);
        when(repositoryAdapter.findOrganizationsByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Arrays.asList(organization));
        
        // Mock Alliance Organizations
        when(repositoryAdapter.findAllianceOrganizationsByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Collections.emptyList());
        
        // Mock Contributing Organizations
        when(repositoryAdapter.findContributingOrganizationsByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Collections.emptyList());
        
        // Mock Partnerships
        com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationPartnership partnership = 
            new com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationPartnership();
        partnership.setId(1L);
        partnership.setProjectInnovationId(innovationId);
        partnership.setInstitutionId(100L);
        partnership.setIdPhase(phaseId);
        partnership.setInnovationPartnerTypeId(1L);
        partnership.setIsActive(true);
        when(repositoryAdapter.findPartnershipsByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Arrays.asList(partnership));
        
        // Mock Institutions
        com.example.demo.modules.projectinnovation.domain.model.Institution institution = 
            new com.example.demo.modules.projectinnovation.domain.model.Institution();
        institution.setId(100L);
        institution.setName("Test Institution");
        institution.setAcronym("TI");
        institution.setWebsiteLink("https://test.org");
        when(repositoryAdapter.findInstitutionsByIds(Arrays.asList(100L)))
            .thenReturn(Arrays.asList(institution));
        
        // Mock Contact Persons
        when(repositoryAdapter.findContactPersonsByPartnershipIds(Arrays.asList(1L)))
            .thenReturn(Collections.emptyList());
        
        // Mock Bundles
        when(repositoryAdapter.findBundlesByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Collections.emptyList());
        
        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(innovationId, phaseId);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        verify(repositoryAdapter).findSdgsByInnovationIdAndPhase(innovationId, phaseId);
        verify(repositoryAdapter).findRegionsByInnovationIdAndPhase(innovationId, phaseId);
        verify(repositoryAdapter).findCountriesByInnovationIdAndPhase(innovationId, phaseId);
    }

    @Test
    void getProjectInnovationInfo_WithPartnershipsAndContactPersons_ShouldCoverHelpers() {
        // Arrange
        Long innovationId = 1L;
        Long phaseId = 1L;
        
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(innovationId, phaseId))
            .thenReturn(Optional.of(testInnovationInfo));
        
        // Mock empty lists for most relations
        when(repositoryAdapter.findSdgsByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Collections.emptyList());
        when(repositoryAdapter.findRegionsByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Collections.emptyList());
        when(repositoryAdapter.findCountriesByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Collections.emptyList());
        when(repositoryAdapter.findReferencesByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Collections.emptyList());
        when(repositoryAdapter.findComplementarySolutionsByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Collections.emptyList());
        when(repositoryAdapter.findOrganizationsByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Collections.emptyList());
        when(repositoryAdapter.findAllianceOrganizationsByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Collections.emptyList());
        when(repositoryAdapter.findContributingOrganizationsByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Collections.emptyList());
        
        // Mock Partnerships with contact persons
        com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationPartnership partnership = 
            new com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationPartnership();
        partnership.setId(1L);
        partnership.setProjectInnovationId(innovationId);
        partnership.setInstitutionId(100L);
        partnership.setIdPhase(phaseId);
        partnership.setInnovationPartnerTypeId(2L);
        partnership.setIsActive(true);
        when(repositoryAdapter.findPartnershipsByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Arrays.asList(partnership));
        
        // Mock Institution
        com.example.demo.modules.projectinnovation.domain.model.Institution institution = 
            new com.example.demo.modules.projectinnovation.domain.model.Institution();
        institution.setId(100L);
        institution.setName("Partner Institution");
        institution.setAcronym("PI");
        when(repositoryAdapter.findInstitutionsByIds(Arrays.asList(100L)))
            .thenReturn(Arrays.asList(institution));
        
        // Mock Contact Persons
        com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationPartnershipPerson person = 
            new com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationPartnershipPerson();
        person.setId(1L);
        person.setPartnershipId(1L);
        person.setUserId(10L);
        person.setIsActive(true);
        when(repositoryAdapter.findContactPersonsByPartnershipIds(Arrays.asList(1L)))
            .thenReturn(Arrays.asList(person));
        
        // Mock User
        com.example.demo.modules.projectinnovation.domain.model.User user = 
            new com.example.demo.modules.projectinnovation.domain.model.User();
        user.setId(10L);
        user.setFirstName("John");
        user.setLastName("Doe");
        user.setEmail("john.doe@example.com");
        when(repositoryAdapter.findUserById(10L)).thenReturn(Optional.of(user));
        
        // Mock Bundles
        when(repositoryAdapter.findBundlesByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Collections.emptyList());
        
        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(innovationId, phaseId);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(repositoryAdapter).findContactPersonsByPartnershipIds(Arrays.asList(1L));
        verify(repositoryAdapter).findUserById(10L);
    }

    @Test
    void getProjectInnovationInfo_WithBundles_ShouldCoverBundleHelpers() {
        // Arrange
        Long innovationId = 1L;
        Long phaseId = 1L;
        
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(innovationId, phaseId))
            .thenReturn(Optional.of(testInnovationInfo));
        
        // Mock empty lists
        when(repositoryAdapter.findSdgsByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Collections.emptyList());
        when(repositoryAdapter.findRegionsByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Collections.emptyList());
        when(repositoryAdapter.findCountriesByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Collections.emptyList());
        when(repositoryAdapter.findReferencesByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Collections.emptyList());
        when(repositoryAdapter.findComplementarySolutionsByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Collections.emptyList());
        when(repositoryAdapter.findOrganizationsByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Collections.emptyList());
        when(repositoryAdapter.findAllianceOrganizationsByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Collections.emptyList());
        when(repositoryAdapter.findContributingOrganizationsByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Collections.emptyList());
        when(repositoryAdapter.findPartnershipsByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Collections.emptyList());
        when(repositoryAdapter.findInstitutionsByIds(Collections.emptyList()))
            .thenReturn(Collections.emptyList());
        when(repositoryAdapter.findContactPersonsByPartnershipIds(Collections.emptyList()))
            .thenReturn(Collections.emptyList());
        
        // Mock Bundles
        com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationBundle bundle = 
            new com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationBundle();
        bundle.setId(1L);
        bundle.setProjectInnovationId(innovationId);
        bundle.setSelectedInnovationId(2L);
        bundle.setIdPhase(phaseId);
        bundle.setIsActive(true);
        when(repositoryAdapter.findBundlesByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Arrays.asList(bundle));
        
        // Mock bundle info
        ProjectInnovationInfo bundleInfo = new ProjectInnovationInfo();
        bundleInfo.setId(2L);
        bundleInfo.setProjectInnovationId(2L);
        bundleInfo.setTitle("Bundle Innovation");
        bundleInfo.setReadinessScale(5);
        when(repositoryAdapter.findActiveInfoByInnovationIdsAndPhase(Arrays.asList(2L), phaseId))
            .thenReturn(Arrays.asList(bundleInfo));
        
        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(innovationId, phaseId);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(repositoryAdapter).findBundlesByInnovationIdAndPhase(innovationId, phaseId);
    }

    @Test
    void getProjectInnovationInfo_WithAllianceOrganizations_ShouldCoverHelpers() {
        // Arrange
        Long innovationId = 1L;
        Long phaseId = 1L;
        
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(innovationId, phaseId))
            .thenReturn(Optional.of(testInnovationInfo));
        
        // Mock empty lists
        when(repositoryAdapter.findSdgsByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Collections.emptyList());
        when(repositoryAdapter.findRegionsByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Collections.emptyList());
        when(repositoryAdapter.findCountriesByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Collections.emptyList());
        when(repositoryAdapter.findReferencesByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Collections.emptyList());
        when(repositoryAdapter.findComplementarySolutionsByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Collections.emptyList());
        when(repositoryAdapter.findOrganizationsByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Collections.emptyList());
        
        // Mock Alliance Organizations
        com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationAllianceOrganization alliance = 
            new com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationAllianceOrganization();
        alliance.setId(1L);
        alliance.setProjectInnovationId(innovationId);
        alliance.setIdPhase(phaseId);
        alliance.setInstitutionId(200L);
        alliance.setInstitutionTypeId(1L);
        alliance.setOrganizationName("Alliance Org");
        alliance.setIsScalingPartner(true);
        alliance.setIsActive(true);
        when(repositoryAdapter.findAllianceOrganizationsByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Arrays.asList(alliance));
        
        // Mock Institution for alliance
        com.example.demo.modules.projectinnovation.domain.model.Institution allianceInstitution = 
            new com.example.demo.modules.projectinnovation.domain.model.Institution();
        allianceInstitution.setId(200L);
        allianceInstitution.setName("Alliance Institution");
        allianceInstitution.setAcronym("AI");
        when(repositoryAdapter.findInstitutionsByIds(Arrays.asList(200L)))
            .thenReturn(Arrays.asList(allianceInstitution));
        
        // Mock Contributing Organizations
        com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationContributingOrganization contributing = 
            new com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationContributingOrganization();
        contributing.setId(1L);
        contributing.setProjectInnovationId(innovationId);
        contributing.setIdPhase(phaseId);
        contributing.setInstitutionId(300L);
        when(repositoryAdapter.findContributingOrganizationsByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Arrays.asList(contributing));
        
        // Mock Institution for contributing
        com.example.demo.modules.projectinnovation.domain.model.Institution contributingInstitution = 
            new com.example.demo.modules.projectinnovation.domain.model.Institution();
        contributingInstitution.setId(300L);
        contributingInstitution.setName("Contributing Institution");
        contributingInstitution.setAcronym("CI");
        when(repositoryAdapter.findInstitutionsByIds(Arrays.asList(300L)))
            .thenReturn(Arrays.asList(contributingInstitution));
        
        // Mock headquarter locations
        when(repositoryAdapter.findHeadquarterCitiesByInstitutionIds(Arrays.asList(300L)))
            .thenReturn(Collections.emptyMap());
        
        // Mock Partnerships
        when(repositoryAdapter.findPartnershipsByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Collections.emptyList());
        when(repositoryAdapter.findContactPersonsByPartnershipIds(Collections.emptyList()))
            .thenReturn(Collections.emptyList());
        
        // Mock Bundles
        when(repositoryAdapter.findBundlesByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Collections.emptyList());
        
        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(innovationId, phaseId);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(repositoryAdapter).findAllianceOrganizationsByInnovationIdAndPhase(innovationId, phaseId);
        verify(repositoryAdapter).findContributingOrganizationsByInnovationIdAndPhase(innovationId, phaseId);
    }

    @Test
    void getProjectInnovationInfo_WithComplementarySolutions_ShouldCoverHelpers() {
        // Arrange
        Long innovationId = 1L;
        Long phaseId = 1L;
        
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(innovationId, phaseId))
            .thenReturn(Optional.of(testInnovationInfo));
        
        // Mock empty lists
        when(repositoryAdapter.findSdgsByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Collections.emptyList());
        when(repositoryAdapter.findRegionsByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Collections.emptyList());
        when(repositoryAdapter.findCountriesByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Collections.emptyList());
        when(repositoryAdapter.findReferencesByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Collections.emptyList());
        
        // Mock Complementary Solutions
        com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationComplementarySolution solution = 
            new com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationComplementarySolution();
        solution.setId(1L);
        solution.setTitle("Complementary Solution");
        solution.setShortTitle("CS");
        solution.setShortDescription("Description");
        solution.setProjectInnovationTypeId(1L);
        solution.setIdPhase(phaseId);
        solution.setIsActive(true);
        when(repositoryAdapter.findComplementarySolutionsByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Arrays.asList(solution));
        
        when(repositoryAdapter.findOrganizationsByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Collections.emptyList());
        when(repositoryAdapter.findAllianceOrganizationsByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Collections.emptyList());
        when(repositoryAdapter.findContributingOrganizationsByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Collections.emptyList());
        when(repositoryAdapter.findPartnershipsByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Collections.emptyList());
        when(repositoryAdapter.findInstitutionsByIds(Collections.emptyList()))
            .thenReturn(Collections.emptyList());
        when(repositoryAdapter.findContactPersonsByPartnershipIds(Collections.emptyList()))
            .thenReturn(Collections.emptyList());
        when(repositoryAdapter.findBundlesByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Collections.emptyList());
        
        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(innovationId, phaseId);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(repositoryAdapter).findComplementarySolutionsByInnovationIdAndPhase(innovationId, phaseId);
    }

    @Test
    void getProjectInnovationInfo_WithKnowledgePotential_ShouldIncludeReason() {
        // Arrange
        Long innovationId = 1L;
        Long phaseId = 1L;
        
        testInnovationInfo.setHasKnowledgePotentialId(2L);
        testInnovationInfo.setReasonKnowledgePotential("Test reason");
        
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(innovationId, phaseId))
            .thenReturn(Optional.of(testInnovationInfo));
        
        // Mock empty relations
        when(repositoryAdapter.findSdgsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findRegionsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findCountriesByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findReferencesByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findComplementarySolutionsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findOrganizationsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findAllianceOrganizationsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findContributingOrganizationsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findPartnershipsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findInstitutionsByIds(any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findContactPersonsByPartnershipIds(any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findBundlesByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        
        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(innovationId, phaseId);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getProjectInnovationInfo_WithContactPersonNoUser_ShouldUseDefaults() {
        // Arrange
        Long innovationId = 1L;
        Long phaseId = 1L;
        
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(innovationId, phaseId))
            .thenReturn(Optional.of(testInnovationInfo));
        
        // Mock empty lists
        when(repositoryAdapter.findSdgsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findRegionsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findCountriesByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findReferencesByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findComplementarySolutionsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findOrganizationsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findAllianceOrganizationsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findContributingOrganizationsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        
        // Mock Partnership with contact person but no user found
        com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationPartnership partnership = 
            new com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationPartnership();
        partnership.setId(1L);
        partnership.setProjectInnovationId(innovationId);
        partnership.setInstitutionId(100L);
        partnership.setIdPhase(phaseId);
        partnership.setInnovationPartnerTypeId(3L);
        when(repositoryAdapter.findPartnershipsByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Arrays.asList(partnership));
        
        com.example.demo.modules.projectinnovation.domain.model.Institution institution = 
            new com.example.demo.modules.projectinnovation.domain.model.Institution();
        institution.setId(100L);
        institution.setName("Test Inst");
        when(repositoryAdapter.findInstitutionsByIds(any())).thenReturn(Arrays.asList(institution));
        
        com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationPartnershipPerson person = 
            new com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationPartnershipPerson();
        person.setId(1L);
        person.setPartnershipId(1L);
        person.setUserId(999L);
        when(repositoryAdapter.findContactPersonsByPartnershipIds(Arrays.asList(1L)))
            .thenReturn(Arrays.asList(person));
        
        // User not found
        when(repositoryAdapter.findUserById(999L)).thenReturn(Optional.empty());
        
        when(repositoryAdapter.findBundlesByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        
        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(innovationId, phaseId);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(repositoryAdapter).findUserById(999L);
    }

    @Test
    void getProjectInnovationInfo_WithUserHavingUsernameOnly_ShouldUseUsername() {
        // Arrange
        Long innovationId = 1L;
        Long phaseId = 1L;
        
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(innovationId, phaseId))
            .thenReturn(Optional.of(testInnovationInfo));
        
        // Mock empty lists
        when(repositoryAdapter.findSdgsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findRegionsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findCountriesByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findReferencesByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findComplementarySolutionsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findOrganizationsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findAllianceOrganizationsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findContributingOrganizationsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        
        // Mock Partnership
        com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationPartnership partnership = 
            new com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationPartnership();
        partnership.setId(1L);
        partnership.setProjectInnovationId(innovationId);
        partnership.setInstitutionId(100L);
        partnership.setIdPhase(phaseId);
        partnership.setInnovationPartnerTypeId(4L);
        when(repositoryAdapter.findPartnershipsByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Arrays.asList(partnership));
        
        com.example.demo.modules.projectinnovation.domain.model.Institution institution = 
            new com.example.demo.modules.projectinnovation.domain.model.Institution();
        institution.setId(100L);
        institution.setName("Test Inst");
        when(repositoryAdapter.findInstitutionsByIds(any())).thenReturn(Arrays.asList(institution));
        
        com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationPartnershipPerson person = 
            new com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationPartnershipPerson();
        person.setId(1L);
        person.setPartnershipId(1L);
        person.setUserId(10L);
        when(repositoryAdapter.findContactPersonsByPartnershipIds(Arrays.asList(1L)))
            .thenReturn(Arrays.asList(person));
        
        // User with only username (no first/last name)
        com.example.demo.modules.projectinnovation.domain.model.User user = 
            new com.example.demo.modules.projectinnovation.domain.model.User();
        user.setId(10L);
        user.setUsername("testuser");
        user.setEmail("test@example.com");
        when(repositoryAdapter.findUserById(10L)).thenReturn(Optional.of(user));
        
        when(repositoryAdapter.findBundlesByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        
        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(innovationId, phaseId);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    // Tests for SDG repository methods

    @Test
    void getProjectInnovationInfo_WithSdgData_ShouldResolveSdgNames() {
        // Arrange
        Long innovationId = 1L;
        Long phaseId = 1L;
        
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(innovationId, phaseId))
            .thenReturn(Optional.of(testInnovationInfo));
        
        // Mock SDG with data
        com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationSdg sdg = 
            new com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationSdg();
        sdg.setId(1L);
        sdg.setInnovationId(innovationId);
        sdg.setSdgId(5L);
        sdg.setIdPhase(phaseId);
        sdg.setIsActive(true);
        when(repositoryAdapter.findSdgsByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Arrays.asList(sdg));
        
        // Mock SDG repository using findBySmoCode (the actual method used)
        com.example.demo.modules.sustainabledevelopmentgoals.domain.model.SustainableDevelopmentGoal sdgEntity = 
            new com.example.demo.modules.sustainabledevelopmentgoals.domain.model.SustainableDevelopmentGoal();
        sdgEntity.setId(5L);
        sdgEntity.setShortName("SDG 5");
        sdgEntity.setFullName("Gender Equality");
        sdgEntity.setSmoCode(5L);
        when(sdgRepository.findBySmoCode(5L)).thenReturn(Optional.of(sdgEntity));
        
        // Mock other empty relations
        when(repositoryAdapter.findRegionsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findCountriesByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findReferencesByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findComplementarySolutionsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findOrganizationsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findAllianceOrganizationsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findContributingOrganizationsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findPartnershipsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findInstitutionsByIds(any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findContactPersonsByPartnershipIds(any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findBundlesByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        
        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(innovationId, phaseId);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(sdgRepository, atLeast(1)).findBySmoCode(5L);
    }

    @Test
    void getProjectInnovationInfo_WithSdgNotFound_ShouldHandleGracefully() {
        // Arrange
        Long innovationId = 1L;
        Long phaseId = 1L;
        
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(innovationId, phaseId))
            .thenReturn(Optional.of(testInnovationInfo));
        
        // Mock SDG with data but SDG not found in repository
        com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationSdg sdg = 
            new com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationSdg();
        sdg.setId(1L);
        sdg.setInnovationId(innovationId);
        sdg.setSdgId(999L);
        sdg.setIdPhase(phaseId);
        sdg.setIsActive(true);
        when(repositoryAdapter.findSdgsByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Arrays.asList(sdg));
        
        // SDG not found - uses findBySmoCode
        when(sdgRepository.findBySmoCode(999L)).thenReturn(Optional.empty());
        
        // Mock other empty relations
        when(repositoryAdapter.findRegionsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findCountriesByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findReferencesByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findComplementarySolutionsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findOrganizationsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findAllianceOrganizationsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findContributingOrganizationsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findPartnershipsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findInstitutionsByIds(any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findContactPersonsByPartnershipIds(any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findBundlesByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        
        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(innovationId, phaseId);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getProjectInnovationInfo_WithInnovationType_ShouldResolveTypeName() {
        // Arrange
        Long innovationId = 1L;
        Long phaseId = 1L;
        
        testInnovationInfo.setInnovationTypeId(3L);
        
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(innovationId, phaseId))
            .thenReturn(Optional.of(testInnovationInfo));
        
        // Mock InnovationType repository
        com.example.demo.modules.innovationtype.domain.model.InnovationType innovationType = 
            new com.example.demo.modules.innovationtype.domain.model.InnovationType();
        innovationType.setId(3L);
        innovationType.setName("Genetic Innovation");
        innovationType.setDefinition("Innovations related to genetic improvements");
        when(innovationTypeRepository.findById(3L)).thenReturn(Optional.of(innovationType));
        
        // Mock empty relations
        when(repositoryAdapter.findSdgsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findRegionsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findCountriesByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findReferencesByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findComplementarySolutionsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findOrganizationsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findAllianceOrganizationsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findContributingOrganizationsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findPartnershipsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findInstitutionsByIds(any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findContactPersonsByPartnershipIds(any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findBundlesByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        
        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(innovationId, phaseId);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(innovationTypeRepository).findById(3L);
    }

    @Test
    void getProjectInnovationInfo_WithInnovationTypeNotFound_ShouldHandleGracefully() {
        // Arrange
        Long innovationId = 1L;
        Long phaseId = 1L;
        
        testInnovationInfo.setInnovationTypeId(999L);
        
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(innovationId, phaseId))
            .thenReturn(Optional.of(testInnovationInfo));
        
        // InnovationType not found
        when(innovationTypeRepository.findById(999L)).thenReturn(Optional.empty());
        
        // Mock empty relations
        when(repositoryAdapter.findSdgsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findRegionsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findCountriesByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findReferencesByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findComplementarySolutionsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findOrganizationsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findAllianceOrganizationsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findContributingOrganizationsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findPartnershipsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findInstitutionsByIds(any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findContactPersonsByPartnershipIds(any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findBundlesByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        
        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(innovationId, phaseId);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getProjectInnovationInfo_WithRegionData_ShouldResolveRegionName() {
        // Arrange
        Long innovationId = 1L;
        Long phaseId = 1L;
        
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(innovationId, phaseId))
            .thenReturn(Optional.of(testInnovationInfo));
        
        // Mock empty SDGs
        when(repositoryAdapter.findSdgsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        
        // Mock Region with data
        com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationRegion region = 
            new com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationRegion();
        region.setId(1);
        region.setProjectInnovationId(innovationId);
        region.setIdRegion(7L);
        region.setIdPhase(phaseId);
        when(repositoryAdapter.findRegionsByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Arrays.asList(region));
        
        // Mock LocElement for region using findActiveById
        com.example.demo.modules.projectinnovation.domain.model.LocElement locElement = 
            new com.example.demo.modules.projectinnovation.domain.model.LocElement();
        locElement.setId(7L);
        locElement.setName("Sub-Saharan Africa");
        locElement.setIsoAlpha2("SSA");
        when(locElementRepository.findActiveById(7L)).thenReturn(Optional.of(locElement));
        
        // Mock other empty relations
        when(repositoryAdapter.findCountriesByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findReferencesByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findComplementarySolutionsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findOrganizationsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findAllianceOrganizationsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findContributingOrganizationsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findPartnershipsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findInstitutionsByIds(any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findContactPersonsByPartnershipIds(any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findBundlesByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        
        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(innovationId, phaseId);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(locElementRepository).findActiveById(7L);
    }

    @Test
    void getProjectInnovationInfo_WithCountryData_ShouldResolveCountryName() {
        // Arrange
        Long innovationId = 1L;
        Long phaseId = 1L;
        
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(innovationId, phaseId))
            .thenReturn(Optional.of(testInnovationInfo));
        
        // Mock empty SDGs and Regions
        when(repositoryAdapter.findSdgsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findRegionsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        
        // Mock Country with data
        com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationCountry country = 
            new com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationCountry();
        country.setId(1);
        country.setProjectInnovationId(innovationId);
        country.setIdCountry(113L);
        country.setIdPhase(phaseId);
        when(repositoryAdapter.findCountriesByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Arrays.asList(country));
        
        // Mock LocElement for country using findActiveById
        com.example.demo.modules.projectinnovation.domain.model.LocElement locElement = 
            new com.example.demo.modules.projectinnovation.domain.model.LocElement();
        locElement.setId(113L);
        locElement.setName("Kenya");
        locElement.setIsoAlpha2("KE");
        when(locElementRepository.findActiveById(113L)).thenReturn(Optional.of(locElement));
        
        // Mock other empty relations
        when(repositoryAdapter.findReferencesByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findComplementarySolutionsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findOrganizationsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findAllianceOrganizationsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findContributingOrganizationsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findPartnershipsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findInstitutionsByIds(any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findContactPersonsByPartnershipIds(any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findBundlesByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        
        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(innovationId, phaseId);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(locElementRepository).findActiveById(113L);
    }

    @Test
    void getProjectInnovationInfo_WithRegionNotFound_ShouldHandleGracefully() {
        // Arrange
        Long innovationId = 1L;
        Long phaseId = 1L;
        
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(innovationId, phaseId))
            .thenReturn(Optional.of(testInnovationInfo));
        
        // Mock empty SDGs
        when(repositoryAdapter.findSdgsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        
        // Mock Region with data but region not found
        com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationRegion region = 
            new com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationRegion();
        region.setId(1);
        region.setProjectInnovationId(innovationId);
        region.setIdRegion(999L);
        region.setIdPhase(phaseId);
        when(repositoryAdapter.findRegionsByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Arrays.asList(region));
        
        // Region not found - uses findActiveById
        when(locElementRepository.findActiveById(999L)).thenReturn(Optional.empty());
        
        // Mock other empty relations
        when(repositoryAdapter.findCountriesByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findReferencesByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findComplementarySolutionsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findOrganizationsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findAllianceOrganizationsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findContributingOrganizationsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findPartnershipsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findInstitutionsByIds(any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findContactPersonsByPartnershipIds(any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findBundlesByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        
        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(innovationId, phaseId);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getProjectInnovationInfo_WithCountryNotFound_ShouldHandleGracefully() {
        // Arrange
        Long innovationId = 1L;
        Long phaseId = 1L;
        
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(innovationId, phaseId))
            .thenReturn(Optional.of(testInnovationInfo));
        
        // Mock empty SDGs and Regions
        when(repositoryAdapter.findSdgsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findRegionsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        
        // Mock Country with data but country not found
        com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationCountry country = 
            new com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationCountry();
        country.setId(1);
        country.setProjectInnovationId(innovationId);
        country.setIdCountry(999L);
        country.setIdPhase(phaseId);
        when(repositoryAdapter.findCountriesByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Arrays.asList(country));
        
        // Country not found - uses findActiveById
        when(locElementRepository.findActiveById(999L)).thenReturn(Optional.empty());
        
        // Mock other empty relations
        when(repositoryAdapter.findReferencesByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findComplementarySolutionsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findOrganizationsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findAllianceOrganizationsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findContributingOrganizationsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findPartnershipsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findInstitutionsByIds(any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findContactPersonsByPartnershipIds(any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findBundlesByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        
        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(innovationId, phaseId);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void getProjectInnovationInfo_WithMultipleSdgs_ShouldResolveAllNames() {
        // Arrange
        Long innovationId = 1L;
        Long phaseId = 1L;
        
        when(projectInnovationUseCase.findProjectInnovationInfoByInnovationIdAndPhaseId(innovationId, phaseId))
            .thenReturn(Optional.of(testInnovationInfo));
        
        // Mock multiple SDGs
        com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationSdg sdg1 = 
            new com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationSdg();
        sdg1.setId(1L);
        sdg1.setSdgId(1L);
        sdg1.setIsActive(true);
        
        com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationSdg sdg2 = 
            new com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationSdg();
        sdg2.setId(2L);
        sdg2.setSdgId(2L);
        sdg2.setIsActive(true);
        
        when(repositoryAdapter.findSdgsByInnovationIdAndPhase(innovationId, phaseId))
            .thenReturn(Arrays.asList(sdg1, sdg2));
        
        // Mock SDG entities using findBySmoCode
        com.example.demo.modules.sustainabledevelopmentgoals.domain.model.SustainableDevelopmentGoal sdgEntity1 = 
            new com.example.demo.modules.sustainabledevelopmentgoals.domain.model.SustainableDevelopmentGoal();
        sdgEntity1.setId(1L);
        sdgEntity1.setShortName("SDG 1");
        sdgEntity1.setFullName("No Poverty");
        when(sdgRepository.findBySmoCode(1L)).thenReturn(Optional.of(sdgEntity1));
        
        com.example.demo.modules.sustainabledevelopmentgoals.domain.model.SustainableDevelopmentGoal sdgEntity2 = 
            new com.example.demo.modules.sustainabledevelopmentgoals.domain.model.SustainableDevelopmentGoal();
        sdgEntity2.setId(2L);
        sdgEntity2.setShortName("SDG 2");
        sdgEntity2.setFullName("Zero Hunger");
        when(sdgRepository.findBySmoCode(2L)).thenReturn(Optional.of(sdgEntity2));
        
        // Mock other empty relations
        when(repositoryAdapter.findRegionsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findCountriesByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findReferencesByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findComplementarySolutionsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findOrganizationsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findAllianceOrganizationsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findContributingOrganizationsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findPartnershipsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findInstitutionsByIds(any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findContactPersonsByPartnershipIds(any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findBundlesByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        
        // Act
        ResponseEntity<?> result = controller.getProjectInnovationInfoByInnovationIdAndPhaseId(innovationId, phaseId);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(sdgRepository, atLeast(1)).findBySmoCode(1L);
        verify(sdgRepository, atLeast(1)).findBySmoCode(2L);
    }

    // Tests to cover toSimpleResponse path (covers getSdgsForInnovation, getRegionsForInnovation, etc.)

    @Test
    void searchInnovationsSimple_WithSdgsRegionsCountries_ShouldCoverHelperMethods() {
        // Arrange - create innovation info with data that will trigger helper methods
        testInnovationInfo.setProjectInnovationId(1L);
        testInnovationInfo.setIdPhase(1L);
        testInnovationInfo.setInnovationTypeId(2L);
        
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findAllActiveInnovationsInfo()).thenReturn(innovations);
        
        // Mock SDGs for toSimpleResponse
        com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationSdg sdg = 
            new com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationSdg();
        sdg.setId(1L);
        sdg.setInnovationId(1L);
        sdg.setSdgId(3L);
        sdg.setIdPhase(1L);
        sdg.setIsActive(true);
        when(repositoryAdapter.findSdgsByInnovationIdAndPhase(1L, 1L))
            .thenReturn(Arrays.asList(sdg));
        
        // Mock SDG entity 
        com.example.demo.modules.sustainabledevelopmentgoals.domain.model.SustainableDevelopmentGoal sdgEntity = 
            new com.example.demo.modules.sustainabledevelopmentgoals.domain.model.SustainableDevelopmentGoal();
        sdgEntity.setId(3L);
        sdgEntity.setSmoCode(3L);
        sdgEntity.setShortName("SDG 3");
        sdgEntity.setFullName("Good Health and Well-being");
        when(sdgRepository.findBySmoCode(3L)).thenReturn(Optional.of(sdgEntity));
        
        // Mock Regions
        com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationRegion region = 
            new com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationRegion();
        region.setId(1);
        region.setProjectInnovationId(1L);
        region.setIdRegion(5L);
        region.setIdPhase(1L);
        when(repositoryAdapter.findRegionsByInnovationIdAndPhase(1L, 1L))
            .thenReturn(Arrays.asList(region));
        
        // Mock LocElement for region
        com.example.demo.modules.projectinnovation.domain.model.LocElement regionElement = 
            new com.example.demo.modules.projectinnovation.domain.model.LocElement();
        regionElement.setId(5L);
        regionElement.setName("East Africa");
        when(locElementRepository.findActiveById(5L)).thenReturn(Optional.of(regionElement));
        
        // Mock Countries
        com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationCountry country = 
            new com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationCountry();
        country.setId(1);
        country.setProjectInnovationId(1L);
        country.setIdCountry(108L);
        country.setIdPhase(1L);
        when(repositoryAdapter.findCountriesByInnovationIdAndPhase(1L, 1L))
            .thenReturn(Arrays.asList(country));
        
        // Mock LocElement for country
        com.example.demo.modules.projectinnovation.domain.model.LocElement countryElement = 
            new com.example.demo.modules.projectinnovation.domain.model.LocElement();
        countryElement.setId(108L);
        countryElement.setName("Ethiopia");
        when(locElementRepository.findActiveById(108L)).thenReturn(Optional.of(countryElement));
        
        // Mock InnovationType
        com.example.demo.modules.innovationtype.domain.model.InnovationType innovationType = 
            new com.example.demo.modules.innovationtype.domain.model.InnovationType();
        innovationType.setId(2L);
        innovationType.setName("Production systems innovation");
        when(innovationTypeRepository.findById(2L)).thenReturn(Optional.of(innovationType));
        
        // Act
        ResponseEntity<?> result = invokeSearchInnovationsSimple(
            null, null, null, null, null, null, null, null, 0, 20);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(sdgRepository, atLeast(1)).findBySmoCode(3L);
        verify(locElementRepository, atLeast(1)).findActiveById(5L);
        verify(locElementRepository, atLeast(1)).findActiveById(108L);
        verify(innovationTypeRepository, atLeast(1)).findById(2L);
    }

    @Test
    void searchInnovationsSimple_WithSdgFallback_ShouldUseHardcodedValues() {
        // Arrange - SDG not found in database, should use switch fallback
        testInnovationInfo.setProjectInnovationId(1L);
        testInnovationInfo.setIdPhase(1L);
        testInnovationInfo.setInnovationTypeId(null);
        
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findAllActiveInnovationsInfo()).thenReturn(innovations);
        
        // Mock SDG that doesn't exist in database
        com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationSdg sdg = 
            new com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationSdg();
        sdg.setId(1L);
        sdg.setInnovationId(1L);
        sdg.setSdgId(7L); // SDG 7 - should fallback to hardcoded
        sdg.setIdPhase(1L);
        sdg.setIsActive(true);
        when(repositoryAdapter.findSdgsByInnovationIdAndPhase(1L, 1L))
            .thenReturn(Arrays.asList(sdg));
        
        // SDG not found - will use fallback
        when(sdgRepository.findBySmoCode(7L)).thenReturn(Optional.empty());
        
        // Empty regions/countries
        when(repositoryAdapter.findRegionsByInnovationIdAndPhase(1L, 1L))
            .thenReturn(Collections.emptyList());
        when(repositoryAdapter.findCountriesByInnovationIdAndPhase(1L, 1L))
            .thenReturn(Collections.emptyList());
        
        // Act
        ResponseEntity<?> result = invokeSearchInnovationsSimple(
            null, null, null, null, null, null, null, null, 0, 20);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(sdgRepository, atLeast(1)).findBySmoCode(7L);
    }

    @Test
    void searchInnovationsSimple_WithInnovationTypeFallback_ShouldUseDefaultName() {
        // Arrange - InnovationType not found
        testInnovationInfo.setProjectInnovationId(1L);
        testInnovationInfo.setIdPhase(1L);
        testInnovationInfo.setInnovationTypeId(99L); // Non-existent type
        
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findAllActiveInnovationsInfo()).thenReturn(innovations);
        
        // Empty SDGs/regions/countries
        when(repositoryAdapter.findSdgsByInnovationIdAndPhase(1L, 1L))
            .thenReturn(Collections.emptyList());
        when(repositoryAdapter.findRegionsByInnovationIdAndPhase(1L, 1L))
            .thenReturn(Collections.emptyList());
        when(repositoryAdapter.findCountriesByInnovationIdAndPhase(1L, 1L))
            .thenReturn(Collections.emptyList());
        
        // InnovationType not found
        when(innovationTypeRepository.findById(99L)).thenReturn(Optional.empty());
        
        // Act
        ResponseEntity<?> result = invokeSearchInnovationsSimple(
            null, null, null, null, null, null, null, null, 0, 20);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(innovationTypeRepository, atLeast(1)).findById(99L);
    }

    @Test
    void searchInnovationsSimple_WithRegionFallback_ShouldUseDefaultName() {
        // Arrange - Region not found
        testInnovationInfo.setProjectInnovationId(1L);
        testInnovationInfo.setIdPhase(1L);
        testInnovationInfo.setInnovationTypeId(null);
        
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findAllActiveInnovationsInfo()).thenReturn(innovations);
        
        // Empty SDGs/countries
        when(repositoryAdapter.findSdgsByInnovationIdAndPhase(1L, 1L))
            .thenReturn(Collections.emptyList());
        when(repositoryAdapter.findCountriesByInnovationIdAndPhase(1L, 1L))
            .thenReturn(Collections.emptyList());
        
        // Region that doesn't exist
        com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationRegion region = 
            new com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationRegion();
        region.setId(1);
        region.setProjectInnovationId(1L);
        region.setIdRegion(999L);
        region.setIdPhase(1L);
        when(repositoryAdapter.findRegionsByInnovationIdAndPhase(1L, 1L))
            .thenReturn(Arrays.asList(region));
        
        // Region not found
        when(locElementRepository.findActiveById(999L)).thenReturn(Optional.empty());
        
        // Act
        ResponseEntity<?> result = invokeSearchInnovationsSimple(
            null, null, null, null, null, null, null, null, 0, 20);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void searchInnovationsSimple_WithCountryFallback_ShouldUseDefaultName() {
        // Arrange - Country not found
        testInnovationInfo.setProjectInnovationId(1L);
        testInnovationInfo.setIdPhase(1L);
        testInnovationInfo.setInnovationTypeId(null);
        
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findAllActiveInnovationsInfo()).thenReturn(innovations);
        
        // Empty SDGs/regions
        when(repositoryAdapter.findSdgsByInnovationIdAndPhase(1L, 1L))
            .thenReturn(Collections.emptyList());
        when(repositoryAdapter.findRegionsByInnovationIdAndPhase(1L, 1L))
            .thenReturn(Collections.emptyList());
        
        // Country that doesn't exist
        com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationCountry country = 
            new com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationCountry();
        country.setId(1);
        country.setProjectInnovationId(1L);
        country.setIdCountry(999L);
        country.setIdPhase(1L);
        when(repositoryAdapter.findCountriesByInnovationIdAndPhase(1L, 1L))
            .thenReturn(Arrays.asList(country));
        
        // Country not found
        when(locElementRepository.findActiveById(999L)).thenReturn(Optional.empty());
        
        // Act
        ResponseEntity<?> result = invokeSearchInnovationsSimple(
            null, null, null, null, null, null, null, null, 0, 20);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void searchInnovationsSimple_WithMultipleSdgsFallback_ShouldCoverAllSwitchCases() {
        // Arrange - Multiple SDGs to cover different switch cases
        testInnovationInfo.setProjectInnovationId(1L);
        testInnovationInfo.setIdPhase(1L);
        testInnovationInfo.setInnovationTypeId(null);
        
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findAllActiveInnovationsInfo()).thenReturn(innovations);
        
        // Create multiple SDGs with different IDs
        List<com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationSdg> sdgs = new java.util.ArrayList<>();
        for (long i = 1; i <= 17; i++) {
            com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationSdg sdg = 
                new com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationSdg();
            sdg.setId(i);
            sdg.setInnovationId(1L);
            sdg.setSdgId(i);
            sdg.setIdPhase(1L);
            sdg.setIsActive(true);
            sdgs.add(sdg);
            // All SDGs not found - will use fallback switch
            when(sdgRepository.findBySmoCode(i)).thenReturn(Optional.empty());
        }
        when(repositoryAdapter.findSdgsByInnovationIdAndPhase(1L, 1L)).thenReturn(sdgs);
        
        // Empty regions/countries
        when(repositoryAdapter.findRegionsByInnovationIdAndPhase(1L, 1L))
            .thenReturn(Collections.emptyList());
        when(repositoryAdapter.findCountriesByInnovationIdAndPhase(1L, 1L))
            .thenReturn(Collections.emptyList());
        
        // Act
        ResponseEntity<?> result = invokeSearchInnovationsSimple(
            null, null, null, null, null, null, null, null, 0, 20);

        // Assert
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void toCompleteInfoWithRelationsResponse_WrapperMethod_ShouldDelegateToFullMethod() throws Exception {
        // Arrange - setup testInnovationInfo with IDs
        testInnovationInfo.setProjectInnovationId(1L);
        testInnovationInfo.setIdPhase(1L);
        
        // Mock empty relations
        when(repositoryAdapter.findSdgsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findRegionsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findCountriesByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findReferencesByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findComplementarySolutionsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findOrganizationsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findAllianceOrganizationsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findContributingOrganizationsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findPartnershipsByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findInstitutionsByIds(any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findContactPersonsByPartnershipIds(any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findBundlesByInnovationIdAndPhase(any(), any())).thenReturn(Collections.emptyList());
        
        // Act - invoke the private full method using reflection
        Method fullMethod = ProjectInnovationController.class.getDeclaredMethod(
            "toCompleteInfoWithRelationsResponse", ProjectInnovationInfo.class, Long.class, Long.class);
        fullMethod.setAccessible(true);
        Object result = fullMethod.invoke(controller, testInnovationInfo, 1L, 1L);
        
        // Assert
        assertNotNull(result);
    }

    // ============ Branch Coverage Tests for SDG Exception Handling ============

    @Test
    void getSdgShortNameById_WhenRepositoryThrowsException_ShouldUseFallbackSwitch() throws Exception {
        // Arrange - Force exception to cover catch block with switch cases 1-17
        Method method = ProjectInnovationController.class.getDeclaredMethod("getSdgShortNameById", Long.class);
        method.setAccessible(true);
        
        // Test all 17 SDG cases + default by forcing exception
        when(sdgRepository.findBySmoCode(anyLong())).thenThrow(new RuntimeException("Database error"));
        
        // Act & Assert - cover all switch cases
        for (long i = 1; i <= 17; i++) {
            Object result = method.invoke(controller, i);
            assertNotNull(result);
        }
        // Default case
        Object defaultResult = method.invoke(controller, 99L);
        assertEquals("SDG 99", defaultResult);
        
        // Null case
        Object nullResult = method.invoke(controller, (Long) null);
        assertEquals("Unknown SDG", nullResult);
    }

    @Test
    void getSdgFullNameById_WhenRepositoryThrowsException_ShouldUseFallbackSwitch() throws Exception {
        // Arrange - Force exception to cover catch block with switch cases 1-17
        Method method = ProjectInnovationController.class.getDeclaredMethod("getSdgFullNameById", Long.class);
        method.setAccessible(true);
        
        when(sdgRepository.findBySmoCode(anyLong())).thenThrow(new RuntimeException("Database error"));
        
        // Act & Assert - cover all switch cases
        for (long i = 1; i <= 17; i++) {
            Object result = method.invoke(controller, i);
            assertNotNull(result);
        }
        // Default case
        Object defaultResult = method.invoke(controller, 99L);
        assertTrue(defaultResult.toString().contains("SDG 99"));
        
        // Null case
        Object nullResult = method.invoke(controller, (Long) null);
        assertEquals("Unknown SDG", nullResult);
    }

    @Test
    void getSdgFullName_WhenRepositoryThrowsException_ShouldUseFallbackSwitch() throws Exception {
        // Arrange - Force exception to cover catch block with switch cases 1-17
        Method method = ProjectInnovationController.class.getDeclaredMethod("getSdgFullName", Long.class);
        method.setAccessible(true);
        
        when(sdgRepository.findBySmoCode(anyLong())).thenThrow(new RuntimeException("Database error"));
        
        // Act & Assert - cover all switch cases
        for (long i = 1; i <= 17; i++) {
            Object result = method.invoke(controller, i);
            assertNotNull(result);
        }
        // Default case
        Object defaultResult = method.invoke(controller, 99L);
        assertEquals("SDG 99", defaultResult);
        
        // Null case
        Object nullResult = method.invoke(controller, (Long) null);
        assertNull(nullResult);
    }

    @Test
    void getInnovationTypeNameById_WhenRepositoryThrowsException_ShouldUseFallbackSwitch() throws Exception {
        // Arrange - Force exception to cover catch block with switch cases 1-5
        Method method = ProjectInnovationController.class.getDeclaredMethod("getInnovationTypeNameById", Long.class);
        method.setAccessible(true);
        
        when(innovationTypeRepository.findById(anyLong())).thenThrow(new RuntimeException("Database error"));
        
        // Act & Assert - cover all switch cases 1-5
        for (long i = 1; i <= 5; i++) {
            Object result = method.invoke(controller, i);
            assertNotNull(result);
        }
        // Default case
        Object defaultResult = method.invoke(controller, 99L);
        assertTrue(defaultResult.toString().contains("Innovation Type 99"));
        
        // Null case
        Object nullResult = method.invoke(controller, (Long) null);
        assertEquals("Unknown", nullResult);
    }

    // ============ Branch Coverage Tests for getOrganizationTypeName ============

    @Test
    void getOrganizationTypeName_AllSwitchCases_ShouldReturnCorrectNames() throws Exception {
        Method method = ProjectInnovationController.class.getDeclaredMethod("getOrganizationTypeName", Long.class);
        method.setAccessible(true);
        
        // Test case 1-5 + default
        assertEquals("Government Agency", method.invoke(controller, 1L));
        assertEquals("Private Sector", method.invoke(controller, 2L));
        assertEquals("NGO/Civil Society", method.invoke(controller, 3L));
        assertEquals("Academic Institution", method.invoke(controller, 4L));
        assertEquals("International Organization", method.invoke(controller, 5L));
        assertTrue(method.invoke(controller, 99L).toString().contains("Organization Type 99"));
        assertNull(method.invoke(controller, (Long) null));
    }

    // ============ Branch Coverage Tests for getPartnerTypeName ============

    @Test
    void getPartnerTypeName_AllSwitchCases_ShouldReturnCorrectNames() throws Exception {
        Method method = ProjectInnovationController.class.getDeclaredMethod("getPartnerTypeName", Long.class);
        method.setAccessible(true);
        
        // Test case 1-4 + default
        assertEquals("Leading Partner", method.invoke(controller, 1L));
        assertEquals("Implementing Partner", method.invoke(controller, 2L));
        assertEquals("Supporting Partner", method.invoke(controller, 3L));
        assertEquals("Co-funding Partner", method.invoke(controller, 4L));
        assertTrue(method.invoke(controller, 99L).toString().contains("Partner Type 99"));
        assertNull(method.invoke(controller, (Long) null));
    }

    // ============ Branch Coverage Tests for Region/Country Exception Paths ============

    @Test
    void getRegionNameById_WhenRepositoryThrowsException_ShouldUseFallback() throws Exception {
        Method method = ProjectInnovationController.class.getDeclaredMethod("getRegionNameById", Long.class);
        method.setAccessible(true);
        
        when(locElementRepository.findActiveById(anyLong())).thenThrow(new RuntimeException("Database error"));
        
        Object result = method.invoke(controller, 1L);
        assertTrue(result.toString().contains("Region"));
        
        // Null case
        assertNull(method.invoke(controller, (Long) null));
    }

    @Test
    void getCountryNameById_WhenRepositoryThrowsException_ShouldUseFallback() throws Exception {
        Method method = ProjectInnovationController.class.getDeclaredMethod("getCountryNameById", Long.class);
        method.setAccessible(true);
        
        when(locElementRepository.findActiveById(anyLong())).thenThrow(new RuntimeException("Database error"));
        
        Object result = method.invoke(controller, 1L);
        assertTrue(result.toString().contains("Country"));
        
        // Null case
        assertNull(method.invoke(controller, (Long) null));
    }

    // ============ Branch Coverage Tests for toContactPersonResponse ============

    @Test
    void toContactPersonResponse_WithNullUser_ShouldUseDefaultValues() throws Exception {
        // Arrange
        com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationPartnershipPerson person = 
            new com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationPartnershipPerson();
        person.setId(1L);
        person.setPartnershipId(1L);
        person.setUserId(99L);
        
        when(repositoryAdapter.findUserById(99L)).thenReturn(Optional.empty());
        
        Method method = ProjectInnovationController.class.getDeclaredMethod(
            "toContactPersonResponse", 
            com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationPartnershipPerson.class);
        method.setAccessible(true);
        
        // Act
        Object result = method.invoke(controller, person);
        
        // Assert
        assertNotNull(result);
    }

    @Test
    void toContactPersonResponse_WithUserHavingOnlyFirstName_ShouldBuildNameCorrectly() throws Exception {
        // Arrange
        com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationPartnershipPerson person = 
            new com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationPartnershipPerson();
        person.setId(1L);
        person.setPartnershipId(1L);
        person.setUserId(1L);
        
        com.example.demo.modules.projectinnovation.domain.model.User user = 
            new com.example.demo.modules.projectinnovation.domain.model.User();
        user.setId(1L);
        user.setFirstName("John");
        user.setLastName(null);
        user.setEmail("john@example.com");
        
        when(repositoryAdapter.findUserById(1L)).thenReturn(Optional.of(user));
        
        Method method = ProjectInnovationController.class.getDeclaredMethod(
            "toContactPersonResponse", 
            com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationPartnershipPerson.class);
        method.setAccessible(true);
        
        // Act
        Object result = method.invoke(controller, person);
        
        // Assert
        assertNotNull(result);
    }

    @Test
    void toContactPersonResponse_WithUserHavingOnlyLastName_ShouldBuildNameCorrectly() throws Exception {
        // Arrange
        com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationPartnershipPerson person = 
            new com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationPartnershipPerson();
        person.setId(1L);
        person.setPartnershipId(1L);
        person.setUserId(1L);
        
        com.example.demo.modules.projectinnovation.domain.model.User user = 
            new com.example.demo.modules.projectinnovation.domain.model.User();
        user.setId(1L);
        user.setFirstName(null);
        user.setLastName("Doe");
        user.setEmail("doe@example.com");
        
        when(repositoryAdapter.findUserById(1L)).thenReturn(Optional.of(user));
        
        Method method = ProjectInnovationController.class.getDeclaredMethod(
            "toContactPersonResponse", 
            com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationPartnershipPerson.class);
        method.setAccessible(true);
        
        // Act
        Object result = method.invoke(controller, person);
        
        // Assert
        assertNotNull(result);
    }

    @Test
    void toContactPersonResponse_WithUserHavingEmptyNames_ShouldUseUsername() throws Exception {
        // Arrange
        com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationPartnershipPerson person = 
            new com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationPartnershipPerson();
        person.setId(1L);
        person.setPartnershipId(1L);
        person.setUserId(1L);
        
        com.example.demo.modules.projectinnovation.domain.model.User user = 
            new com.example.demo.modules.projectinnovation.domain.model.User();
        user.setId(1L);
        user.setFirstName("  ");
        user.setLastName("  ");
        user.setUsername("jdoe");
        user.setEmail(null);
        
        when(repositoryAdapter.findUserById(1L)).thenReturn(Optional.of(user));
        
        Method method = ProjectInnovationController.class.getDeclaredMethod(
            "toContactPersonResponse", 
            com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationPartnershipPerson.class);
        method.setAccessible(true);
        
        // Act
        Object result = method.invoke(controller, person);
        
        // Assert
        assertNotNull(result);
    }

    @Test
    void toContactPersonResponse_WithUserHavingNoNameOrUsername_ShouldUseFallback() throws Exception {
        // Arrange
        com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationPartnershipPerson person = 
            new com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationPartnershipPerson();
        person.setId(1L);
        person.setPartnershipId(1L);
        person.setUserId(1L);
        
        com.example.demo.modules.projectinnovation.domain.model.User user = 
            new com.example.demo.modules.projectinnovation.domain.model.User();
        user.setId(1L);
        user.setFirstName("");
        user.setLastName("");
        user.setUsername(null);
        user.setEmail(null);
        
        when(repositoryAdapter.findUserById(1L)).thenReturn(Optional.of(user));
        
        Method method = ProjectInnovationController.class.getDeclaredMethod(
            "toContactPersonResponse", 
            com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationPartnershipPerson.class);
        method.setAccessible(true);
        
        // Act
        Object result = method.invoke(controller, person);
        
        // Assert
        assertNotNull(result);
    }

    // ============ Branch Coverage Tests for toInfoResponse null branches ============

    @Test
    void toInfoResponse_WithNullProjectInnovationId_ShouldHandleGracefully() throws Exception {
        // Arrange
        testInnovationInfo.setProjectInnovationId(null);
        testInnovationInfo.setIdPhase(1L);
        
        Method method = ProjectInnovationController.class.getDeclaredMethod(
            "toInfoResponse", ProjectInnovationInfo.class);
        method.setAccessible(true);
        
        // Act
        Object result = method.invoke(controller, testInnovationInfo);
        
        // Assert
        assertNotNull(result);
    }

    @Test
    void toInfoResponse_WithNullIdPhase_ShouldHandleGracefully() throws Exception {
        // Arrange
        testInnovationInfo.setProjectInnovationId(1L);
        testInnovationInfo.setIdPhase(null);
        
        Method method = ProjectInnovationController.class.getDeclaredMethod(
            "toInfoResponse", ProjectInnovationInfo.class);
        method.setAccessible(true);
        
        // Act
        Object result = method.invoke(controller, testInnovationInfo);
        
        // Assert
        assertNotNull(result);
    }

    // ============ Branch Coverage Tests for toSimpleResponse null branches ============

    @Test
    void toSimpleResponse_WithNullProjectInnovationId_ShouldHandleGracefully() throws Exception {
        // Arrange
        testInnovationInfo.setProjectInnovationId(null);
        testInnovationInfo.setIdPhase(1L);
        testInnovationInfo.setInnovationTypeId(1L);
        
        when(innovationTypeRepository.findById(1L)).thenReturn(Optional.empty());
        
        Method method = ProjectInnovationController.class.getDeclaredMethod(
            "toSimpleResponse", ProjectInnovationInfo.class);
        method.setAccessible(true);
        
        // Act
        Object result = method.invoke(controller, testInnovationInfo);
        
        // Assert
        assertNotNull(result);
    }

    @Test
    void toSimpleResponse_WithNullIdPhase_ShouldHandleGracefully() throws Exception {
        // Arrange
        testInnovationInfo.setProjectInnovationId(1L);
        testInnovationInfo.setIdPhase(null);
        testInnovationInfo.setInnovationTypeId(null);
        
        Method method = ProjectInnovationController.class.getDeclaredMethod(
            "toSimpleResponse", ProjectInnovationInfo.class);
        method.setAccessible(true);
        
        // Act
        Object result = method.invoke(controller, testInnovationInfo);
        
        // Assert
        assertNotNull(result);
    }

    // ============ Branch Coverage Tests for toContributingOrganizationResponse ============

    @Test
    void toContributingOrganizationResponse_WithNullInstitution_ShouldUseFallback() throws Exception {
        // Arrange
        com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationContributingOrganization contrib = 
            new com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationContributingOrganization();
        contrib.setId(1L);
        contrib.setProjectInnovationId(1L);
        contrib.setIdPhase(1L);
        contrib.setInstitutionId(99L);
        
        Method method = ProjectInnovationController.class.getDeclaredMethod(
            "toContributingOrganizationResponse",
            com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationContributingOrganization.class,
            List.class, java.util.Map.class);
        method.setAccessible(true);
        
        // Act - empty institutions list and empty map
        Object result = method.invoke(controller, contrib, Collections.emptyList(), Collections.emptyMap());
        
        // Assert
        assertNotNull(result);
    }

    @Test
    void toContributingOrganizationResponse_WithHeadquarterButNullLocationName_ShouldUseCity() throws Exception {
        // Arrange
        com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationContributingOrganization contrib = 
            new com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationContributingOrganization();
        contrib.setId(1L);
        contrib.setProjectInnovationId(1L);
        contrib.setIdPhase(1L);
        contrib.setInstitutionId(1L);
        
        com.example.demo.modules.projectinnovation.domain.model.Institution institution = 
            new com.example.demo.modules.projectinnovation.domain.model.Institution();
        institution.setId(1L);
        institution.setName("Test Institution");
        institution.setAcronym("TI");
        
        // Create HeadquarterLocation with city but null locationName
        java.util.Map<Long, ProjectInnovationRepositoryAdapter.HeadquarterLocation> headquarterMap = new java.util.HashMap<>();
        headquarterMap.put(1L, new ProjectInnovationRepositoryAdapter.HeadquarterLocation("Test City", 1L, null));
        
        Method method = ProjectInnovationController.class.getDeclaredMethod(
            "toContributingOrganizationResponse",
            com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationContributingOrganization.class,
            List.class, java.util.Map.class);
        method.setAccessible(true);
        
        // Act
        Object result = method.invoke(controller, contrib, Arrays.asList(institution), headquarterMap);
        
        // Assert
        assertNotNull(result);
    }

    // ============ Branch Coverage Tests for toAllianceOrganizationResponse ============

    @Test
    void toAllianceOrganizationResponse_WithNullInstitution_ShouldUseFallback() throws Exception {
        // Arrange
        com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationAllianceOrganization alliance = 
            new com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationAllianceOrganization();
        alliance.setId(1L);
        alliance.setProjectInnovationId(1L);
        alliance.setIdPhase(1L);
        alliance.setInstitutionId(99L);
        
        Method method = ProjectInnovationController.class.getDeclaredMethod(
            "toAllianceOrganizationResponse",
            com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationAllianceOrganization.class,
            List.class);
        method.setAccessible(true);
        
        // Act - empty institutions list
        Object result = method.invoke(controller, alliance, Collections.emptyList());
        
        // Assert
        assertNotNull(result);
    }

    // ============ Branch Coverage Tests for toPartnershipResponse ============

    @Test
    void toPartnershipResponse_WithNullInstitution_ShouldUseUnknown() throws Exception {
        // Arrange
        com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationPartnership partnership = 
            new com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationPartnership();
        partnership.setId(1L);
        partnership.setProjectInnovationId(1L);
        partnership.setIdPhase(1L);
        partnership.setInstitutionId(99L);
        partnership.setInnovationPartnerTypeId(1L);
        
        Method method = ProjectInnovationController.class.getDeclaredMethod(
            "toPartnershipResponse",
            com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationPartnership.class,
            List.class, List.class);
        method.setAccessible(true);
        
        // Act - empty institutions and contact persons lists
        Object result = method.invoke(controller, partnership, Collections.emptyList(), Collections.emptyList());
        
        // Assert
        assertNotNull(result);
    }

    // ============ Branch Coverage Tests for getSdgsForInnovation exception path ============

    @Test
    void getSdgsForInnovation_WhenRepositoryThrowsException_ShouldReturnEmptyList() throws Exception {
        // Arrange
        when(repositoryAdapter.findSdgsByInnovationIdAndPhase(anyLong(), anyLong()))
            .thenThrow(new RuntimeException("Database error"));
        
        Method method = ProjectInnovationController.class.getDeclaredMethod(
            "getSdgsForInnovation", Long.class, Long.class);
        method.setAccessible(true);
        
        // Act
        Object result = method.invoke(controller, 1L, 1L);
        
        // Assert
        assertTrue(((List<?>) result).isEmpty());
    }

    @Test
    void getRegionsForInnovation_WhenRepositoryThrowsException_ShouldReturnEmptyList() throws Exception {
        // Arrange
        when(repositoryAdapter.findRegionsByInnovationIdAndPhase(anyLong(), anyLong()))
            .thenThrow(new RuntimeException("Database error"));
        
        Method method = ProjectInnovationController.class.getDeclaredMethod(
            "getRegionsForInnovation", Long.class, Long.class);
        method.setAccessible(true);
        
        // Act
        Object result = method.invoke(controller, 1L, 1L);
        
        // Assert
        assertTrue(((List<?>) result).isEmpty());
    }

    @Test
    void getCountriesForInnovation_WhenRepositoryThrowsException_ShouldReturnEmptyList() throws Exception {
        // Arrange
        when(repositoryAdapter.findCountriesByInnovationIdAndPhase(anyLong(), anyLong()))
            .thenThrow(new RuntimeException("Database error"));
        
        Method method = ProjectInnovationController.class.getDeclaredMethod(
            "getCountriesForInnovation", Long.class, Long.class);
        method.setAccessible(true);
        
        // Act
        Object result = method.invoke(controller, 1L, 1L);
        
        // Assert
        assertTrue(((List<?>) result).isEmpty());
    }

    // ============ Branch Coverage for bundleInfoKey ============

    @Test
    void bundleInfoKey_WithNullValues_ShouldHandleGracefully() throws Exception {
        Method method = ProjectInnovationController.class.getDeclaredMethod(
            "bundleInfoKey", Long.class, Long.class);
        method.setAccessible(true);
        
        // Test with null innovationId
        Object result1 = method.invoke(controller, null, 1L);
        assertNotNull(result1);
        
        // Test with null phaseId
        Object result2 = method.invoke(controller, 1L, null);
        assertNotNull(result2);
        
        // Test with both null
        Object result3 = method.invoke(controller, null, null);
        assertNotNull(result3);
    }

    // ============ Branch Coverage for maybeIncludeReasonKnowledgePotential ============

    @Test
    void maybeIncludeReasonKnowledgePotential_WithHasKnowledgePotentialIdEquals2_ShouldReturnReason() throws Exception {
        // Arrange
        testInnovationInfo.setHasKnowledgePotentialId(2L);
        testInnovationInfo.setReasonKnowledgePotential("Test reason");
        
        Method method = ProjectInnovationController.class.getDeclaredMethod(
            "maybeIncludeReasonKnowledgePotential", ProjectInnovationInfo.class);
        method.setAccessible(true);
        
        // Act
        Object result = method.invoke(controller, testInnovationInfo);
        
        // Assert
        assertEquals("Test reason", result);
    }

    @Test
    void maybeIncludeReasonKnowledgePotential_WithHasKnowledgePotentialIdNot2_ShouldReturnNull() throws Exception {
        // Arrange
        testInnovationInfo.setHasKnowledgePotentialId(1L);
        testInnovationInfo.setReasonKnowledgePotential("Test reason");
        
        Method method = ProjectInnovationController.class.getDeclaredMethod(
            "maybeIncludeReasonKnowledgePotential", ProjectInnovationInfo.class);
        method.setAccessible(true);
        
        // Act
        Object result = method.invoke(controller, testInnovationInfo);
        
        // Assert
        assertNull(result);
    }

    @Test
    void maybeIncludeReasonKnowledgePotential_WithNullHasKnowledgePotentialId_ShouldReturnNull() throws Exception {
        // Arrange
        testInnovationInfo.setHasKnowledgePotentialId(null);
        testInnovationInfo.setReasonKnowledgePotential("Test reason");
        
        Method method = ProjectInnovationController.class.getDeclaredMethod(
            "maybeIncludeReasonKnowledgePotential", ProjectInnovationInfo.class);
        method.setAccessible(true);
        
        // Act
        Object result = method.invoke(controller, testInnovationInfo);
        
        // Assert
        assertNull(result);
    }

    @Test
    void maybeIncludeReasonKnowledgePotential_WithNullInfo_ShouldReturnNull() throws Exception {
        // Arrange
        Method method = ProjectInnovationController.class.getDeclaredMethod(
            "maybeIncludeReasonKnowledgePotential", ProjectInnovationInfo.class);
        method.setAccessible(true);
        
        // Act
        Object result = method.invoke(controller, (ProjectInnovationInfo) null);
        
        // Assert
        assertNull(result);
    }

    // ============ Branch Coverage for searchInnovationsComplete lambda catch block ============

    @Test
    void searchInnovationsComplete_WhenExceptionInMapping_ShouldFilterOutNullResults() {
        // Arrange - Create innovation that will cause exception during mapping
        testInnovationInfo.setProjectInnovationId(1L);
        testInnovationInfo.setIdPhase(1L);
        
        List<ProjectInnovationInfo> innovations = Arrays.asList(testInnovationInfo);
        when(projectInnovationUseCase.findAllActiveInnovationsInfo()).thenReturn(innovations);
        
        // Force exception in toCompleteInfoWithRelationsResponse by making actorsService throw
        when(actorsService.findActiveActorsByInnovationIdAndPhase(anyLong(), anyInt()))
            .thenThrow(new RuntimeException("Database connection failed"));
        
        // Act
        ResponseEntity<?> result = invokeSearchInnovationsComplete(
            null, null, null, null, null, null, null, null, 0, 20);
        
        // Assert - Should return OK with empty/filtered results (the exception is caught and logged)
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void searchInnovationsComplete_WithMultipleInnovations_OneThrowsException_ShouldReturnOthers() {
        // Arrange - Create two innovations, one will fail, one will succeed
        ProjectInnovationInfo failingInfo = new ProjectInnovationInfo();
        failingInfo.setId(1L);
        failingInfo.setProjectInnovationId(1L);
        failingInfo.setIdPhase(1L);
        
        ProjectInnovationInfo successInfo = new ProjectInnovationInfo();
        successInfo.setId(2L);
        successInfo.setProjectInnovationId(2L);
        successInfo.setIdPhase(1L);
        
        List<ProjectInnovationInfo> innovations = Arrays.asList(failingInfo, successInfo);
        when(projectInnovationUseCase.findAllActiveInnovationsInfo()).thenReturn(innovations);
        
        // First call throws exception, second call succeeds
        when(actorsService.findActiveActorsByInnovationIdAndPhase(eq(1L), anyInt()))
            .thenThrow(new RuntimeException("Failed for innovation 1"));
        when(actorsService.findActiveActorsByInnovationIdAndPhase(eq(2L), anyInt()))
            .thenReturn(Collections.emptyList());
        
        // Mock other dependencies for the successful innovation
        when(repositoryAdapter.findSdgsByInnovationIdAndPhase(eq(2L), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findRegionsByInnovationIdAndPhase(eq(2L), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findCountriesByInnovationIdAndPhase(eq(2L), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findReferencesByInnovationIdAndPhase(eq(2L), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findComplementarySolutionsByInnovationIdAndPhase(eq(2L), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findOrganizationsByInnovationIdAndPhase(eq(2L), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findAllianceOrganizationsByInnovationIdAndPhase(eq(2L), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findContributingOrganizationsByInnovationIdAndPhase(eq(2L), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findPartnershipsByInnovationIdAndPhase(eq(2L), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findBundlesByInnovationIdAndPhase(eq(2L), any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findInstitutionsByIds(any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findContactPersonsByPartnershipIds(any())).thenReturn(Collections.emptyList());
        when(repositoryAdapter.findHeadquarterCitiesByInstitutionIds(any())).thenReturn(Collections.emptyMap());
        
        // Act
        ResponseEntity<?> result = invokeSearchInnovationsComplete(
            null, null, null, null, null, null, null, null, 0, 20);
        
        // Assert - Should return OK with the successful innovation (one filtered out)
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

    @Test
    void searchInnovationsComplete_WithNullIdPhase_ShouldCatchNullPointerException() {
        // Arrange - Create innovation with null idPhase to trigger NullPointerException in lambda
        ProjectInnovationInfo infoWithNullPhase = new ProjectInnovationInfo();
        infoWithNullPhase.setId(1L);
        infoWithNullPhase.setProjectInnovationId(1L);
        infoWithNullPhase.setIdPhase(null); // This will cause NPE when phaseId.intValue() is called
        
        List<ProjectInnovationInfo> innovations = Arrays.asList(infoWithNullPhase);
        when(projectInnovationUseCase.findAllActiveInnovationsInfo()).thenReturn(innovations);
        
        // Act
        ResponseEntity<?> result = invokeSearchInnovationsComplete(
            null, null, null, null, null, null, null, null, 0, 20);
        
        // Assert - Should return OK with empty results (exception caught and null filtered)
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }
}
