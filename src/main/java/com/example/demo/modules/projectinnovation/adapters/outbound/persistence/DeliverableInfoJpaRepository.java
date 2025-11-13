package com.example.demo.modules.projectinnovation.adapters.outbound.persistence;

import com.example.demo.modules.projectinnovation.domain.model.DeliverableInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for querying deliverables_info metadata.
 */
@Repository
public interface DeliverableInfoJpaRepository extends JpaRepository<DeliverableInfo, Long> {

    /**
     * Find active deliverable info rows for the given deliverable ids and phase.
     */
    @Query("""
            SELECT d
            FROM DeliverableInfo d
            WHERE d.deliverableId IN :deliverableIds
              AND (d.isActive IS NULL OR d.isActive = true)
              AND (:phaseId IS NULL OR d.idPhase = :phaseId)
            """)
    List<DeliverableInfo> findActiveByDeliverableIdInAndPhase(@Param("deliverableIds") List<Long> deliverableIds,
                                                              @Param("phaseId") Long phaseId);
}
