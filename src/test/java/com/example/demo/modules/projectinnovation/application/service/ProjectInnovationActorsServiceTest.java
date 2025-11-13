package com.example.demo.modules.projectinnovation.application.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.example.demo.modules.projectinnovation.adapters.outbound.persistence.ProjectInnovationRepositoryAdapter;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProjectInnovationActorsServiceTest {

    @Mock
    private ProjectInnovationRepositoryAdapter repositoryAdapter;

    @InjectMocks
    private ProjectInnovationActorsService service;

    @Test
    void serviceExists_AndIsNotNull() {
        // Assert
        assertNotNull(service);
        assertNotNull(repositoryAdapter);
    }

    @Test 
    void serviceCanBeInstantiated_WithMockDependency() {
        // Act - Service is already instantiated by @InjectMocks
        // Assert
        assertTrue(service instanceof ProjectInnovationActorsService);
    }
}