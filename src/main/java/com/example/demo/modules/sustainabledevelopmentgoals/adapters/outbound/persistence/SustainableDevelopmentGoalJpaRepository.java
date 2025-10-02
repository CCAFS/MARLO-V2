package com.example.demo.modules.sustainabledevelopmentgoals.adapters.outbound.persistence;

import com.example.demo.modules.sustainabledevelopmentgoals.domain.model.SustainableDevelopmentGoal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SustainableDevelopmentGoalJpaRepository extends JpaRepository<SustainableDevelopmentGoal, Long> {
    
    Optional<SustainableDevelopmentGoal> findBySmoCode(Long smoCode);
}