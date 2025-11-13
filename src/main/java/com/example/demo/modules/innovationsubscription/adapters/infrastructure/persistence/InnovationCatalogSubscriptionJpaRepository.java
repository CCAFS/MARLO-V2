package com.example.demo.modules.innovationsubscription.adapters.infrastructure.persistence;

import com.example.demo.modules.innovationsubscription.domain.model.InnovationCatalogSubscription;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InnovationCatalogSubscriptionJpaRepository extends JpaRepository<InnovationCatalogSubscription, Long> {

    Optional<InnovationCatalogSubscription> findByEmailIgnoreCase(String email);
}
