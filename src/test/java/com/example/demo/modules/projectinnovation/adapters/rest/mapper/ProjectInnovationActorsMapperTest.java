package com.example.demo.modules.projectinnovation.adapters.rest.mapper;

import com.example.demo.modules.projectinnovation.adapters.rest.dto.ProjectInnovationActorsResponse;
import com.example.demo.modules.projectinnovation.domain.model.Actor;
import com.example.demo.modules.projectinnovation.domain.model.ProjectInnovationActors;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class ProjectInnovationActorsMapperTest {

    @InjectMocks
    private ProjectInnovationActorsMapper mapper;

    private ProjectInnovationActors testActors;
    private Actor testActor;

    @BeforeEach
    void setUp() {
        testActor = new Actor();
        testActor.setId(1L);
        testActor.setName("Test Actor");
        testActor.setDescription("Test Description");
        testActor.setIsActive(true);
        testActor.setPrmsNameEquivalent("PRMS Name");

        testActors = new ProjectInnovationActors();
        testActors.setId(1L);
        testActors.setInnovationId(100L);
        testActors.setActorId(1L);
        testActors.setActor(testActor);
        testActors.setIsActive(true);
        testActors.setActiveSince(LocalDateTime.now());
        testActors.setCreatedBy(1L);
        testActors.setIdPhase(1L);
    }

    @Test
    void toResponse_WhenActorsNotNull_ShouldReturnResponse() {
        // Act
        ProjectInnovationActorsResponse result = mapper.toResponse(testActors);

        // Assert
        assertNotNull(result);
        assertEquals(testActors.getId(), result.id());
        assertEquals(testActors.getInnovationId(), result.innovationId());
        assertEquals(testActors.getActorId(), result.actorId());
        assertNotNull(result.actorInfo());
        assertEquals(testActor.getId(), result.actorInfo().id());
        assertEquals(testActor.getName(), result.actorInfo().name());
    }

    @Test
    void toResponse_WhenActorsNull_ShouldReturnNull() {
        // Act
        ProjectInnovationActorsResponse result = mapper.toResponse(null);

        // Assert
        assertNull(result);
    }

    @Test
    void toResponse_WhenActorIsNull_ShouldReturnResponseWithoutActorInfo() {
        // Arrange
        testActors.setActor(null);

        // Act
        ProjectInnovationActorsResponse result = mapper.toResponse(testActors);

        // Assert
        assertNotNull(result);
        assertEquals(testActors.getId(), result.id());
        assertNull(result.actorInfo());
    }

    @Test
    void toResponseList_WhenListNotNull_ShouldReturnList() {
        // Arrange
        List<ProjectInnovationActors> actorsList = Arrays.asList(testActors);

        // Act
        List<ProjectInnovationActorsResponse> result = mapper.toResponseList(actorsList);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testActors.getId(), result.get(0).id());
    }

    @Test
    void toResponseList_WhenListNull_ShouldReturnNull() {
        // Act
        List<ProjectInnovationActorsResponse> result = mapper.toResponseList(null);

        // Assert
        assertNull(result);
    }

    @Test
    void toResponseList_WhenListEmpty_ShouldReturnEmptyList() {
        // Arrange
        List<ProjectInnovationActors> actorsList = Collections.emptyList();

        // Act
        List<ProjectInnovationActorsResponse> result = mapper.toResponseList(actorsList);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void toResponseList_WhenMultipleActors_ShouldReturnAll() {
        // Arrange
        ProjectInnovationActors actors2 = new ProjectInnovationActors();
        actors2.setId(2L);
        actors2.setInnovationId(100L);
        actors2.setActorId(2L);
        
        List<ProjectInnovationActors> actorsList = Arrays.asList(testActors, actors2);

        // Act
        List<ProjectInnovationActorsResponse> result = mapper.toResponseList(actorsList);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
    }
}
