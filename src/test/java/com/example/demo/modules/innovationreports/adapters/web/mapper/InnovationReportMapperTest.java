package com.example.demo.modules.innovationreports.adapters.web.mapper;

import com.example.demo.modules.innovationreports.adapters.web.dto.CreateInnovationReportRequestDto;
import com.example.demo.modules.innovationreports.adapters.web.dto.InnovationReportResponseDto;
import com.example.demo.modules.innovationreports.domain.model.InnovationCatalogReport;
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
class InnovationReportMapperTest {

    @InjectMocks
    private InnovationReportMapper mapper;

    private InnovationCatalogReport testReport;
    private CreateInnovationReportRequestDto testRequestDto;

    @BeforeEach
    void setUp() {
        testReport = new InnovationCatalogReport(100L, "John", "Doe", "john@example.com", "Test narrative");
        testReport.setId(1L);
        testReport.setIsActive(true);
        testReport.setActiveSince(LocalDateTime.now());

        testRequestDto = new CreateInnovationReportRequestDto();
        testRequestDto.setInnovationId(100L);
        testRequestDto.setUserName("John");
        testRequestDto.setUserLastname("Doe");
        testRequestDto.setUserEmail("john@example.com");
        testRequestDto.setInterestNarrative("Test narrative");
    }

    @Test
    void toResponseDto_WhenReportNotNull_ShouldReturnDto() {
        // Act
        InnovationReportResponseDto result = mapper.toResponseDto(testReport);

        // Assert
        assertNotNull(result);
        assertEquals(testReport.getId(), result.getId());
        assertEquals(testReport.getInnovationId(), result.getInnovationId());
        assertEquals(testReport.getUserName(), result.getUserName());
        assertEquals(testReport.getInterestNarrative(), result.getInterestNarrative());
    }

    @Test
    void toResponseDto_WhenReportNull_ShouldReturnNull() {
        // Act
        InnovationReportResponseDto result = mapper.toResponseDto(null);

        // Assert
        assertNull(result);
    }

    @Test
    void toResponseDtoList_WhenListNotNull_ShouldReturnList() {
        // Arrange
        List<InnovationCatalogReport> reports = Arrays.asList(testReport);

        // Act
        List<InnovationReportResponseDto> result = mapper.toResponseDtoList(reports);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(testReport.getId(), result.get(0).getId());
    }

    @Test
    void toResponseDtoList_WhenListNull_ShouldReturnEmptyList() {
        // Act
        List<InnovationReportResponseDto> result = mapper.toResponseDtoList(null);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void toResponseDtoList_WhenListEmpty_ShouldReturnEmptyList() {
        // Arrange
        List<InnovationCatalogReport> reports = Collections.emptyList();

        // Act
        List<InnovationReportResponseDto> result = mapper.toResponseDtoList(reports);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void toEntity_WhenRequestNotNull_ShouldReturnEntity() {
        // Act
        InnovationCatalogReport result = mapper.toEntity(testRequestDto);

        // Assert
        assertNotNull(result);
        assertEquals(testRequestDto.getInnovationId(), result.getInnovationId());
        assertEquals(testRequestDto.getUserName(), result.getUserName());
        assertEquals(testRequestDto.getInterestNarrative(), result.getInterestNarrative());
    }

    @Test
    void toEntity_WhenRequestNull_ShouldReturnNull() {
        // Act
        InnovationCatalogReport result = mapper.toEntity(null);

        // Assert
        assertNull(result);
    }

    @Test
    void toEntity_WithModificationJustification_ShouldSetJustification() {
        // Arrange
        testRequestDto.setModificationJustification("Test justification");

        // Act
        InnovationCatalogReport result = mapper.toEntity(testRequestDto);

        // Assert
        assertNotNull(result);
        assertEquals("Test justification", result.getModificationJustification());
    }
}
