package com.example.demo.modules.actors.adapters.rest.mapper;

import com.example.demo.modules.actors.adapters.rest.dto.ActorResponse;
import com.example.demo.modules.projectinnovation.domain.model.Actor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ActorMapperTest {

    @InjectMocks
    private ActorMapper mapper;

    private Actor testActor;

    @BeforeEach
    void setUp() {
        testActor = new Actor();
        testActor.setId(1L);
        testActor.setName("Test Actor");
        testActor.setPrmsNameEquivalent("PRMS Name");
    }

    @Test
    void toResponse_WhenActorNotNull_ShouldReturnResponse() {
        // Act
        ActorResponse result = mapper.toResponse(testActor);

        // Assert
        assertNotNull(result);
        assertEquals(testActor.getId(), result.getId());
        assertEquals(testActor.getName(), result.getName());
        assertEquals(testActor.getPrmsNameEquivalent(), result.getPrmsNameEquivalent());
    }

    @Test
    void toResponse_WhenActorNull_ShouldReturnNull() {
        // Act
        ActorResponse result = mapper.toResponse(null);

        // Assert
        assertNull(result);
    }

    @Test
    void toResponseList_WhenListNotNull_ShouldReturnList() {
        // Arrange
        List<Actor> actors = Arrays.asList(testActor);

        // Act
        List<ActorResponse> result = mapper.toResponseList(actors);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testActor.getId(), result.get(0).getId());
    }

    @Test
    void toResponseList_WhenListNull_ShouldReturnEmptyList() {
        // Act
        List<ActorResponse> result = mapper.toResponseList(null);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void toResponseList_WhenListEmpty_ShouldReturnEmptyList() {
        // Arrange
        List<Actor> actors = Collections.emptyList();

        // Act
        List<ActorResponse> result = mapper.toResponseList(actors);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }
}
