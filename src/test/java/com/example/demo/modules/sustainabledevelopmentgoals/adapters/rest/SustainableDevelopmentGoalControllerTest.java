package com.example.demo.modules.sustainabledevelopmentgoals.adapters.rest;

import com.example.demo.modules.sustainabledevelopmentgoals.application.port.inbound.SustainableDevelopmentGoalUseCase;
import com.example.demo.modules.sustainabledevelopmentgoals.domain.model.SustainableDevelopmentGoal;
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
class SustainableDevelopmentGoalControllerTest {

    @Mock
    private SustainableDevelopmentGoalUseCase sdgUseCase;

    @InjectMocks
    private SustainableDevelopmentGoalController controller;

    private SustainableDevelopmentGoal testSdg;

    @BeforeEach
    void setUp() {
        testSdg = new SustainableDevelopmentGoal();
        testSdg.setId(1L);
        testSdg.setSmoCode(1L);
        testSdg.setShortName("No Poverty");
        testSdg.setFullName("End poverty in all its forms everywhere");
    }

    @Test
    void getSustainableDevelopmentGoals_WithoutId_ShouldReturnList() {
        // Arrange
        List<SustainableDevelopmentGoal> sdgs = Arrays.asList(testSdg);
        when(sdgUseCase.findAllSustainableDevelopmentGoals()).thenReturn(sdgs);

        // Act
        ResponseEntity<?> result = controller.getSustainableDevelopmentGoals(null);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(sdgUseCase).findAllSustainableDevelopmentGoals();
    }

    @Test
    void getSustainableDevelopmentGoals_WithId_WhenExists_ShouldReturnOk() {
        // Arrange
        when(sdgUseCase.findSustainableDevelopmentGoalById(1L)).thenReturn(Optional.of(testSdg));

        // Act
        ResponseEntity<?> result = controller.getSustainableDevelopmentGoals(1L);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        verify(sdgUseCase).findSustainableDevelopmentGoalById(1L);
    }

    @Test
    void getSustainableDevelopmentGoals_WithId_WhenNotExists_ShouldReturnNotFound() {
        // Arrange
        when(sdgUseCase.findSustainableDevelopmentGoalById(999L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<?> result = controller.getSustainableDevelopmentGoals(999L);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
        verify(sdgUseCase).findSustainableDevelopmentGoalById(999L);
    }
}
