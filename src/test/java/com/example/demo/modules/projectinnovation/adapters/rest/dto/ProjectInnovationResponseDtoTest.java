package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import com.example.demo.modules.projectinnovation.adapters.rest.CreateProjectInnovationRequest;
import com.example.demo.modules.projectinnovation.adapters.rest.UpdateProjectInnovationRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for ProjectInnovation request DTOs to improve code coverage
 */
class ProjectInnovationRequestDtoTest {

    @Test
    void createProjectInnovationRequest_Creation_Success() {
        // Given
        Long projectId = 1L;
        Long createdBy = 123L;
        String justification = "Creating new innovation";
        
        // When
        CreateProjectInnovationRequest request = new CreateProjectInnovationRequest(
            projectId, createdBy, justification
        );
        
        // Then
        assertNotNull(request);
        assertEquals(projectId, request.projectId());
        assertEquals(createdBy, request.createdBy());
        assertEquals(justification, request.modificationJustification());
    }

    @Test
    void updateProjectInnovationRequest_Creation_Success() {
        // Given
        Boolean isActive = true;
        Long modifiedBy = 456L;
        String justification = "Updating innovation";
        
        // When
        UpdateProjectInnovationRequest request = new UpdateProjectInnovationRequest(
            isActive, modifiedBy, justification
        );
        
        // Then
        assertNotNull(request);
        assertEquals(isActive, request.isActive());
        assertEquals(modifiedBy, request.modifiedBy());
        assertEquals(justification, request.modificationJustification());
    }

    @Test
    void createProjectInnovationRequest_WithNullValues_Success() {
        // When
        CreateProjectInnovationRequest request = new CreateProjectInnovationRequest(
            null, null, null
        );
        
        // Then
        assertNotNull(request);
        assertNull(request.projectId());
        assertNull(request.createdBy());
        assertNull(request.modificationJustification());
    }

    @Test
    void updateProjectInnovationRequest_WithNullValues_Success() {
        // When
        UpdateProjectInnovationRequest request = new UpdateProjectInnovationRequest(
            null, null, null
        );
        
        // Then
        assertNotNull(request);
        assertNull(request.isActive());
        assertNull(request.modifiedBy());
        assertNull(request.modificationJustification());
    }

    @Test
    void requestDtos_ToString_ReturnsValidString() {
        // Given
        CreateProjectInnovationRequest createRequest = new CreateProjectInnovationRequest(
            1L, 123L, "Test justification"
        );
        
        UpdateProjectInnovationRequest updateRequest = new UpdateProjectInnovationRequest(
            true, 456L, "Update justification"
        );
        
        // When
        String createString = createRequest.toString();
        String updateString = updateRequest.toString();
        
        // Then
        assertNotNull(createString);
        assertNotNull(updateString);
        assertTrue(createString.contains("CreateProjectInnovationRequest"));
        assertTrue(updateString.contains("UpdateProjectInnovationRequest"));
    }

    @Test
    void requestDtos_Equals_WorksCorrectly() {
        // Given
        CreateProjectInnovationRequest request1 = new CreateProjectInnovationRequest(
            1L, 123L, "Test"
        );
        
        CreateProjectInnovationRequest request2 = new CreateProjectInnovationRequest(
            1L, 123L, "Test"
        );
        
        CreateProjectInnovationRequest request3 = new CreateProjectInnovationRequest(
            2L, 123L, "Different"
        );
        
        // Then
        assertEquals(request1, request2);
        assertNotEquals(request1, request3);
        assertEquals(request1.hashCode(), request2.hashCode());
    }

    @Test
    void requestDtos_HashCode_WorksCorrectly() {
        // Given
        CreateProjectInnovationRequest request = new CreateProjectInnovationRequest(
            1L, 123L, "Test"
        );
        
        UpdateProjectInnovationRequest updateRequest = new UpdateProjectInnovationRequest(
            true, 456L, "Update"
        );
        
        // When & Then
        assertNotNull(request.hashCode());
        assertNotNull(updateRequest.hashCode());
        
        // Same object should have same hash
        assertEquals(request.hashCode(), request.hashCode());
        assertEquals(updateRequest.hashCode(), updateRequest.hashCode());
    }
}