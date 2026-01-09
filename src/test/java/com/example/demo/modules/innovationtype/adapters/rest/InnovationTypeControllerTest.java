package com.example.demo.modules.innovationtype.adapters.rest;

import com.example.demo.modules.innovationtype.adapters.rest.dto.InnovationTypeResponse;
import com.example.demo.modules.innovationtype.application.port.inbound.InnovationTypeUseCase;
import com.example.demo.modules.innovationtype.domain.model.InnovationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InnovationTypeControllerTest {

    @Mock
    private InnovationTypeUseCase innovationTypeUseCase;

    @InjectMocks
    private InnovationTypeController controller;

    private InnovationType testInnovationType;

    @BeforeEach
    void setUp() {
        testInnovationType = new InnovationType();
        testInnovationType.setId(1L);
        testInnovationType.setName("Test Type");
        testInnovationType.setDefinition("Test Definition");
        testInnovationType.setIsOldType(false);
    }

    @Test
    void getInnovationTypes_WhenIdProvided_ShouldReturnSpecificType() {
        // Arrange
        Long id = 1L;
        when(innovationTypeUseCase.findInnovationTypeById(id))
            .thenReturn(Optional.of(testInnovationType));

        // Act
        ResponseEntity<?> result = controller.getInnovationTypes(id);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertTrue(result.getBody() instanceof InnovationTypeResponse);
        verify(innovationTypeUseCase).findInnovationTypeById(id);
        verify(innovationTypeUseCase, never()).findAllInnovationTypes();
    }

    @Test
    void getInnovationTypes_WhenIdProvidedButNotFound_ShouldReturnNotFound() {
        // Arrange
        Long id = 999L;
        when(innovationTypeUseCase.findInnovationTypeById(id))
            .thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> result = controller.getInnovationTypes(id);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        verify(innovationTypeUseCase).findInnovationTypeById(id);
    }

    @Test
    void getInnovationTypes_WhenIdNotProvided_ShouldReturnAllTypes() {
        // Arrange
        InnovationType type2 = new InnovationType();
        type2.setId(2L);
        type2.setName("Type 2");
        
        List<InnovationType> types = Arrays.asList(testInnovationType, type2);
        when(innovationTypeUseCase.findAllInnovationTypes()).thenReturn(types);

        // Act
        ResponseEntity<?> result = controller.getInnovationTypes(null);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertTrue(result.getBody() instanceof List);
        @SuppressWarnings("unchecked")
        List<InnovationTypeResponse> responseList = (List<InnovationTypeResponse>) result.getBody();
        assertEquals(2, responseList.size());
        verify(innovationTypeUseCase).findAllInnovationTypes();
        verify(innovationTypeUseCase, never()).findInnovationTypeById(any());
    }

    @Test
    void getInnovationTypes_WhenNoTypesExist_ShouldReturnEmptyList() {
        // Arrange
        when(innovationTypeUseCase.findAllInnovationTypes()).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<?> result = controller.getInnovationTypes(null);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertTrue(result.getBody() instanceof List);
        @SuppressWarnings("unchecked")
        List<InnovationTypeResponse> responseList = (List<InnovationTypeResponse>) result.getBody();
        assertTrue(responseList.isEmpty());
    }
}
