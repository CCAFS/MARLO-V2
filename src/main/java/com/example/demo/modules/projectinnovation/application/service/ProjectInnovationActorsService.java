package com.example.demo.modules.projectinnovation.application.service;

import com.example.demo.modules.projectinnovation.adapters.outbound.persistence.ProjectInnovationActorsJpaRepository;
import com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationActors;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectInnovationActorsService {
    
    private final ProjectInnovationActorsJpaRepository actorsRepository;
    
    public ProjectInnovationActorsService(ProjectInnovationActorsJpaRepository actorsRepository) {
        this.actorsRepository = actorsRepository;
    }
    
    public List<ProjectInnovationActors> findActiveActorsByInnovationId(Long innovationId) {
        return actorsRepository.findByInnovationIdAndIsActiveTrue(innovationId);
    }
    
    public List<ProjectInnovationActors> findActiveActorsByPhase(Integer phaseId) {
        return actorsRepository.findByIdPhaseAndIsActiveTrue(phaseId);
    }
    
    public List<ProjectInnovationActors> findActiveActorsByInnovationIdAndPhase(Long innovationId, Integer phaseId) {
        return actorsRepository.findByInnovationIdAndIdPhaseAndIsActiveTrue(innovationId, phaseId);
    }
}