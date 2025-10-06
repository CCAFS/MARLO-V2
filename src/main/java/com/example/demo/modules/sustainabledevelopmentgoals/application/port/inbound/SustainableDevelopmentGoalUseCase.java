package com.example.demo.modules.sustainabledevelopmentgoals.application.port.inbound;

import com.example.demo.modules.sustainabledevelopmentgoals.domain.model.SustainableDevelopmentGoal;

import java.util.List;
import java.util.Optional;

public interface SustainableDevelopmentGoalUseCase {
    
    List<SustainableDevelopmentGoal> findAllSustainableDevelopmentGoals();
    
    Optional<SustainableDevelopmentGoal> findSustainableDevelopmentGoalById(Long id);
    
    Optional<SustainableDevelopmentGoal> findSustainableDevelopmentGoalBySmoCode(Long smoCode);
}