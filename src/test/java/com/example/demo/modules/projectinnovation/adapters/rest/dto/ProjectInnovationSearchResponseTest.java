package com.example.demo.modules.projectinnovation.adapters.rest.dto;

import com.example.demo.modules.projectinnovation.adapters.rest.ProjectInnovationInfoResponse;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.lang.reflect.Constructor;

import static org.junit.jupiter.api.Assertions.*;

class ProjectInnovationSearchResponseTest {

    @Test
    void paginationInfo_Of_ShouldCalculateCorrectly() {
        // Act
        ProjectInnovationSearchResponse.PaginationInfo pagination = 
            ProjectInnovationSearchResponse.PaginationInfo.of(0, 20, 100);

        // Assert
        assertEquals(0, pagination.offset());
        assertEquals(20, pagination.limit());
        assertEquals(1, pagination.currentPage());
        assertEquals(5, pagination.totalPages());
        assertTrue(pagination.hasNext());
        assertFalse(pagination.hasPrevious());
    }

    @Test
    void paginationInfo_WithOffset_ShouldCalculatePage() {
        // Act
        ProjectInnovationSearchResponse.PaginationInfo pagination = 
            ProjectInnovationSearchResponse.PaginationInfo.of(40, 20, 100);

        // Assert
        assertEquals(3, pagination.currentPage());
        assertTrue(pagination.hasPrevious());
    }

    @Test
    void paginationInfo_LastPage_ShouldHaveNoNext() {
        // Act
        ProjectInnovationSearchResponse.PaginationInfo pagination = 
            ProjectInnovationSearchResponse.PaginationInfo.of(80, 20, 100);

        // Assert
        assertFalse(pagination.hasNext());
    }

    @Test
    void of_ShouldCreateResponse() {
        // Arrange
        List<ProjectInnovationInfoResponse> innovations = Collections.emptyList();
        ProjectInnovationSearchResponse.SearchFilters filters = createSearchFilters("ALL_ACTIVE");
        ProjectInnovationSearchResponse.PaginationInfo pagination = 
            ProjectInnovationSearchResponse.PaginationInfo.of(0, 20, 0);

        // Act
        ProjectInnovationSearchResponse response = ProjectInnovationSearchResponse.of(
            innovations, 0, filters, pagination
        );

        // Assert
        assertNotNull(response);
        assertEquals(0, response.totalCount());
        assertEquals("ALL_ACTIVE", response.appliedFilters().searchType());
    }

    private ProjectInnovationSearchResponse.SearchFilters createSearchFilters(String searchType) {
        try {
            Constructor<?> constructor = ProjectInnovationSearchResponse.SearchFilters.class
                .getDeclaredConstructors()[0];
            int paramCount = constructor.getParameterCount();
            Object[] args = paramCount == 9
                ? new Object[] {null, null, null, null, null, null, null, null, searchType}
                : new Object[] {null, null, null, null, null, null, null, searchType};
            return (ProjectInnovationSearchResponse.SearchFilters) constructor.newInstance(args);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException(e);
        }
    }
}
