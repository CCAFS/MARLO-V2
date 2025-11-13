package com.example.demo.modules.innovationreports.application.service;

import com.example.demo.modules.innovationreports.domain.model.InnovationCatalogReport;
import com.example.demo.modules.innovationreports.domain.port.out.InnovationReportRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InnovationReportServiceTest {

    @Mock
    private InnovationReportRepository reportRepository;

    @InjectMocks
    private InnovationReportService reportService;

    private InnovationCatalogReport report;

    private static final Long INNOVATION_ID = 123L;
    private static final String USER_NAME = "Alice";
    private static final String USER_LASTNAME = "Doe";
    private static final String USER_EMAIL = "alice.doe@example.org";
    private static final String INTEREST = "Interested in piloting.";

    @BeforeEach
    void setUp() {
        report = new InnovationCatalogReport(INNOVATION_ID, USER_NAME, USER_LASTNAME, USER_EMAIL, INTEREST);
        report.setId(1L);
        report.setActiveSince(LocalDateTime.now());
    }

    @Test
    void getActiveReportsByInnovationId_returnsReports() {
        when(reportRepository.findActiveReportsByInnovationId(INNOVATION_ID)).thenReturn(List.of(report));

        List<InnovationCatalogReport> result = reportService.getActiveReportsByInnovationId(INNOVATION_ID);

        assertEquals(1, result.size());
        assertEquals(report.getId(), result.get(0).getId());
        verify(reportRepository).findActiveReportsByInnovationId(INNOVATION_ID);
    }

    @Test
    void getActiveReportsByInnovationId_nullInnovationIdThrows() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                () -> reportService.getActiveReportsByInnovationId(null));
        assertEquals("Innovation ID cannot be null", ex.getMessage());
        verify(reportRepository, never()).findActiveReportsByInnovationId(any());
    }

    @Test
    void getActiveReportsByUserEmail_returnsReports() {
        when(reportRepository.findActiveReportsByUserEmail(USER_EMAIL)).thenReturn(List.of(report));

        List<InnovationCatalogReport> result = reportService.getActiveReportsByUserEmail(USER_EMAIL);

        assertEquals(1, result.size());
        verify(reportRepository).findActiveReportsByUserEmail(USER_EMAIL);
    }

    @Test
    void getActiveReportsByUserEmail_invalidEmailThrows() {
        assertThrows(IllegalArgumentException.class,
                () -> reportService.getActiveReportsByUserEmail("invalid"));
        verify(reportRepository, never()).findActiveReportsByUserEmail(any());
    }

    @Test
    void getActiveReportsCount_returnsCount() {
        when(reportRepository.countActiveReportsByInnovationId(INNOVATION_ID)).thenReturn(5L);

        Long count = reportService.getActiveReportsCount(INNOVATION_ID);

        assertEquals(5L, count);
        verify(reportRepository).countActiveReportsByInnovationId(INNOVATION_ID);
    }

    @Test
    void createReport_succeeds() {
        when(reportRepository.save(any(InnovationCatalogReport.class))).thenReturn(report);

        InnovationCatalogReport created = reportService.createReport(INNOVATION_ID, USER_NAME, USER_LASTNAME, USER_EMAIL, INTEREST);

        assertNotNull(created);
        verify(reportRepository).save(any(InnovationCatalogReport.class));
    }

    @Test
    void createReport_invalidParametersThrow() {
        assertThrows(IllegalArgumentException.class,
                () -> reportService.createReport(null, USER_NAME, USER_LASTNAME, USER_EMAIL, INTEREST));
        assertThrows(IllegalArgumentException.class,
                () -> reportService.createReport(INNOVATION_ID, "", USER_LASTNAME, USER_EMAIL, INTEREST));
        assertThrows(IllegalArgumentException.class,
                () -> reportService.createReport(INNOVATION_ID, USER_NAME, USER_LASTNAME, "bad-email", INTEREST));
        verify(reportRepository, never()).save(any());
    }

    @Test
    void createReport_allowsOptionalLastnameAndEmail() {
        when(reportRepository.save(any(InnovationCatalogReport.class))).thenReturn(report);

        assertDoesNotThrow(() -> reportService.createReport(INNOVATION_ID, USER_NAME, null, null, INTEREST));
        verify(reportRepository, atLeastOnce()).save(any(InnovationCatalogReport.class));
    }

    @Test
    void createReportWithAudit_setsJustification() {
        when(reportRepository.save(any(InnovationCatalogReport.class))).thenAnswer(invocation -> {
            InnovationCatalogReport saved = invocation.getArgument(0);
            saved.setId(10L);
            return saved;
        });

        InnovationCatalogReport created = reportService.createReportWithAudit(INNOVATION_ID, USER_NAME, USER_LASTNAME, USER_EMAIL, INTEREST, "test");

        assertEquals("test", created.getModificationJustification());
        verify(reportRepository).save(any(InnovationCatalogReport.class));
    }

    @Test
    void deactivateReport_success() {
        when(reportRepository.softDelete(1L)).thenReturn(true);

        assertTrue(reportService.deactivateReport(1L));
        verify(reportRepository).softDelete(1L);
    }

    @Test
    void deactivateReport_notFoundReturnsFalse() {
        when(reportRepository.softDelete(1L)).thenReturn(false);

        assertFalse(reportService.deactivateReport(1L));
    }

    @Test
    void deactivateReport_nullIdThrows() {
        assertThrows(IllegalArgumentException.class, () -> reportService.deactivateReport(null));
        verify(reportRepository, never()).softDelete(anyLong());
    }
}
