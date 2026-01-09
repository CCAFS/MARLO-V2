package com.example.demo.modules.actors.adapters.rest;

import com.example.demo.modules.actors.adapters.rest.dto.ActorResponse;
import com.example.demo.modules.actors.adapters.rest.mapper.ActorMapper;
import com.example.demo.modules.actors.application.service.ActorService;
import com.example.demo.modules.projectinnovation.domain.model.Actor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActorControllerTest {

    @Mock
    private ActorMapper actorMapper;

    private ActorController actorController;
    private ActorService actorService;
    private List<Actor> actorServiceResult;

    private Actor testActor;
    private ActorResponse testActorResponse;

    @BeforeEach
    void setUp() {
        actorServiceResult = Collections.emptyList();
        actorService = mock(ActorService.class, invocation -> {
            if (List.class.isAssignableFrom(invocation.getMethod().getReturnType())) {
                return actorServiceResult;
            }
            return RETURNS_DEFAULTS.answer(invocation);
        });
        actorController = new ActorController(actorService, actorMapper);

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
        
        actorServiceResult = actors;
        when(actorMapper.toResponseList(actors)).thenReturn(responses);

        // Act
        ResponseEntity<List<ActorResponse>> result = invokeGetAllActiveActors(null);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().size());
        assertEquals(testActorResponse.getId(), result.getBody().get(0).getId());
        assertTrue(wasMethodInvoked(actorService, "getActiveActors") || wasMethodInvoked(actorService, "getAllActiveActors"));
        verify(actorMapper).toResponseList(actors);
    }

    @Test
    void getAllActiveActors_WhenEmpty_ShouldReturnEmptyList() {
        // Arrange
        actorServiceResult = Collections.emptyList();
        when(actorMapper.toResponseList(anyList())).thenReturn(Collections.emptyList());

        // Act
        ResponseEntity<List<ActorResponse>> result = invokeGetAllActiveActors(null);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertTrue(result.getBody().isEmpty());
        assertTrue(wasMethodInvoked(actorService, "getActiveActors") || wasMethodInvoked(actorService, "getAllActiveActors"));
    }

    @SuppressWarnings("unchecked")
    private ResponseEntity<List<ActorResponse>> invokeGetAllActiveActors(String nameFilter) {
        try {
            Method method = ActorController.class.getMethod("getAllActiveActors", String.class);
            return (ResponseEntity<List<ActorResponse>>) method.invoke(actorController, nameFilter);
        } catch (NoSuchMethodException e) {
            try {
                Method method = ActorController.class.getMethod("getAllActiveActors");
                return (ResponseEntity<List<ActorResponse>>) method.invoke(actorController);
            } catch (ReflectiveOperationException inner) {
                throw new RuntimeException(inner);
            }
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean wasMethodInvoked(Object mock, String methodName) {
        return mockingDetails(mock).getInvocations().stream()
            .anyMatch(invocation -> invocation.getMethod().getName().equals(methodName));
    }
}
