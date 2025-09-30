package com.example.demo.modules.projectinnovation.application.port.outbound;

import com.example.demo.modules.projectinnovation.domain.model.ProjectInnovation;
import com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationInfo;

import java.util.List;
import java.util.Optional;

public interface ProjectInnovationRepositoryPort {
    // Métodos por defecto que filtran por is_active = true
    List<ProjectInnovation> findAll();
    Optional<ProjectInnovation> findById(Long id);
    ProjectInnovation save(ProjectInnovation projectInnovation);
    void deleteById(Long id);
    
    // Métodos adicionales con filtros específicos
    List<ProjectInnovation> findAllIncludingInactive();
    Optional<ProjectInnovation> findByIdIncludingInactive(Long id);
    List<ProjectInnovation> findByProjectId(Long projectId);
    List<ProjectInnovation> findByProjectIdAndActive(Long projectId, Boolean isActive);
    
    // Operations for ProjectInnovationInfo
    List<ProjectInnovationInfo> findProjectInnovationInfoByProjectInnovationId(Long projectInnovationId);
    Optional<ProjectInnovationInfo> findProjectInnovationInfoById(Long id);
    Optional<ProjectInnovationInfo> findProjectInnovationInfoByInnovationIdAndPhaseId(Long innovationId, Long phaseId);
    List<ProjectInnovationInfo> findProjectInnovationInfoByPhase(Long phaseId);
    ProjectInnovationInfo saveProjectInnovationInfo(ProjectInnovationInfo projectInnovationInfo);
    void deleteProjectInnovationInfoById(Long id);
}