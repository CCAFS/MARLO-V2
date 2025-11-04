package com.example.demo.modules.innovationreports.adapters.infrastructure.persistence;

import com.example.demo.modules.innovationreports.domain.model.InnovationCatalogReport;
import com.example.demo.modules.innovationreports.domain.port.out.InnovationReportRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Component
@Transactional
public class InnovationReportRepositoryAdapter implements InnovationReportRepository {

    private final InnovationCatalogReportJpaRepository jpaRepository;

    public InnovationReportRepositoryAdapter(InnovationCatalogReportJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public InnovationCatalogReport save(InnovationCatalogReport report) {
        return jpaRepository.save(report);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InnovationCatalogReport> findActiveReportsByInnovationId(Long innovationId) {
        return jpaRepository.findActiveByInnovationId(innovationId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InnovationCatalogReport> findActiveReportsByUserEmail(String userEmail) {
        return jpaRepository.findActiveByUserEmail(userEmail);
    }

    @Override
    @Transactional(readOnly = true)
    public Long countActiveReportsByInnovationId(Long innovationId) {
        return jpaRepository.countActiveByInnovationId(innovationId);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<InnovationCatalogReport> findById(Long id) {
        return jpaRepository.findById(id);
    }

    @Override
    public boolean softDelete(Long reportId) {
        return jpaRepository.softDelete(reportId) > 0;
    }
}
