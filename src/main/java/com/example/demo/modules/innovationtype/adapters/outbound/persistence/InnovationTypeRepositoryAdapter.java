package com.example.demo.modules.innovationtype.adapters.outbound.persistence;

import com.example.demo.modules.innovationtype.application.port.outbound.InnovationTypeRepositoryPort;
import com.example.demo.modules.innovationtype.domain.model.InnovationType;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Repository adapter implementation for InnovationType
 * Implements the repository port using JPA repository
 */
@Component
public class InnovationTypeRepositoryAdapter implements InnovationTypeRepositoryPort {
    
    private final InnovationTypeJpaRepository innovationTypeJpaRepository;
    
    public InnovationTypeRepositoryAdapter(InnovationTypeJpaRepository innovationTypeJpaRepository) {
        this.innovationTypeJpaRepository = innovationTypeJpaRepository;
    }
    
        @Override
    public List<InnovationType> findAll() {
        return innovationTypeJpaRepository.findAll();
    }
    
    @Override
    public Optional<InnovationType> findById(Long id) {
        return innovationTypeJpaRepository.findById(id);
    }
}