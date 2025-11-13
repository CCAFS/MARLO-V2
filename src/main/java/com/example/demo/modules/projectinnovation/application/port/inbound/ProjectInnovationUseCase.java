package com.example.demo.modules.projectinnovation.application.port.inbound;

import com.example.demo.modules.projectinnovation.domain.model.ProjectInnovation;
import com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationInfo;

import java.util.List;
import java.util.Optional;

public interface ProjectInnovationUseCase {
    // Default methods that only return active records (is_active = true)
    Optional<ProjectInnovation> findProjectInnovationById(Long id);
    ProjectInnovation createProjectInnovation(ProjectInnovation projectInnovation);
    ProjectInnovation updateProjectInnovation(Long id, ProjectInnovation projectInnovation);
    void deleteProjectInnovation(Long id); // Will mark as inactive instead of deleting
    
    // Additional methods with specific filters
    List<ProjectInnovation> findAllProjectInnovationsIncludingInactive();
    List<ProjectInnovation> findProjectInnovationsByProjectId(Long projectId);
    List<ProjectInnovation> findProjectInnovationsByProjectIdAndActive(Long projectId, Boolean isActive);
    ProjectInnovation activateProjectInnovation(Long id);
    ProjectInnovation deactivateProjectInnovation(Long id);
    
    // Operations for ProjectInnovationInfo
    List<ProjectInnovationInfo> findProjectInnovationInfoByProjectInnovationId(Long projectInnovationId);
    Optional<ProjectInnovationInfo> findProjectInnovationInfoById(Long id);
    Optional<ProjectInnovationInfo> findProjectInnovationInfoByInnovationIdAndPhaseId(Long innovationId, Long phaseId);
    List<ProjectInnovationInfo> findProjectInnovationInfoByPhase(Long phaseId);
    ProjectInnovationInfo createProjectInnovationInfo(ProjectInnovationInfo projectInnovationInfo);
    ProjectInnovationInfo updateProjectInnovationInfo(Long id, ProjectInnovationInfo projectInnovationInfo);
    void deleteProjectInnovationInfo(Long id);
    
    // New method for finding innovations by phase using actors table
    List<ProjectInnovation> findActiveInnovationsByPhase(Integer phaseId);
    
    // Advanced filtering methods for complete innovation search (only active records)
    List<ProjectInnovation> findActiveInnovationsWithFilters(Long phase, Integer readinessScale, Long innovationTypeId);
    List<ProjectInnovation> findActiveInnovationsBySdgFilters(Long innovationId, Long phase, Long sdgId);
    List<ProjectInnovation> findAllActiveInnovationsComplete();
    
    // Advanced filtering methods that return complete ProjectInnovationInfo (only active records)
    List<ProjectInnovationInfo> findActiveInnovationsInfoWithFilters(Long phase, Integer readinessScale, Long innovationTypeId, List<Long> countryIds);
    List<ProjectInnovationInfo> findActiveInnovationsInfoBySdgFilters(Long innovationId, Long phase, Long sdgId, List<Long> countryIds);
    List<ProjectInnovationInfo> findAllActiveInnovationsInfo();
}
