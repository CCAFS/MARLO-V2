package com.example.demo.modules.sustainabledevelopmentgoals.application.port.outbound;

import com.example.demo.modules.sustainabledevelopmentgoals.domain.model.SustainableDevelopmentGoal;

import java.util.List;
import java.util.Optional;

public interface SustainableDevelopmentGoalRepositoryPort {
    
    List<SustainableDevelopmentGoal> findAll();
    
    Optional<SustainableDevelopmentGoal> findById(Long id);
    
    Optional<SustainableDevelopmentGoal> findBySmoCode(Long smoCode);
}