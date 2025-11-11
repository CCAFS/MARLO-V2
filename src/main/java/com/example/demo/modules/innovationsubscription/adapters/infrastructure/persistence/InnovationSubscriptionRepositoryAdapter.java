package com.example.demo.modules.innovationsubscription.adapters.infrastructure.persistence;

import com.example.demo.modules.innovationsubscription.domain.model.InnovationCatalogSubscription;
import com.example.demo.modules.innovationsubscription.domain.port.out.InnovationSubscriptionRepository;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class InnovationSubscriptionRepositoryAdapter implements InnovationSubscriptionRepository {

    private final InnovationCatalogSubscriptionJpaRepository jpaRepository;

    public InnovationSubscriptionRepositoryAdapter(InnovationCatalogSubscriptionJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public InnovationCatalogSubscription save(InnovationCatalogSubscription subscription) {
        return jpaRepository.save(subscription);
    }

    @Override
    public Optional<InnovationCatalogSubscription> findByEmailIgnoreCase(String email) {
        return jpaRepository.findByEmailIgnoreCase(email);
    }
}
