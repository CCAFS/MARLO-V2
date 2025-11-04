package com.example.demo.modules.innovationreports.domain.port.in;

import com.example.demo.modules.innovationreports.domain.model.InnovationCatalogReport;

import java.util.List;

public interface InnovationReportUseCase {

    List<InnovationCatalogReport> getActiveReportsByInnovationId(Long innovationId);

    List<InnovationCatalogReport> getActiveReportsByUserEmail(String userEmail);

    Long getActiveReportsCount(Long innovationId);

    InnovationCatalogReport createReport(Long innovationId,
                                         String userName,
                                         String userLastname,
                                         String userEmail,
                                         String interestNarrative);

    InnovationCatalogReport createReportWithAudit(Long innovationId,
                                                  String userName,
                                                  String userLastname,
                                                  String userEmail,
                                                  String interestNarrative,
                                                  String modificationJustification);

    boolean deactivateReport(Long reportId);
}
