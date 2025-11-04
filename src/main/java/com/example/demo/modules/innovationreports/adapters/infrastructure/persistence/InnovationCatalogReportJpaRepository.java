package com.example.demo.modules.innovationreports.adapters.infrastructure.persistence;

import com.example.demo.modules.innovationreports.domain.model.InnovationCatalogReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InnovationCatalogReportJpaRepository extends JpaRepository<InnovationCatalogReport, Long> {

    @Query("SELECT r FROM InnovationCatalogReport r " +
           "WHERE r.innovationId = :innovationId AND r.isActive = true " +
           "ORDER BY r.activeSince DESC")
    List<InnovationCatalogReport> findActiveByInnovationId(@Param("innovationId") Long innovationId);

    @Query("SELECT r FROM InnovationCatalogReport r " +
           "WHERE r.userEmail = :userEmail AND r.isActive = true " +
           "ORDER BY r.activeSince DESC")
    List<InnovationCatalogReport> findActiveByUserEmail(@Param("userEmail") String userEmail);

    @Query("SELECT COUNT(r) FROM InnovationCatalogReport r " +
           "WHERE r.innovationId = :innovationId AND r.isActive = true")
    Long countActiveByInnovationId(@Param("innovationId") Long innovationId);

    @Modifying
    @Query("UPDATE InnovationCatalogReport r SET r.isActive = false, r.activeSince = CURRENT_TIMESTAMP " +
           "WHERE r.id = :reportId AND r.isActive = true")
    int softDelete(@Param("reportId") Long reportId);
}
