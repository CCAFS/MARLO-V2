package com.example.demo.modules.innovationreports.domain.port.out;

import com.example.demo.modules.innovationreports.domain.model.InnovationCatalogReport;

import java.util.List;
import java.util.Optional;

public interface InnovationReportRepository {

    InnovationCatalogReport save(InnovationCatalogReport report);

    List<InnovationCatalogReport> findActiveReportsByInnovationId(Long innovationId);

    List<InnovationCatalogReport> findActiveReportsByUserEmail(String userEmail);

    Long countActiveReportsByInnovationId(Long innovationId);

    Optional<InnovationCatalogReport> findById(Long id);

    boolean softDelete(Long reportId);
}
