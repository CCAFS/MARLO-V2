package com.example.demo.modules.projectinnovation.adapters.outbound.persistence;

import com.example.demo.modules.projectinnovation.domain.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * JPA Repository for User entity
 * Handles data access for user information
 */
@Repository
public interface UserJpaRepository extends JpaRepository<User, Long> {
    
    /**
     * Find user by ID - uses the default findById method
     */
}