package com.example.demo.modules.innovationreports.adapters.web.controller;

import com.example.demo.modules.innovationreports.adapters.web.dto.CreateInnovationReportRequestDto;
import com.example.demo.modules.innovationreports.adapters.web.dto.InnovationReportResponseDto;
import com.example.demo.modules.innovationreports.adapters.web.mapper.InnovationReportMapper;
import com.example.demo.modules.innovationreports.domain.model.InnovationCatalogReport;
import com.example.demo.modules.innovationreports.domain.port.in.InnovationReportUseCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InnovationReportControllerTest {

    @Mock
    private InnovationReportUseCase reportUseCase;

    @Mock
    private InnovationReportMapper mapper;

    @InjectMocks
    private InnovationReportController controller;

    private InnovationCatalogReport testReport;
    private InnovationReportResponseDto testResponseDto;

    @BeforeEach
    void setUp() {
        testReport = new InnovationCatalogReport(100L, "John", "Doe", "test@example.com", "Test narrative");
        testReport.setId(1L);
        testReport.setIsActive(true);

        testResponseDto = new InnovationReportResponseDto(
            1L, 100L, "John", "Doe", "test@example.com", 
            "Test narrative", true, null, null
        );
    }

    @Test
    void getActiveReportsByInnovationId_ShouldReturnList() {
        // Arrange
        List<InnovationCatalogReport> reports = Arrays.asList(testReport);
        List<InnovationReportResponseDto> dtos = Arrays.asList(testResponseDto);
        
        when(reportUseCase.getActiveReportsByInnovationId(100L)).thenReturn(reports);
        when(mapper.toResponseDtoList(reports)).thenReturn(dtos);

        // Act
        ResponseEntity<List<InnovationReportResponseDto>> result = controller.getActiveReportsByInnovationId(100L);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().size());
        verify(reportUseCase).getActiveReportsByInnovationId(100L);
    }

    @Test
    void getActiveReportsByInnovationId_WhenInvalidId_ShouldReturnBadRequest() {
        // Arrange
        when(reportUseCase.getActiveReportsByInnovationId(-1L))
            .thenThrow(new IllegalArgumentException("Invalid innovation ID"));

        // Act
        ResponseEntity<List<InnovationReportResponseDto>> result = controller.getActiveReportsByInnovationId(-1L);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.BAD_REQUEST, result.getStatusCode());
    }

    @Test
    void getActiveReportsCount_ShouldReturnCount() {
        // Arrange
        when(reportUseCase.getActiveReportsCount(100L)).thenReturn(5L);

        // Act
        ResponseEntity<Long> result = controller.getActiveReportsCount(100L);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertEquals(5L, result.getBody());
        verify(reportUseCase).getActiveReportsCount(100L);
    }

    @Test
    void getActiveReportsByUserEmail_ShouldReturnList() {
        // Arrange
        List<InnovationCatalogReport> reports = Arrays.asList(testReport);
        List<InnovationReportResponseDto> dtos = Arrays.asList(testResponseDto);
        
        when(reportUseCase.getActiveReportsByUserEmail("test@example.com")).thenReturn(reports);
        when(mapper.toResponseDtoList(reports)).thenReturn(dtos);

        // Act
        ResponseEntity<List<InnovationReportResponseDto>> result = controller.getActiveReportsByUserEmail("test@example.com");

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.OK, result.getStatusCode());
        assertNotNull(result.getBody());
        assertEquals(1, result.getBody().size());
        verify(reportUseCase).getActiveReportsByUserEmail("test@example.com");
    }

    @Test
    void createReport_WhenValid_ShouldReturnCreated() {
        // Arrange
        CreateInnovationReportRequestDto request = new CreateInnovationReportRequestDto();
        request.setInnovationId(100L);
        request.setUserName("John");
        request.setUserLastname("Doe");
        request.setUserEmail("test@example.com");
        request.setInterestNarrative("Test narrative");
        
        when(reportUseCase.createReport(eq(100L), eq("John"), eq("Doe"), eq("test@example.com"), eq("Test narrative")))
            .thenReturn(testReport);
        when(mapper.toResponseDto(testReport)).thenReturn(testResponseDto);

        // Act
        ResponseEntity<InnovationReportResponseDto> result = controller.createReport(request);

        // Assert
        assertNotNull(result);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertNotNull(result.getBody());
        verify(reportUseCase).createReport(eq(100L), eq("John"), eq("Doe"), eq("test@example.com"), eq("Test narrative"));
    }
}
