package com.example.demo.modules.sustainabledevelopmentgoals.application.service;

import com.example.demo.modules.sustainabledevelopmentgoals.application.port.inbound.SustainableDevelopmentGoalUseCase;
import com.example.demo.modules.sustainabledevelopmentgoals.application.port.outbound.SustainableDevelopmentGoalRepositoryPort;
import com.example.demo.modules.sustainabledevelopmentgoals.domain.model.SustainableDevelopmentGoal;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SustainableDevelopmentGoalService implements SustainableDevelopmentGoalUseCase {
    
    private final SustainableDevelopmentGoalRepositoryPort repositoryPort;
    
    public SustainableDevelopmentGoalService(SustainableDevelopmentGoalRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }
    
    @Override
    public List<SustainableDevelopmentGoal> findAllSustainableDevelopmentGoals() {
        return repositoryPort.findAll();
    }
    
    @Override
    public Optional<SustainableDevelopmentGoal> findSustainableDevelopmentGoalById(Long id) {
        return repositoryPort.findById(id);
    }
    
    @Override
    public Optional<SustainableDevelopmentGoal> findSustainableDevelopmentGoalBySmoCode(Long smoCode) {
        return repositoryPort.findBySmoCode(smoCode);
    }
}