package com.example.demo.modules.projectinnovation.adapters.outbound.persistence;

import com.example.demo.modules.projectinnovation.application.port.outbound.ProjectInnovationRepositoryPort;
import com.example.demo.modules.projectinnovation.domain.model.ProjectInnovation;
import com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationInfo;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class ProjectInnovationRepositoryAdapter implements ProjectInnovationRepositoryPort {
    
    private final ProjectInnovationJpaRepository projectInnovationJpaRepository;
    private final ProjectInnovationInfoJpaRepository projectInnovationInfoJpaRepository;
    
    public ProjectInnovationRepositoryAdapter(
            ProjectInnovationJpaRepository projectInnovationJpaRepository,
            ProjectInnovationInfoJpaRepository projectInnovationInfoJpaRepository) {
        this.projectInnovationJpaRepository = projectInnovationJpaRepository;
        this.projectInnovationInfoJpaRepository = projectInnovationInfoJpaRepository;
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
    public List<ProjectInnovationInfo> findActiveInnovationsInfoWithFilters(Long phase, Integer readinessScale, Long innovationTypeId) {
        return projectInnovationInfoJpaRepository.findActiveInnovationsInfoWithFilters(phase, readinessScale, innovationTypeId);
    }
    
    @Override
    public List<ProjectInnovationInfo> findActiveInnovationsInfoBySdgFilters(Long innovationId, Long phase, Long sdgId) {
        return projectInnovationInfoJpaRepository.findActiveInnovationsInfoBySdgFilters(innovationId, phase, sdgId);
    }
    
    @Override
    public List<ProjectInnovationInfo> findAllActiveInnovationsInfo() {
        return projectInnovationInfoJpaRepository.findAllActiveInnovationsInfo();
    }
}