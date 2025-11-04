package com.example.demo.modules.innovationreports.application.service;

import com.example.demo.modules.innovationreports.domain.model.InnovationCatalogReport;
import com.example.demo.modules.innovationreports.domain.port.in.InnovationReportUseCase;
import com.example.demo.modules.innovationreports.domain.port.out.InnovationReportRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.regex.Pattern;

@Service
@Transactional
public class InnovationReportService implements InnovationReportUseCase {

    private static final Logger logger = LoggerFactory.getLogger(InnovationReportService.class);
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$");

    private final InnovationReportRepository reportRepository;

    public InnovationReportService(InnovationReportRepository reportRepository) {
        this.reportRepository = reportRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public List<InnovationCatalogReport> getActiveReportsByInnovationId(Long innovationId) {
        if (innovationId == null) {
            throw new IllegalArgumentException("Innovation ID cannot be null");
        }

        try {
            return reportRepository.findActiveReportsByInnovationId(innovationId);
        } catch (DataAccessException e) {
            logger.error("Database error while fetching reports for innovation {}: {}", innovationId, e.getMessage());
            throw new RuntimeException("Database error occurred while fetching reports", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<InnovationCatalogReport> getActiveReportsByUserEmail(String userEmail) {
        validateEmail(userEmail);

        try {
            return reportRepository.findActiveReportsByUserEmail(userEmail);
        } catch (DataAccessException e) {
            logger.error("Database error while fetching reports for user {}: {}", userEmail, e.getMessage());
            throw new RuntimeException("Database error occurred while fetching user reports", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Long getActiveReportsCount(Long innovationId) {
        if (innovationId == null) {
            throw new IllegalArgumentException("Innovation ID cannot be null");
        }

        try {
            return reportRepository.countActiveReportsByInnovationId(innovationId);
        } catch (DataAccessException e) {
            logger.error("Database error while counting reports for innovation {}: {}", innovationId, e.getMessage());
            throw new RuntimeException("Database error occurred while counting reports", e);
        }
    }

    @Override
    public InnovationCatalogReport createReport(Long innovationId, String userName, String userLastname, String userEmail, String interestNarrative) {
        validateReportParameters(innovationId, userName, userLastname, userEmail);

        try {
            InnovationCatalogReport report = new InnovationCatalogReport(innovationId, userName, userLastname, userEmail, interestNarrative);
            return reportRepository.save(report);
        } catch (DataAccessException e) {
            logger.error("Database error while creating report for innovation {}: {}", innovationId, e.getMessage());
            throw new RuntimeException("Database error occurred while creating report", e);
        }
    }

    @Override
    public InnovationCatalogReport createReportWithAudit(Long innovationId, String userName, String userLastname, String userEmail, String interestNarrative, String modificationJustification) {
        validateReportParameters(innovationId, userName, userLastname, userEmail);

        try {
            InnovationCatalogReport report = new InnovationCatalogReport(innovationId, userName, userLastname, userEmail, interestNarrative);
            report.setModificationJustification(modificationJustification);
            return reportRepository.save(report);
        } catch (DataAccessException e) {
            logger.error("Database error while creating report with audit for innovation {}: {}", innovationId, e.getMessage());
            throw new RuntimeException("Database error occurred while creating report", e);
        }
    }

    @Override
    public boolean deactivateReport(Long reportId) {
        if (reportId == null) {
            throw new IllegalArgumentException("Report ID cannot be null");
        }

        try {
            return reportRepository.softDelete(reportId);
        } catch (DataAccessException e) {
            logger.error("Database error while deactivating report {}: {}", reportId, e.getMessage());
            throw new RuntimeException("Database error occurred while deactivating report", e);
        }
    }

    private void validateReportParameters(Long innovationId, String userName, String userLastname, String userEmail) {
        if (innovationId == null) {
            throw new IllegalArgumentException("Innovation ID cannot be null");
        }
        if (userName == null || userName.trim().isEmpty()) {
            throw new IllegalArgumentException("User name cannot be null or empty");
        }
        if (userLastname == null || userLastname.trim().isEmpty()) {
            throw new IllegalArgumentException("User lastname cannot be null or empty");
        }
        validateEmail(userEmail);
    }

    private void validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("User email cannot be null or empty");
        }
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }
    }
}
