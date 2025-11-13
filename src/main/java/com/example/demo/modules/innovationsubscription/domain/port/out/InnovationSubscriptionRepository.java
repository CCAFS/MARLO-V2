package com.example.demo.modules.innovationsubscription.domain.port.out;

import com.example.demo.modules.innovationsubscription.domain.model.InnovationCatalogSubscription;
import java.util.Optional;

/**
 * Output port for persistence access to innovation catalog subscriptions.
 */
public interface InnovationSubscriptionRepository {

    InnovationCatalogSubscription save(InnovationCatalogSubscription subscription);

    Optional<InnovationCatalogSubscription> findByEmailIgnoreCase(String email);
}
