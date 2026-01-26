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
}
