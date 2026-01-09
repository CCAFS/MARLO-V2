package com.example.demo.modules.innovationtype.application.service;

import com.example.demo.modules.innovationtype.application.port.outbound.InnovationTypeRepositoryPort;
import com.example.demo.modules.innovationtype.domain.model.InnovationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InnovationTypeServiceTest {

    @Mock
    private InnovationTypeRepositoryPort innovationTypeRepositoryPort;

    @InjectMocks
    private InnovationTypeService innovationTypeService;

    private InnovationType testInnovationType;

    @BeforeEach
    void setUp() {
        testInnovationType = new InnovationType();
        testInnovationType.setId(1L);
        testInnovationType.setName("Test Innovation Type");
        testInnovationType.setDefinition("Test Definition");
        testInnovationType.setIsOldType(false);
    }

    @Test
    void findAllInnovationTypes_ShouldReturnList() {
        // Arrange
        List<InnovationType> expected = Arrays.asList(testInnovationType);
        when(innovationTypeRepositoryPort.findAll()).thenReturn(expected);

        // Act
        List<InnovationType> result = innovationTypeService.findAllInnovationTypes();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testInnovationType.getId(), result.get(0).getId());
        assertEquals(testInnovationType.getName(), result.get(0).getName());
        verify(innovationTypeRepositoryPort).findAll();
    }

    @Test
    void findAllInnovationTypes_WhenEmpty_ShouldReturnEmptyList() {
        // Arrange
        when(innovationTypeRepositoryPort.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<InnovationType> result = innovationTypeService.findAllInnovationTypes();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(innovationTypeRepositoryPort).findAll();
    }

    @Test
    void findAllInnovationTypes_WhenMultipleTypes_ShouldReturnAll() {
        // Arrange
        InnovationType type2 = new InnovationType();
        type2.setId(2L);
        type2.setName("Type 2");
        
        List<InnovationType> expected = Arrays.asList(testInnovationType, type2);
        when(innovationTypeRepositoryPort.findAll()).thenReturn(expected);

        // Act
        List<InnovationType> result = innovationTypeService.findAllInnovationTypes();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(innovationTypeRepositoryPort).findAll();
    }

    @Test
    void findInnovationTypeById_WhenExists_ShouldReturnOptional() {
        // Arrange
        when(innovationTypeRepositoryPort.findById(1L)).thenReturn(Optional.of(testInnovationType));

        // Act
        Optional<InnovationType> result = innovationTypeService.findInnovationTypeById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(testInnovationType.getId(), result.get().getId());
        assertEquals(testInnovationType.getName(), result.get().getName());
        verify(innovationTypeRepositoryPort).findById(1L);
    }

    @Test
    void findInnovationTypeById_WhenNotExists_ShouldReturnEmpty() {
        // Arrange
        when(innovationTypeRepositoryPort.findById(999L)).thenReturn(Optional.empty());

        // Act
        Optional<InnovationType> result = innovationTypeService.findInnovationTypeById(999L);

        // Assert
        assertFalse(result.isPresent());
        verify(innovationTypeRepositoryPort).findById(999L);
    }
}
