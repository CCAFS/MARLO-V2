package com.example.demo.modules.innovationtype.application.service;

import com.example.demo.modules.innovationtype.application.port.inbound.InnovationTypeUseCase;
import com.example.demo.modules.innovationtype.application.port.outbound.InnovationTypeRepositoryPort;
import com.example.demo.modules.innovationtype.domain.model.InnovationType;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service implementation for InnovationType use cases
 * Contains business logic for innovation type operations
 */
@Service
public class InnovationTypeService implements InnovationTypeUseCase {
    
    private final InnovationTypeRepositoryPort innovationTypeRepositoryPort;
    
    public InnovationTypeService(InnovationTypeRepositoryPort innovationTypeRepositoryPort) {
        this.innovationTypeRepositoryPort = innovationTypeRepositoryPort;
    }
    
    @Override
    public List<InnovationType> findAllInnovationTypes() {
        return innovationTypeRepositoryPort.findAll();
    }
    
    @Override
    public Optional<InnovationType> findInnovationTypeById(Long id) {
        return innovationTypeRepositoryPort.findById(id);
    }
}