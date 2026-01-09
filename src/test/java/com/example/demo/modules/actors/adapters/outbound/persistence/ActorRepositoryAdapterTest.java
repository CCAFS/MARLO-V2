package com.example.demo.modules.actors.adapters.outbound.persistence;

import com.example.demo.modules.projectinnovation.adapters.outbound.persistence.ActorJpaRepository;
import com.example.demo.modules.projectinnovation.domain.model.Actor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ActorRepositoryAdapterTest {

    private ActorJpaRepository actorJpaRepository;
    private ActorRepositoryAdapter adapter;

    @BeforeEach
    void setUp() {
        actorJpaRepository = mock(ActorJpaRepository.class);
        adapter = new ActorRepositoryAdapter(actorJpaRepository);
    }

    @Test
    void findAllActive_ShouldReturnList() {
        // Arrange
        Actor actor1 = new Actor();
        actor1.setId(1L);
        Actor actor2 = new Actor();
        actor2.setId(2L);
        when(actorJpaRepository.findAllActive())
            .thenReturn(Arrays.asList(actor1, actor2));

        // Act
        List<Actor> result = adapter.findAllActive();

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        verify(actorJpaRepository).findAllActive();
    }

    @Test
    void findAllActive_WhenEmpty_ShouldReturnEmptyList() {
        // Arrange
        when(actorJpaRepository.findAllActive()).thenReturn(Arrays.asList());

        // Act
        List<Actor> result = adapter.findAllActive();

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(actorJpaRepository).findAllActive();
    }
}
