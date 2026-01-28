package com.example.demo.modules.projectinnovation.adapters.outbound.persistence;

import com.example.demo.modules.projectinnovation.domain.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectInnovationRepositoryAdapterTest {

    @Mock
    private ProjectInnovationJpaRepository projectInnovationJpaRepository;

    @Mock
    private ProjectInnovationInfoJpaRepository projectInnovationInfoJpaRepository;

    @Mock
    private ProjectInnovationSdgJpaRepository projectInnovationSdgJpaRepository;

    @Mock
    private ProjectInnovationRegionJpaRepository projectInnovationRegionJpaRepository;

    @Mock
    private ProjectInnovationCountryJpaRepository countryRepository;

    @Mock
    private ProjectInnovationOrganizationJpaRepository projectInnovationOrganizationJpaRepository;

    @Mock
    private ProjectInnovationAllianceOrganizationJpaRepository projectInnovationAllianceOrganizationJpaRepository;

    @Mock
    private ProjectInnovationPartnershipJpaRepository projectInnovationPartnershipJpaRepository;

    @Mock
    private ProjectInnovationPartnershipPersonJpaRepository projectInnovationPartnershipPersonJpaRepository;

    @Mock
    private ProjectInnovationContributingOrganizationJpaRepository projectInnovationContributingOrganizationJpaRepository;

    @Mock
    private ProjectInnovationReferenceJpaRepository projectInnovationReferenceJpaRepository;

    @Mock
    private ProjectInnovationBundleJpaRepository projectInnovationBundleJpaRepository;

    @Mock
    private ProjectInnovationComplementarySolutionJpaRepository projectInnovationComplementarySolutionJpaRepository;

    @Mock
    private DeliverableInfoJpaRepository deliverableInfoJpaRepository;

    @Mock
    private DeliverableDisseminationJpaRepository deliverableDisseminationJpaRepository;

    @Mock
    private InstitutionJpaRepository institutionJpaRepository;

    @Mock
    private UserJpaRepository userJpaRepository;

    @Mock
    private NamedParameterJdbcTemplate jdbcTemplate;

    @InjectMocks
    private ProjectInnovationRepositoryAdapter adapter;

    @Test
    void countDistinctCountries_ReturnsCountFromRepository() {
        // Arrange
        Long mockCount = 7L;
        Long testPhaseId = 100L;
        
        when(countryRepository.countDistinctCountriesByInnovationAndPhase(null, testPhaseId))
                .thenReturn(mockCount);

        // Act
        Long result = adapter.countDistinctCountries(null, testPhaseId);

        // Assert
        assertEquals(mockCount, result);
    }

    @Test
    void countDistinctInnovations_ReturnsCountFromRepository() {
        // Arrange
        Long mockCount = 42L;
        Long testPhaseId = 200L;
        
        when(countryRepository.countDistinctInnovationsByInnovationAndPhase(null, testPhaseId))
                .thenReturn(mockCount);

        // Act
        Long result = adapter.countDistinctInnovations(null, testPhaseId);

        // Assert
        assertEquals(mockCount, result);
    }

    @Test
    void countDistinctCountries_WithNullResult_ReturnsNull() {
        // Arrange
        Long testPhaseId = 300L;
        
        when(countryRepository.countDistinctCountriesByInnovationAndPhase(null, testPhaseId))
                .thenReturn(null);

        // Act
        Long result = adapter.countDistinctCountries(null, testPhaseId);

        // Assert
        assertNull(result);
    }

    @Test
    void countDistinctInnovations_WithZeroResult_ReturnsZero() {
        // Arrange
        Long testPhaseId = 400L;
        Long zeroCount = 0L;
        
        when(countryRepository.countDistinctInnovationsByInnovationAndPhase(null, testPhaseId))
                .thenReturn(zeroCount);

        // Act
        Long result = adapter.countDistinctInnovations(null, testPhaseId);

        // Assert
        assertEquals(zeroCount, result);
    }

    @Test
    void findAll_ShouldReturnActiveRecords() {
        // Arrange
        List<ProjectInnovation> mockInnovations = List.of(new ProjectInnovation(), new ProjectInnovation());
        when(projectInnovationJpaRepository.findAllActive()).thenReturn(mockInnovations);

        // Act
        List<ProjectInnovation> result = adapter.findAll();

        // Assert
        assertEquals(2, result.size());
        verify(projectInnovationJpaRepository).findAllActive();
    }

    @Test
    void findById_WithExistingId_ShouldReturnInnovation() {
        // Arrange
        Long id = 1L;
        ProjectInnovation innovation = new ProjectInnovation();
        innovation.setId(id);
        when(projectInnovationJpaRepository.findByIdAndIsActive(id, true)).thenReturn(Optional.of(innovation));

        // Act
        Optional<ProjectInnovation> result = adapter.findById(id);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(id, result.get().getId());
    }

    @Test
    void findById_WithNonExistingId_ShouldReturnEmpty() {
        // Arrange
        Long id = 999L;
        when(projectInnovationJpaRepository.findByIdAndIsActive(id, true)).thenReturn(Optional.empty());

        // Act
        Optional<ProjectInnovation> result = adapter.findById(id);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void save_ShouldCallRepository() {
        // Arrange
        ProjectInnovation innovation = new ProjectInnovation();
        when(projectInnovationJpaRepository.save(innovation)).thenReturn(innovation);

        // Act
        ProjectInnovation result = adapter.save(innovation);

        // Assert
        assertNotNull(result);
        verify(projectInnovationJpaRepository).save(innovation);
    }

    @Test
    void deleteById_WithExistingId_ShouldSoftDelete() {
        // Arrange
        Long id = 1L;
        ProjectInnovation innovation = new ProjectInnovation();
        innovation.setId(id);
        innovation.setIsActive(true);
        when(projectInnovationJpaRepository.findById(id)).thenReturn(Optional.of(innovation));
        when(projectInnovationJpaRepository.save(any(ProjectInnovation.class))).thenReturn(innovation);

        // Act
        adapter.deleteById(id);

        // Assert
        verify(projectInnovationJpaRepository).findById(id);
        verify(projectInnovationJpaRepository).save(argThat(pi -> !pi.getIsActive()));
    }

    @Test
    void deleteById_WithNonExistingId_ShouldNotCallSave() {
        // Arrange
        Long id = 999L;
        when(projectInnovationJpaRepository.findById(id)).thenReturn(Optional.empty());

        // Act
        adapter.deleteById(id);

        // Assert
        verify(projectInnovationJpaRepository).findById(id);
        verify(projectInnovationJpaRepository, never()).save(any());
    }

    @Test
    void findAllIncludingInactive_ShouldReturnAllRecords() {
        // Arrange
        List<ProjectInnovation> mockInnovations = List.of(new ProjectInnovation());
        when(projectInnovationJpaRepository.findAll()).thenReturn(mockInnovations);

        // Act
        List<ProjectInnovation> result = adapter.findAllIncludingInactive();

        // Assert
        assertEquals(1, result.size());
        verify(projectInnovationJpaRepository).findAll();
    }

    @Test
    void findByIdIncludingInactive_ShouldFindById() {
        // Arrange
        Long id = 1L;
        ProjectInnovation innovation = new ProjectInnovation();
        when(projectInnovationJpaRepository.findById(id)).thenReturn(Optional.of(innovation));

        // Act
        Optional<ProjectInnovation> result = adapter.findByIdIncludingInactive(id);

        // Assert
        assertTrue(result.isPresent());
    }

    @Test
    void findByProjectId_ShouldReturnActiveByProject() {
        // Arrange
        Long projectId = 1L;
        List<ProjectInnovation> mockInnovations = List.of(new ProjectInnovation());
        when(projectInnovationJpaRepository.findByProjectIdAndIsActive(projectId, true)).thenReturn(mockInnovations);

        // Act
        List<ProjectInnovation> result = adapter.findByProjectId(projectId);

        // Assert
        assertEquals(1, result.size());
    }

    @Test
    void findByProjectIdAndActive_ShouldFilterByActive() {
        // Arrange
        Long projectId = 1L;
        Boolean isActive = false;
        List<ProjectInnovation> mockInnovations = List.of(new ProjectInnovation());
        when(projectInnovationJpaRepository.findByProjectIdAndIsActive(projectId, isActive)).thenReturn(mockInnovations);

        // Act
        List<ProjectInnovation> result = adapter.findByProjectIdAndActive(projectId, isActive);

        // Assert
        assertEquals(1, result.size());
    }

    @Test
    void findProjectInnovationInfoByProjectInnovationId_ShouldReturnInfoList() {
        // Arrange
        Long innovationId = 1L;
        List<ProjectInnovationInfo> mockInfoList = List.of(new ProjectInnovationInfo());
        when(projectInnovationInfoJpaRepository.findByProjectInnovationId(innovationId)).thenReturn(mockInfoList);

        // Act
        List<ProjectInnovationInfo> result = adapter.findProjectInnovationInfoByProjectInnovationId(innovationId);

        // Assert
        assertEquals(1, result.size());
    }

    @Test
    void findProjectInnovationInfoById_ShouldReturnInfo() {
        // Arrange
        Long id = 1L;
        ProjectInnovationInfo info = new ProjectInnovationInfo();
        when(projectInnovationInfoJpaRepository.findById(id)).thenReturn(Optional.of(info));

        // Act
        Optional<ProjectInnovationInfo> result = adapter.findProjectInnovationInfoById(id);

        // Assert
        assertTrue(result.isPresent());
    }

    @Test
    void saveProjectInnovationInfo_ShouldCallRepository() {
        // Arrange
        ProjectInnovationInfo info = new ProjectInnovationInfo();
        when(projectInnovationInfoJpaRepository.save(info)).thenReturn(info);

        // Act
        ProjectInnovationInfo result = adapter.saveProjectInnovationInfo(info);

        // Assert
        assertNotNull(result);
        verify(projectInnovationInfoJpaRepository).save(info);
    }

    @Test
    void deleteProjectInnovationInfoById_ShouldCallRepository() {
        // Arrange
        Long id = 1L;
        doNothing().when(projectInnovationInfoJpaRepository).deleteById(id);

        // Act
        adapter.deleteProjectInnovationInfoById(id);

        // Assert
        verify(projectInnovationInfoJpaRepository).deleteById(id);
    }

    @Test
    void findSdgsByInnovationIdAndPhase_ShouldReturnActiveSdgs() {
        // Arrange
        Long innovationId = 1L;
        Long phaseId = 100L;
        List<ProjectInnovationSdg> mockSdgs = List.of(new ProjectInnovationSdg());
        when(projectInnovationSdgJpaRepository.findByInnovationIdAndIdPhaseAndIsActive(innovationId, phaseId, true))
                .thenReturn(mockSdgs);

        // Act
        List<ProjectInnovationSdg> result = adapter.findSdgsByInnovationIdAndPhase(innovationId, phaseId);

        // Assert
        assertEquals(1, result.size());
    }

    @Test
    void findRegionsByInnovationIdAndPhase_ShouldReturnRegions() {
        // Arrange
        Long innovationId = 1L;
        Long phaseId = 100L;
        List<ProjectInnovationRegion> mockRegions = List.of(new ProjectInnovationRegion());
        when(projectInnovationRegionJpaRepository.findByProjectInnovationIdAndIdPhase(innovationId, phaseId))
                .thenReturn(mockRegions);

        // Act
        List<ProjectInnovationRegion> result = adapter.findRegionsByInnovationIdAndPhase(innovationId, phaseId);

        // Assert
        assertEquals(1, result.size());
    }

    @Test
    void findCountriesByInnovationIdAndPhase_ShouldReturnCountries() {
        // Arrange
        Long innovationId = 1L;
        Long phaseId = 100L;
        List<ProjectInnovationCountry> mockCountries = List.of(new ProjectInnovationCountry());
        when(countryRepository.findByProjectInnovationIdAndIdPhase(innovationId, phaseId))
                .thenReturn(mockCountries);

        // Act
        List<ProjectInnovationCountry> result = adapter.findCountriesByInnovationIdAndPhase(innovationId, phaseId);

        // Assert
        assertEquals(1, result.size());
    }

    @Test
    void findOrganizationsByInnovationIdAndPhase_ShouldReturnOrganizations() {
        // Arrange
        Long innovationId = 1L;
        Long phaseId = 100L;
        List<ProjectInnovationOrganization> mockOrgs = List.of(new ProjectInnovationOrganization());
        when(projectInnovationOrganizationJpaRepository.findByProjectInnovationIdAndIdPhase(innovationId, phaseId))
                .thenReturn(mockOrgs);

        // Act
        List<ProjectInnovationOrganization> result = adapter.findOrganizationsByInnovationIdAndPhase(innovationId, phaseId);

        // Assert
        assertEquals(1, result.size());
    }

    @Test
    void findPartnershipsByInnovationIdAndPhase_ShouldReturnActivePartnerships() {
        // Arrange
        Long innovationId = 1L;
        Long phaseId = 100L;
        List<ProjectInnovationPartnership> mockPartnerships = List.of(new ProjectInnovationPartnership());
        when(projectInnovationPartnershipJpaRepository.findByProjectInnovationIdAndIdPhaseAndIsActive(innovationId, phaseId, true))
                .thenReturn(mockPartnerships);

        // Act
        List<ProjectInnovationPartnership> result = adapter.findPartnershipsByInnovationIdAndPhase(innovationId, phaseId);

        // Assert
        assertEquals(1, result.size());
    }

    @Test
    void findContactPersonsByPartnershipIds_ShouldReturnPersons() {
        // Arrange
        List<Long> partnershipIds = List.of(1L, 2L, 3L);
        List<ProjectInnovationPartnershipPerson> mockPersons = List.of(new ProjectInnovationPartnershipPerson());
        when(projectInnovationPartnershipPersonJpaRepository.findActivePersonsByPartnershipIds(partnershipIds))
                .thenReturn(mockPersons);

        // Act
        List<ProjectInnovationPartnershipPerson> result = adapter.findContactPersonsByPartnershipIds(partnershipIds);

        // Assert
        assertEquals(1, result.size());
    }

    @Test
    void findInstitutionsByIds_ShouldReturnInstitutions() {
        // Arrange
        List<Long> institutionIds = List.of(1L, 2L);
        List<Institution> mockInstitutions = List.of(new Institution());
        when(institutionJpaRepository.findActiveInstitutionsByIds(institutionIds)).thenReturn(mockInstitutions);

        // Act
        List<Institution> result = adapter.findInstitutionsByIds(institutionIds);

        // Assert
        assertEquals(1, result.size());
    }

    @Test
    void findHeadquarterCitiesByInstitutionIds_WithNullIds_ShouldReturnEmptyMap() {
        // Act
        Map<Long, ProjectInnovationRepositoryAdapter.HeadquarterLocation> result = 
                adapter.findHeadquarterCitiesByInstitutionIds(null);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void findHeadquarterCitiesByInstitutionIds_WithEmptyList_ShouldReturnEmptyMap() {
        // Act
        Map<Long, ProjectInnovationRepositoryAdapter.HeadquarterLocation> result = 
                adapter.findHeadquarterCitiesByInstitutionIds(Collections.emptyList());

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void findHeadquarterCitiesByInstitutionIds_WithOnlyNullIds_ShouldReturnEmptyMap() {
        // Arrange
        List<Long> nullIds = Arrays.asList(null, null);

        // Act
        Map<Long, ProjectInnovationRepositoryAdapter.HeadquarterLocation> result = 
                adapter.findHeadquarterCitiesByInstitutionIds(nullIds);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void findBundlesByInnovationIdAndPhase_ShouldReturnActiveBundles() {
        // Arrange
        Long innovationId = 1L;
        Long phaseId = 100L;
        List<ProjectInnovationBundle> mockBundles = List.of(new ProjectInnovationBundle());
        when(projectInnovationBundleJpaRepository.findByProjectInnovationIdAndIdPhaseAndIsActiveTrue(innovationId, phaseId))
                .thenReturn(mockBundles);

        // Act
        List<ProjectInnovationBundle> result = adapter.findBundlesByInnovationIdAndPhase(innovationId, phaseId);

        // Assert
        assertEquals(1, result.size());
    }

    @Test
    void findComplementarySolutionsByInnovationIdAndPhase_ShouldReturnActiveSolutions() {
        // Arrange
        Long innovationId = 1L;
        Long phaseId = 100L;
        List<ProjectInnovationComplementarySolution> mockSolutions = List.of(new ProjectInnovationComplementarySolution());
        when(projectInnovationComplementarySolutionJpaRepository.findByProjectInnovationIdAndIdPhaseAndIsActiveTrue(innovationId, phaseId))
                .thenReturn(mockSolutions);

        // Act
        List<ProjectInnovationComplementarySolution> result = adapter.findComplementarySolutionsByInnovationIdAndPhase(innovationId, phaseId);

        // Assert
        assertEquals(1, result.size());
    }

    @Test
    void findActiveInfoByInnovationIdsAndPhase_WithNullIds_ShouldReturnEmptyList() {
        // Act
        List<ProjectInnovationInfo> result = adapter.findActiveInfoByInnovationIdsAndPhase(null, 100L);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void findActiveInfoByInnovationIdsAndPhase_WithEmptyIds_ShouldReturnEmptyList() {
        // Act
        List<ProjectInnovationInfo> result = adapter.findActiveInfoByInnovationIdsAndPhase(Collections.emptyList(), 100L);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void findActiveInfoByInnovationIdsAndPhase_WithValidIds_ShouldReturnInfoList() {
        // Arrange
        List<Long> innovationIds = List.of(1L, 2L);
        Long phaseId = 100L;
        List<ProjectInnovationInfo> mockInfoList = List.of(new ProjectInnovationInfo());
        when(projectInnovationInfoJpaRepository.findActiveByProjectInnovationIdsAndPhase(innovationIds, phaseId))
                .thenReturn(mockInfoList);

        // Act
        List<ProjectInnovationInfo> result = adapter.findActiveInfoByInnovationIdsAndPhase(innovationIds, phaseId);

        // Assert
        assertEquals(1, result.size());
    }

    @Test
    void findUserById_ShouldReturnUser() {
        // Arrange
        Long userId = 1L;
        User user = new User();
        when(userJpaRepository.findById(userId)).thenReturn(Optional.of(user));

        // Act
        Optional<User> result = adapter.findUserById(userId);

        // Assert
        assertTrue(result.isPresent());
    }

    @Test
    void findUserById_WithNonExistingId_ShouldReturnEmpty() {
        // Arrange
        Long userId = 999L;
        when(userJpaRepository.findById(userId)).thenReturn(Optional.empty());

        // Act
        Optional<User> result = adapter.findUserById(userId);

        // Assert
        assertTrue(result.isEmpty());
    }

    @Test
    void findAverageScalingReadinessByPhase_WithValidPhase_ShouldReturnAverage() {
        // Arrange
        Long phaseId = 100L;
        when(projectInnovationInfoJpaRepository.findAverageScalingReadinessByPhaseOptimized(phaseId))
                .thenReturn(4.5);

        // Act
        Double result = adapter.findAverageScalingReadinessByPhase(phaseId);

        // Assert
        assertEquals(4.5, result);
    }

    @Test
    void findAverageScalingReadinessByPhase_WithNullResult_ShouldReturnZero() {
        // Arrange
        Long phaseId = 100L;
        when(projectInnovationInfoJpaRepository.findAverageScalingReadinessByPhaseOptimized(phaseId))
                .thenReturn(null);

        // Act
        Double result = adapter.findAverageScalingReadinessByPhase(phaseId);

        // Assert
        assertEquals(0.0, result);
    }

    @Test
    void headquarterLocation_Record_ShouldStoreValues() {
        // Arrange & Act
        ProjectInnovationRepositoryAdapter.HeadquarterLocation location = 
                new ProjectInnovationRepositoryAdapter.HeadquarterLocation("Nairobi", 1L, "Kenya");

        // Assert
        assertEquals("Nairobi", location.city());
        assertEquals(1L, location.locElementId());
        assertEquals("Kenya", location.locationName());
    }

    @Test
    void headquarterLocation_Record_WithNullValues_ShouldAcceptNulls() {
        // Arrange & Act
        ProjectInnovationRepositoryAdapter.HeadquarterLocation location = 
                new ProjectInnovationRepositoryAdapter.HeadquarterLocation(null, null, null);

        // Assert
        assertNull(location.city());
        assertNull(location.locElementId());
        assertNull(location.locationName());
    }

    @Test
    void headquarterLocation_Equals_WithSameValues_ShouldBeEqual() {
        // Arrange
        ProjectInnovationRepositoryAdapter.HeadquarterLocation loc1 = 
                new ProjectInnovationRepositoryAdapter.HeadquarterLocation("Nairobi", 1L, "Kenya");
        ProjectInnovationRepositoryAdapter.HeadquarterLocation loc2 = 
                new ProjectInnovationRepositoryAdapter.HeadquarterLocation("Nairobi", 1L, "Kenya");

        // Assert
        assertEquals(loc1, loc2);
        assertEquals(loc1.hashCode(), loc2.hashCode());
    }
}
