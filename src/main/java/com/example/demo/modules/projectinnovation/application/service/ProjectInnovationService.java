package com.example.demo.modules.projectinnovation.application.service;

import com.example.demo.modules.projectinnovation.application.port.inbound.ProjectInnovationUseCase;
import com.example.demo.modules.projectinnovation.application.port.outbound.ProjectInnovationRepositoryPort;
import com.example.demo.modules.projectinnovation.domain.model.ProjectInnovation;
import com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectInnovationService implements ProjectInnovationUseCase {
    
    private final ProjectInnovationRepositoryPort projectInnovationRepositoryPort;
    
    public ProjectInnovationService(ProjectInnovationRepositoryPort projectInnovationRepositoryPort) {
        this.projectInnovationRepositoryPort = projectInnovationRepositoryPort;
    }
    
    // Helper method for internal use - not from interface
    public List<ProjectInnovation> findAllProjectInnovations() {
        return projectInnovationRepositoryPort.findAll();
    }
    
    @Override
    public Optional<ProjectInnovation> findProjectInnovationById(Long id) {
        return projectInnovationRepositoryPort.findById(id);
    }
    
    @Override
    public ProjectInnovation createProjectInnovation(ProjectInnovation projectInnovation) {
        return projectInnovationRepositoryPort.save(projectInnovation);
    }
    
    @Override
    public ProjectInnovation updateProjectInnovation(Long id, ProjectInnovation projectInnovation) {
        if (!projectInnovationRepositoryPort.findById(id).isPresent()) {
            throw new RuntimeException("ProjectInnovation not found with id: " + id);
        }
        projectInnovation.setId(id);
        return projectInnovationRepositoryPort.save(projectInnovation);
    }
    
    @Override
    public void deleteProjectInnovation(Long id) {
        if (!projectInnovationRepositoryPort.findById(id).isPresent()) {
            throw new RuntimeException("ProjectInnovation not found with id: " + id);
        }
        // Soft delete: mark as inactive instead of physical deletion
        projectInnovationRepositoryPort.deleteById(id);
    }
    
    @Override
    public List<ProjectInnovation> findAllProjectInnovationsIncludingInactive() {
        return projectInnovationRepositoryPort.findAll(); // Only active records
    }
    
    @Override
    public List<ProjectInnovation> findProjectInnovationsByProjectId(Long projectId) {
        return projectInnovationRepositoryPort.findByProjectId(projectId);
    }
    
    @Override
    public List<ProjectInnovation> findProjectInnovationsByProjectIdAndActive(Long projectId, Boolean isActive) {
        return projectInnovationRepositoryPort.findByProjectIdAndActive(projectId, isActive);
    }
    
    @Override
    public ProjectInnovation activateProjectInnovation(Long id) {
        Optional<ProjectInnovation> optionalPI = projectInnovationRepositoryPort.findByIdIncludingInactive(id);
        if (!optionalPI.isPresent()) {
            throw new RuntimeException("ProjectInnovation not found with id: " + id);
        }
        ProjectInnovation projectInnovation = optionalPI.get();
        projectInnovation.setIsActive(true);
        return projectInnovationRepositoryPort.save(projectInnovation);
    }
    
    @Override
    public ProjectInnovation deactivateProjectInnovation(Long id) {
        Optional<ProjectInnovation> optionalPI = projectInnovationRepositoryPort.findByIdIncludingInactive(id);
        if (!optionalPI.isPresent()) {
            throw new RuntimeException("ProjectInnovation not found with id: " + id);
        }
        ProjectInnovation projectInnovation = optionalPI.get();
        projectInnovation.setIsActive(false);
        return projectInnovationRepositoryPort.save(projectInnovation);
    }
    
    @Override
    public List<ProjectInnovationInfo> findProjectInnovationInfoByProjectInnovationId(Long projectInnovationId) {
        return projectInnovationRepositoryPort.findProjectInnovationInfoByProjectInnovationId(projectInnovationId);
    }
    
    @Override
    public Optional<ProjectInnovationInfo> findProjectInnovationInfoById(Long id) {
        return projectInnovationRepositoryPort.findProjectInnovationInfoById(id);
    }
    
    @Override
    public Optional<ProjectInnovationInfo> findProjectInnovationInfoByInnovationIdAndPhaseId(Long innovationId, Long phaseId) {
        // First verify that the main innovation is active
        Optional<ProjectInnovation> innovation = projectInnovationRepositoryPort.findById(innovationId);
        if (innovation.isEmpty()) {
            return Optional.empty(); // Innovation does not exist or is inactive
        }
        
        return projectInnovationRepositoryPort.findProjectInnovationInfoByInnovationIdAndPhaseId(innovationId, phaseId);
    }
    
    @Override
    public ProjectInnovationInfo createProjectInnovationInfo(ProjectInnovationInfo projectInnovationInfo) {
        return projectInnovationRepositoryPort.saveProjectInnovationInfo(projectInnovationInfo);
    }
    
    @Override
    public ProjectInnovationInfo updateProjectInnovationInfo(Long id, ProjectInnovationInfo projectInnovationInfo) {
        if (!projectInnovationRepositoryPort.findProjectInnovationInfoById(id).isPresent()) {
            throw new RuntimeException("ProjectInnovationInfo not found with id: " + id);
        }
        projectInnovationInfo.setId(id);
        return projectInnovationRepositoryPort.saveProjectInnovationInfo(projectInnovationInfo);
    }
    
    @Override
    public void deleteProjectInnovationInfo(Long id) {
        if (!projectInnovationRepositoryPort.findProjectInnovationInfoById(id).isPresent()) {
            throw new RuntimeException("ProjectInnovationInfo not found with id: " + id);
        }
        projectInnovationRepositoryPort.deleteProjectInnovationInfoById(id);
    }
    
    @Override
    public List<ProjectInnovationInfo> findProjectInnovationInfoByPhase(Long phaseId) {
        return projectInnovationRepositoryPort.findProjectInnovationInfoByPhase(phaseId);
    }
    
    @Override
    public List<ProjectInnovation> findActiveInnovationsByPhase(Integer phaseId) {
        // This method finds active innovations by phase using actors table
        return projectInnovationRepositoryPort.findActiveInnovationsByPhase(phaseId);
    }
}