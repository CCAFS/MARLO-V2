package com.example.demo.modules.actors.adapters.rest;

import com.example.demo.modules.actors.adapters.rest.dto.ActorResponse;
import com.example.demo.modules.actors.adapters.rest.mapper.ActorMapper;
import com.example.demo.modules.actors.application.service.ActorService;
import com.example.demo.modules.projectinnovation.domain.model.Actor;
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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActorControllerTest {

    @Mock
    private ActorService actorService;

    @Mock
    private ActorMapper actorMapper;

    @InjectMocks
    private ActorController actorController;

    private Actor testActor;
    private ActorResponse testActorResponse;

    @BeforeEach
    void setUp() {
        testActor = new Actor();
        testActor.setId(1L);
        testActor.setName("Test Actor");
        testActor.setIsActive(true);

        testActorResponse = new ActorResponse();
        testActorResponse.setId(1L);
        testActorResponse.setName("Test Actor");
    }

    @Test
    void getAllActiveActors_ShouldReturnOkWithList() {
        // Arrange
        List<Actor> actors = Arrays.asList(testActor);
        List<ActorResponse> responses = Arrays.asList(testActorResponse);
        
        when(actorService.getAllActiveActors()).thenReturn(actors);
        when(actorMapper.toResponseList(actors)).thenReturn(responses);

        // Act
        ResponseEntity<List<ActorResponse>> result = actorController.getAllActiveActors();

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().size());
        assertEquals(testActorResponse.getId(), result.getBody().get(0).getId());
        verify(actorService).getAllActiveActors();
        verify(actorMapper).toResponseList(actors);
    }

    @Test
    void getAllActiveActors_WhenEmpty_ShouldReturnEmptyList() {
        // Arrange
        when(actorService.getAllActiveActors()).thenReturn(Collections.emptyList());
        when(actorMapper.toResponseList(anyList())).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<ActorResponse>> result = actorController.getAllActiveActors();

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertTrue(result.getBody().isEmpty());
        verify(actorService).getAllActiveActors();
    }
}
