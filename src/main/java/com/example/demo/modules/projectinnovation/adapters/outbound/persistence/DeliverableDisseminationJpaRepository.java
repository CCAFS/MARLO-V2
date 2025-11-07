package com.example.demo.modules.projectinnovation.adapters.outbound.persistence;

import com.example.demo.modules.projectinnovation.domain.model.DeliverableDissemination;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliverableDisseminationJpaRepository extends JpaRepository<DeliverableDissemination, Long> {

    @Query("""
            SELECT d
            FROM DeliverableDissemination d
            WHERE d.deliverableId IN :deliverableIds
              AND (:phaseId IS NULL OR d.idPhase = :phaseId)
            """)
    List<DeliverableDissemination> findByDeliverableIdsAndPhase(@Param("deliverableIds") List<Long> deliverableIds,
                                                                @Param("phaseId") Long phaseId);
}
