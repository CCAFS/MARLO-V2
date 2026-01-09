package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.*;

class ProjectInnovationSimpleSearchResponseTest {

    @Test
    void paginationInfo_Of_ShouldCalculateCorrectly() {
        // Act
        ProjectInnovationSimpleSearchResponse.PaginationInfo pagination = 
            ProjectInnovationSimpleSearchResponse.PaginationInfo.of(0, 20, 100);

        // Assert
        assertEquals(0, pagination.offset());
        assertEquals(20, pagination.limit());
        assertEquals(100, pagination.totalCount());
        assertEquals(1, pagination.currentPage());
        assertEquals(5, pagination.totalPages());
        assertTrue(pagination.hasNext());
        assertFalse(pagination.hasPrevious());
    }

    @Test
    void paginationInfo_WithOffset_ShouldCalculatePage() {
        // Act
        ProjectInnovationSimpleSearchResponse.PaginationInfo pagination = 
            ProjectInnovationSimpleSearchResponse.PaginationInfo.of(40, 20, 100);

        // Assert
        assertEquals(3, pagination.currentPage());
        assertTrue(pagination.hasPrevious());
    }

    @Test
    void paginationInfo_LastPage_ShouldHaveNoNext() {
        // Act
        ProjectInnovationSimpleSearchResponse.PaginationInfo pagination = 
            ProjectInnovationSimpleSearchResponse.PaginationInfo.of(80, 20, 100);

        // Assert
        assertFalse(pagination.hasNext());
    }

    @Test
    void of_WithTwoParams_ShouldCreateResponse() {
        // Arrange
        List<ProjectInnovationSimpleResponse> innovations = Collections.emptyList();
        ProjectInnovationSimpleSearchResponse.SearchFilters filters = createSearchFilters("ALL_ACTIVE");

        // Act
        ProjectInnovationSimpleSearchResponse response = ProjectInnovationSimpleSearchResponse.of(
            innovations, filters
        );

        // Assert
        assertNotNull(response);
        assertEquals(0, response.totalCount());
        assertEquals("ALL_ACTIVE", response.appliedFilters().searchType());
        assertNull(response.pagination());
    }

    @Test
    void of_WithFourParams_ShouldCreateResponseWithPagination() {
        // Arrange
        List<ProjectInnovationSimpleResponse> innovations = Collections.emptyList();
        ProjectInnovationSimpleSearchResponse.SearchFilters filters = createSearchFilters("ALL_ACTIVE");
        ProjectInnovationSimpleSearchResponse.PaginationInfo pagination = 
            ProjectInnovationSimpleSearchResponse.PaginationInfo.of(0, 20, 0);

        // Act
        ProjectInnovationSimpleSearchResponse response = ProjectInnovationSimpleSearchResponse.of(
            innovations, 0, filters, pagination
        );

        // Assert
        assertNotNull(response);
        assertEquals(0, response.totalCount());
        assertNotNull(response.pagination());
    }

    private ProjectInnovationSimpleSearchResponse.SearchFilters createSearchFilters(String searchType) {
        try {
            Constructor<?> constructor = ProjectInnovationSimpleSearchResponse.SearchFilters.class
                .getDeclaredConstructors()[0];
            int paramCount = constructor.getParameterCount();
            Object[] args = paramCount == 9
                ? new Object[] {null, null, null, null, null, null, null, null, searchType}
                : new Object[] {null, null, null, null, null, null, null, searchType};
            return (ProjectInnovationSimpleSearchResponse.SearchFilters) constructor.newInstance(args);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}
