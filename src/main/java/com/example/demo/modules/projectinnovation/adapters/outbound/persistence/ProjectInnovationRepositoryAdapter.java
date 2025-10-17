package com.example.demo.modules.projectinnovation.adapters.outbound.persistence;

import com.example.demo.modules.projectinnovation.application.port.outbound.ProjectInnovationRepositoryPort;
import com.example.demo.modules.projectinnovation.domain.model.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProjectInnovationRepositoryAdapter implements ProjectInnovationRepositoryPort {
    
    private final ProjectInnovationJpaRepository projectInnovationJpaRepository;
    private final ProjectInnovationInfoJpaRepository projectInnovationInfoJpaRepository;
    private final ProjectInnovationSdgJpaRepository projectInnovationSdgJpaRepository;
    private final ProjectInnovationRegionJpaRepository projectInnovationRegionJpaRepository;
    private final ProjectInnovationCountryJpaRepository projectInnovationCountryJpaRepository;
    private final ProjectInnovationOrganizationJpaRepository projectInnovationOrganizationJpaRepository;
    private final ProjectInnovationPartnershipJpaRepository projectInnovationPartnershipJpaRepository;
    private final ProjectInnovationPartnershipPersonJpaRepository projectInnovationPartnershipPersonJpaRepository;
    private final ProjectInnovationContributingOrganizationJpaRepository projectInnovationContributingOrganizationJpaRepository;
    private final ProjectInnovationReferenceJpaRepository projectInnovationReferenceJpaRepository;
    private final InstitutionJpaRepository institutionJpaRepository;
    private final UserJpaRepository userJpaRepository;
    
    public ProjectInnovationRepositoryAdapter(
            ProjectInnovationJpaRepository projectInnovationJpaRepository,
            ProjectInnovationInfoJpaRepository projectInnovationInfoJpaRepository,
            ProjectInnovationSdgJpaRepository projectInnovationSdgJpaRepository,
            ProjectInnovationRegionJpaRepository projectInnovationRegionJpaRepository,
            ProjectInnovationCountryJpaRepository projectInnovationCountryJpaRepository,
            ProjectInnovationOrganizationJpaRepository projectInnovationOrganizationJpaRepository,
            ProjectInnovationPartnershipJpaRepository projectInnovationPartnershipJpaRepository,
            ProjectInnovationPartnershipPersonJpaRepository projectInnovationPartnershipPersonJpaRepository,
            ProjectInnovationContributingOrganizationJpaRepository projectInnovationContributingOrganizationJpaRepository,
            ProjectInnovationReferenceJpaRepository projectInnovationReferenceJpaRepository,
            InstitutionJpaRepository institutionJpaRepository,
            UserJpaRepository userJpaRepository) {
        this.projectInnovationJpaRepository = projectInnovationJpaRepository;
        this.projectInnovationInfoJpaRepository = projectInnovationInfoJpaRepository;
        this.projectInnovationSdgJpaRepository = projectInnovationSdgJpaRepository;
        this.projectInnovationRegionJpaRepository = projectInnovationRegionJpaRepository;
        this.projectInnovationCountryJpaRepository = projectInnovationCountryJpaRepository;
        this.projectInnovationOrganizationJpaRepository = projectInnovationOrganizationJpaRepository;
        this.projectInnovationPartnershipJpaRepository = projectInnovationPartnershipJpaRepository;
        this.projectInnovationPartnershipPersonJpaRepository = projectInnovationPartnershipPersonJpaRepository;
        this.projectInnovationContributingOrganizationJpaRepository = projectInnovationContributingOrganizationJpaRepository;
        this.projectInnovationReferenceJpaRepository = projectInnovationReferenceJpaRepository;
        this.institutionJpaRepository = institutionJpaRepository;
        this.userJpaRepository = userJpaRepository;
    }
    
    @Override
    public List<ProjectInnovation> findAll() {
        // By default only return active records
        return projectInnovationJpaRepository.findAllActive();
    }
    
    @Override
    public Optional<ProjectInnovation> findById(Long id) {
        // Only search active records by default
        return projectInnovationJpaRepository.findByIdAndIsActive(id, true);
    }
    
    @Override
    public ProjectInnovation save(ProjectInnovation projectInnovation) {
        return projectInnovationJpaRepository.save(projectInnovation);
    }
    
    @Override
    public void deleteById(Long id) {
        // Soft delete: mark as inactive instead of physical deletion
        Optional<ProjectInnovation> projectInnovation = projectInnovationJpaRepository.findById(id);
        if (projectInnovation.isPresent()) {
            ProjectInnovation pi = projectInnovation.get();
            pi.setIsActive(false);
            projectInnovationJpaRepository.save(pi);
        }
    }
    
    @Override
    public List<ProjectInnovation> findAllIncludingInactive() {
        return projectInnovationJpaRepository.findAll();
    }
    
    @Override
    public Optional<ProjectInnovation> findByIdIncludingInactive(Long id) {
        return projectInnovationJpaRepository.findById(id);
    }
    
    @Override
    public List<ProjectInnovation> findByProjectId(Long projectId) {
        return projectInnovationJpaRepository.findByProjectIdAndIsActive(projectId, true);
    }
    
    @Override
    public List<ProjectInnovation> findByProjectIdAndActive(Long projectId, Boolean isActive) {
        return projectInnovationJpaRepository.findByProjectIdAndIsActive(projectId, isActive);
    }
    
    @Override
    public List<ProjectInnovationInfo> findProjectInnovationInfoByProjectInnovationId(Long projectInnovationId) {
        return projectInnovationInfoJpaRepository.findByProjectInnovationId(projectInnovationId);
    }
    
    @Override
    public Optional<ProjectInnovationInfo> findProjectInnovationInfoById(Long id) {
        return projectInnovationInfoJpaRepository.findById(id);
    }
    
    @Override
    public Optional<ProjectInnovationInfo> findProjectInnovationInfoByInnovationIdAndPhaseId(Long innovationId, Long phaseId) {
        return Optional.ofNullable(projectInnovationInfoJpaRepository.findByProjectInnovationIdAndIdPhase(innovationId, phaseId));
    }
    
    @Override
    public List<ProjectInnovationInfo> findProjectInnovationInfoByPhase(Long phaseId) {
        // Only return information for active innovations
        return projectInnovationInfoJpaRepository.findByPhaseAndActiveInnovation(phaseId);
    }
    
    @Override
    public ProjectInnovationInfo saveProjectInnovationInfo(ProjectInnovationInfo projectInnovationInfo) {
        return projectInnovationInfoJpaRepository.save(projectInnovationInfo);
    }
    
    @Override
    public void deleteProjectInnovationInfoById(Long id) {
        projectInnovationInfoJpaRepository.deleteById(id);
    }
    
    @Override
    public List<ProjectInnovation> findActiveInnovationsByPhase(Integer phaseId) {
        return projectInnovationJpaRepository.findActiveInnovationsByPhase(phaseId);
    }
    
    @Override
    public List<ProjectInnovation> findActiveInnovationsWithFilters(Long phase, Integer readinessScale, Long innovationTypeId) {
        return projectInnovationJpaRepository.findActiveInnovationsWithFilters(phase, readinessScale, innovationTypeId);
    }
    
    @Override
    public List<ProjectInnovation> findActiveInnovationsBySdgFilters(Long innovationId, Long phase, Long sdgId) {
        return projectInnovationJpaRepository.findActiveInnovationsBySdgFilters(innovationId, phase, sdgId);
    }
    
    @Override
    public List<ProjectInnovation> findAllActiveInnovationsComplete() {
        return projectInnovationJpaRepository.findAllActiveInnovationsComplete();
    }
    
    // New methods that return ProjectInnovationInfo instead of ProjectInnovation
    @Override
    public List<ProjectInnovationInfo> findActiveInnovationsInfoWithFilters(Long phase, Integer readinessScale, Long innovationTypeId, List<Long> countryIds) {
        List<Long> normalizedCountryIds = (countryIds == null || countryIds.isEmpty()) ? null : countryIds;
        return projectInnovationInfoJpaRepository.findActiveInnovationsInfoWithFilters(phase, readinessScale, innovationTypeId, normalizedCountryIds);
    }
    
    @Override
    public List<ProjectInnovationInfo> findActiveInnovationsInfoBySdgFilters(Long innovationId, Long phase, Long sdgId, List<Long> countryIds) {
        List<Long> normalizedCountryIds = (countryIds == null || countryIds.isEmpty()) ? null : countryIds;
        return projectInnovationInfoJpaRepository.findActiveInnovationsInfoBySdgFilters(innovationId, phase, sdgId, normalizedCountryIds);
    }
    
    @Override
    public List<ProjectInnovationInfo> findAllActiveInnovationsInfo() {
        return projectInnovationInfoJpaRepository.findAllActiveInnovationsInfo();
    }
    
    // New methods for relationships
    public List<ProjectInnovationSdg> findSdgsByInnovationIdAndPhase(Long innovationId, Long phaseId) {
        return projectInnovationSdgJpaRepository.findByInnovationIdAndIdPhaseAndIsActive(innovationId, phaseId, true);
    }
    
    public List<ProjectInnovationRegion> findRegionsByInnovationIdAndPhase(Long innovationId, Long phaseId) {
        return projectInnovationRegionJpaRepository.findByProjectInnovationIdAndIdPhase(innovationId, phaseId);
    }
    
    public List<ProjectInnovationCountry> findCountriesByInnovationIdAndPhase(Long innovationId, Long phaseId) {
        return projectInnovationCountryJpaRepository.findByProjectInnovationIdAndIdPhase(innovationId, phaseId);
    }
    
    public List<ProjectInnovationOrganization> findOrganizationsByInnovationIdAndPhase(Long innovationId, Long phaseId) {
        return projectInnovationOrganizationJpaRepository.findByProjectInnovationIdAndIdPhase(innovationId, phaseId);
    }
    
    public List<ProjectInnovationPartnership> findPartnershipsByInnovationIdAndPhase(Long innovationId, Long phaseId) {
        return projectInnovationPartnershipJpaRepository.findByProjectInnovationIdAndIdPhaseAndIsActive(innovationId, phaseId, true);
    }
    
    public List<ProjectInnovationPartnershipPerson> findContactPersonsByPartnershipIds(List<Long> partnershipIds) {
        return projectInnovationPartnershipPersonJpaRepository.findActivePersonsByPartnershipIds(partnershipIds);
    }
    
    public List<Institution> findInstitutionsByIds(List<Long> institutionIds) {
        return institutionJpaRepository.findActiveInstitutionsByIds(institutionIds);
    }
    
    // Statistics methods for innovation and country counting
    public Long countDistinctCountries(Long innovationId, Long phaseId) {
        return projectInnovationCountryJpaRepository.countDistinctCountriesByInnovationAndPhase(innovationId, phaseId);
    }
    
    public Long countDistinctInnovations(Long innovationId, Long phaseId) {
        return projectInnovationCountryJpaRepository.countDistinctInnovationsByInnovationAndPhase(innovationId, phaseId);
    }
    
    /**
     * Calculate the average scaling readiness for innovations in a specific phase
     * OPTIMIZED: Using direct query instead of loading all entities and processing in memory
     * @param phaseId The phase ID to filter innovations
     * @return Average scaling readiness value, or 0.0 if no valid values found
     */
    public Double findAverageScalingReadinessByPhase(Long phaseId) {
        try {
            // OPTIMIZED: Direct database calculation instead of loading entities
            Double average = projectInnovationInfoJpaRepository.findAverageScalingReadinessByPhaseOptimized(phaseId);
            return average != null ? average : 0.0;
            
        } catch (Exception e) {
            // Fallback to original implementation if optimized query fails
            try {
                List<ProjectInnovationInfo> innovations = projectInnovationInfoJpaRepository.findByIdPhase(phaseId);
                
                List<Integer> readinessScales = innovations.stream()
                        .map(ProjectInnovationInfo::getReadinessScale)
                        .filter(java.util.Objects::nonNull)
                        .collect(java.util.stream.Collectors.toList());
                
                if (readinessScales.isEmpty()) {
                    return 0.0;
                }
                
                double sum = readinessScales.stream()
                        .mapToInt(Integer::intValue)
                        .sum();
                
                return sum / readinessScales.size();
                
            } catch (Exception fallbackException) {
                return 0.0;
            }
        }
    }
    
    /**
     * Find contributing organizations by innovation ID and phase ID
     */
    public List<ProjectInnovationContributingOrganization> findContributingOrganizationsByInnovationIdAndPhase(Long innovationId, Long phaseId) {
        return projectInnovationContributingOrganizationJpaRepository.findByProjectInnovationIdAndPhaseId(innovationId, phaseId);
    }
    
    /**
     * Find references associated with an innovation and phase.
     */
    public List<ProjectInnovationReference> findReferencesByInnovationIdAndPhase(Long innovationId, Long phaseId) {
        return projectInnovationReferenceJpaRepository.findByProjectInnovationIdAndIdPhase(innovationId, phaseId);
    }
    
    /**
     * Find user by ID
     */
    public Optional<User> findUserById(Long userId) {
        return userJpaRepository.findById(userId);
    }
}
