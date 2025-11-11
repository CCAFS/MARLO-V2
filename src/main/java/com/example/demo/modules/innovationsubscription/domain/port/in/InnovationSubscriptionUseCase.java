package com.example.demo.modules.innovationsubscription.domain.port.in;

import com.example.demo.modules.innovationsubscription.domain.model.InnovationCatalogSubscription;

/**
 * Input port for innovation subscriptions.
 */
public interface InnovationSubscriptionUseCase {

    /**
     * Creates or reactivates an innovation subscription for the provided email.
     *
     * @param email email to subscribe
     * @return stored subscription
     */
    InnovationCatalogSubscription subscribe(String email);

    /**
     * Performs a logical deletion (deactivation) of the subscription for the given email.
     *
     * @param email email to deactivate
     * @return true if a subscription was deactivated, false otherwise
     */
    boolean unsubscribe(String email);
}
