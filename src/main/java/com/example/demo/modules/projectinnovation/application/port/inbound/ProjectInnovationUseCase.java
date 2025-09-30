package com.example.demo.modules.projectinnovation.application.port.inbound;

import com.example.demo.modules.projectinnovation.domain.model.ProjectInnovation;
import com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationInfo;

import java.util.List;
import java.util.Optional;

public interface ProjectInnovationUseCase {
    // Métodos por defecto que solo devuelven registros activos (is_active = true)
    List<ProjectInnovation> findAllProjectInnovations();
    Optional<ProjectInnovation> findProjectInnovationById(Long id);
    ProjectInnovation createProjectInnovation(ProjectInnovation projectInnovation);
    ProjectInnovation updateProjectInnovation(Long id, ProjectInnovation projectInnovation);
    void deleteProjectInnovation(Long id); // Marcará como inactivo en lugar de eliminar
    
    // Métodos adicionales con filtros específicos
    List<ProjectInnovation> findAllProjectInnovationsIncludingInactive();
    List<ProjectInnovation> findProjectInnovationsByProjectId(Long projectId);
    List<ProjectInnovation> findProjectInnovationsByProjectIdAndActive(Long projectId, Boolean isActive);
    ProjectInnovation activateProjectInnovation(Long id);
    ProjectInnovation deactivateProjectInnovation(Long id);
    
    // Operations for ProjectInnovationInfo
    List<ProjectInnovationInfo> findProjectInnovationInfoByProjectInnovationId(Long projectInnovationId);
    Optional<ProjectInnovationInfo> findProjectInnovationInfoById(Long id);
    Optional<ProjectInnovationInfo> findProjectInnovationInfoByInnovationIdAndPhaseId(Long innovationId, Long phaseId);
    ProjectInnovationInfo createProjectInnovationInfo(ProjectInnovationInfo projectInnovationInfo);
    ProjectInnovationInfo updateProjectInnovationInfo(Long id, ProjectInnovationInfo projectInnovationInfo);
    void deleteProjectInnovationInfo(Long id);
}