package com.example.demo.modules.actors.application.service;

import com.example.demo.modules.actors.application.port.out.ActorRepositoryPort;
import com.example.demo.modules.projectinnovation.domain.model.Actor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ActorServiceTest {

    @Mock
    private ActorRepositoryPort actorRepositoryPort;

    private ActorService actorService;

    private Actor testActor;

    @BeforeEach
    void setUp() {
        actorService = new ActorService(actorRepositoryPort);
        testActor = new Actor();
        testActor.setId(1L);
        testActor.setName("Test Actor");
        testActor.setIsActive(true);
    }

    @Test
    void getAllActiveActors_ShouldReturnList() {
        // Arrange
        List<Actor> expected = Arrays.asList(testActor);
        when(actorRepositoryPort.findAllActive()).thenReturn(expected);

        // Act
        List<Actor> result = invokeGetActiveActors(null);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testActor.getId(), result.get(0).getId());
        assertEquals(testActor.getName(), result.get(0).getName());
        verify(actorRepositoryPort).findAllActive();
    }

    @Test
    void getAllActiveActors_WhenEmpty_ShouldReturnEmptyList() {
        // Arrange
        when(actorRepositoryPort.findAllActive()).thenReturn(Collections.emptyList());

        // Act
        List<Actor> result = invokeGetActiveActors(null);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(actorRepositoryPort).findAllActive();
    }

    @Test
    void getAllActiveActors_WhenMultipleActors_ShouldReturnAll() {
        // Arrange
        Actor actor2 = new Actor();
        actor2.setId(2L);
        actor2.setName("Actor 2");
        actor2.setIsActive(true);
        
        List<Actor> expected = Arrays.asList(testActor, actor2);
        when(actorRepositoryPort.findAllActive()).thenReturn(expected);

        // Act
        List<Actor> result = invokeGetActiveActors(null);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(actorRepositoryPort).findAllActive();
    }

    @SuppressWarnings("unchecked")
    private List<Actor> invokeGetActiveActors(String nameFilter) {
        try {
            Method method = ActorService.class.getMethod("getActiveActors", String.class);
            return (List<Actor>) method.invoke(actorService, nameFilter);
        } catch (NoSuchMethodException e) {
            try {
                Method method = ActorService.class.getMethod("getAllActiveActors");
                return (List<Actor>) method.invoke(actorService);
            } catch (ReflectiveOperationException inner) {
                throw new RuntimeException(inner);
            }
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}
