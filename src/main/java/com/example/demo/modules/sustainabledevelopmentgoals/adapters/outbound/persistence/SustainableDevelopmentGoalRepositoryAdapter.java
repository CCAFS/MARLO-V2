package com.example.demo.modules.sustainabledevelopmentgoals.adapters.outbound.persistence;

import com.example.demo.modules.sustainabledevelopmentgoals.application.port.outbound.SustainableDevelopmentGoalRepositoryPort;
import com.example.demo.modules.sustainabledevelopmentgoals.domain.model.SustainableDevelopmentGoal;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class SustainableDevelopmentGoalRepositoryAdapter implements SustainableDevelopmentGoalRepositoryPort {
    
    private final SustainableDevelopmentGoalJpaRepository jpaRepository;
    
    public SustainableDevelopmentGoalRepositoryAdapter(SustainableDevelopmentGoalJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }
    
    @Override
    public List<SustainableDevelopmentGoal> findAll() {
        return jpaRepository.findAll();
    }
    
    @Override
    public Optional<SustainableDevelopmentGoal> findById(Long id) {
        return jpaRepository.findById(id);
    }
    
    @Override
    public Optional<SustainableDevelopmentGoal> findBySmoCode(Long smoCode) {
        return jpaRepository.findBySmoCode(smoCode);
    }
}