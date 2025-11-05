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
        String normalizedEmail = normalizeRequiredEmail(userEmail);

        try {
            return reportRepository.findActiveReportsByUserEmail(normalizedEmail);
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
        InnovationCatalogReport report = buildReport(innovationId, userName, userLastname, userEmail, interestNarrative);

        try {
            return reportRepository.save(report);
        } catch (DataAccessException e) {
            logger.error("Database error while creating report for innovation {}: {}", innovationId, e.getMessage());
            throw new RuntimeException("Database error occurred while creating report", e);
        }
    }

    @Override
    public InnovationCatalogReport createReportWithAudit(Long innovationId, String userName, String userLastname, String userEmail, String interestNarrative, String modificationJustification) {
        InnovationCatalogReport report = buildReport(innovationId, userName, userLastname, userEmail, interestNarrative);

        try {
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

    private InnovationCatalogReport buildReport(Long innovationId,
                                               String userName,
                                               String userLastname,
                                               String userEmail,
                                               String interestNarrative) {
        if (innovationId == null) {
            throw new IllegalArgumentException("Innovation ID cannot be null");
        }

        String normalizedUserName = normalizeRequired(userName, "User name");
        String normalizedLastname = normalizeOptional(userLastname);
        String normalizedEmail = normalizeOptionalEmail(userEmail);

        return new InnovationCatalogReport(
                innovationId,
                normalizedUserName,
                normalizedLastname,
                normalizedEmail,
                interestNarrative
        );
    }

    private String normalizeRequired(String value, String fieldName) {
        String normalized = normalizeOptional(value);
        if (normalized == null) {
            throw new IllegalArgumentException(fieldName + " cannot be null or empty");
        }
        return normalized;
    }

    private String normalizeOptional(String value) {
        if (value == null) {
            return null;
        }
        String trimmed = value.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    private String normalizeRequiredEmail(String email) {
        String normalized = normalizeOptional(email);
        if (normalized == null) {
            throw new IllegalArgumentException("User email cannot be null or empty");
        }
        if (!EMAIL_PATTERN.matcher(normalized).matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }
        return normalized;
    }

    private String normalizeOptionalEmail(String email) {
        String normalized = normalizeOptional(email);
        if (normalized == null) {
            return null;
        }
        if (!EMAIL_PATTERN.matcher(normalized).matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }
        return normalized;
    }
}
