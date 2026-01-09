package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ProjectInnovationCompleteSearchResponseTest {

    @Test
    void paginationInfo_Of_ShouldCalculateCorrectly() {
        // Act
        ProjectInnovationCompleteSearchResponse.PaginationInfo pagination = 
            ProjectInnovationCompleteSearchResponse.PaginationInfo.of(0, 20, 100);

        // Assert
        assertEquals(0, pagination.offset());
        assertEquals(20, pagination.limit());
        assertEquals(1, pagination.currentPage());
        assertEquals(5, pagination.totalPages());
        assertTrue(pagination.hasNext());
        assertFalse(pagination.hasPrevious());
    }

    @Test
    void of_WithFourParams_ShouldCreateResponse() {
        // Arrange
        List<InnovationInfo> innovations = Collections.emptyList();
        ProjectInnovationCompleteSearchResponse.SearchFilters filters = 
            new ProjectInnovationCompleteSearchResponse.SearchFilters(null, null, null, null, null, null, null, "ALL_ACTIVE");
        ProjectInnovationCompleteSearchResponse.PaginationInfo pagination = 
            ProjectInnovationCompleteSearchResponse.PaginationInfo.of(0, 20, 0);

        // Act
        ProjectInnovationCompleteSearchResponse response = ProjectInnovationCompleteSearchResponse.of(
            innovations, 0, filters, pagination
        );

        // Assert
        assertNotNull(response);
        assertEquals(0, response.totalCount());
        assertEquals("ALL_ACTIVE", response.appliedFilters().searchType());
    }

    @Test
    void of_WithTwoParams_ShouldCreateResponse() {
        // Arrange
        List<InnovationInfo> innovations = Collections.emptyList();
        ProjectInnovationCompleteSearchResponse.SearchFilters filters = 
            new ProjectInnovationCompleteSearchResponse.SearchFilters(null, null, null, null, null, null, null, "ALL_ACTIVE");

        // Act
        ProjectInnovationCompleteSearchResponse response = ProjectInnovationCompleteSearchResponse.of(
            innovations, filters
        );

        // Assert
        assertNotNull(response);
        assertEquals(0, response.totalCount());
        assertNull(response.pagination());
    }

    @Test
    void of_WithOneParam_ShouldCreateResponse() {
        // Arrange
        List<InnovationInfo> innovations = Collections.emptyList();

        // Act
        ProjectInnovationCompleteSearchResponse response = ProjectInnovationCompleteSearchResponse.of(innovations);

        // Assert
        assertNotNull(response);
        assertEquals(0, response.totalCount());
        assertNull(response.appliedFilters());
        assertNull(response.pagination());
    }
}
