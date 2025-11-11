package com.example.demo.modules.innovationsubscription.application.service;

import com.example.demo.modules.innovationsubscription.domain.model.InnovationCatalogSubscription;
import com.example.demo.modules.innovationsubscription.domain.port.in.InnovationSubscriptionUseCase;
import com.example.demo.modules.innovationsubscription.domain.port.out.InnovationSubscriptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Application service for managing innovation catalog subscriptions.
 */
@Service
@Transactional
public class InnovationSubscriptionService implements InnovationSubscriptionUseCase {

    private static final Logger logger = LoggerFactory.getLogger(InnovationSubscriptionService.class);

    private final InnovationSubscriptionRepository subscriptionRepository;

    public InnovationSubscriptionService(InnovationSubscriptionRepository subscriptionRepository) {
        this.subscriptionRepository = subscriptionRepository;
    }

    @Override
    public InnovationCatalogSubscription subscribe(String email) {
        String normalizedEmail = validateAndNormalizeEmail(email);

        return subscriptionRepository.findByEmailIgnoreCase(normalizedEmail)
                .map(existing -> {
                    if (Boolean.TRUE.equals(existing.getIsActive())) {
                        logger.debug("Subscription already active for email {}", normalizedEmail);
                        return existing;
                    }
                    existing.setIsActive(true);
                    existing.setModificationJustification("Reactivated via subscription endpoint");
                    logger.info("Reactivated innovation subscription for {}", normalizedEmail);
                    return subscriptionRepository.save(existing);
                })
                .orElseGet(() -> {
                    InnovationCatalogSubscription subscription = new InnovationCatalogSubscription(normalizedEmail);
                    subscription.setModificationJustification("Created via subscription endpoint");
                    logger.info("Created new innovation subscription for {}", normalizedEmail);
                    return subscriptionRepository.save(subscription);
                });
    }

    @Override
    public boolean unsubscribe(String email) {
        String normalizedEmail = validateAndNormalizeEmail(email);

        return subscriptionRepository.findByEmailIgnoreCase(normalizedEmail)
                .map(existing -> {
                    if (!Boolean.TRUE.equals(existing.getIsActive())) {
                        logger.debug("Subscription already inactive for email {}", normalizedEmail);
                        return false;
                    }
                    existing.setIsActive(false);
                    existing.setModificationJustification("Deactivated via subscription endpoint");
                    subscriptionRepository.save(existing);
                    logger.info("Deactivated innovation subscription for {}", normalizedEmail);
                    return true;
                })
                .orElse(false);
    }

    private String validateAndNormalizeEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email is required");
        }
        String normalizedEmail = email.trim().toLowerCase();
        if (!normalizedEmail.contains("@")) {
            throw new IllegalArgumentException("Email must be valid");
        }
        return normalizedEmail;
    }
}
