package com.example.demo.modules.projectinnovation.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Phase entity representing the phases table
 * Contains information about project phases including cycle and year
 */
@Entity
@Table(name = "phases")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Phase {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "description", columnDefinition = "TEXT")
    private String description; // This field represents the 'cycle'
    
    @Column(name = "year")
    private Integer year;
    
    /**
     * Gets the cycle from the description field
     * @return The cycle extracted from description or the full description if extraction fails
     */
    public String getCycle() {
        if (description == null) return "Reporting"; // Default value
        
        // If description contains "Reporting" or "Planning", extract that value
        String lowerDesc = description.toLowerCase();
        if (lowerDesc.contains("reporting")) {
            return "Reporting";
        } else if (lowerDesc.contains("planning")) {
            return "Planning";
        }
        
        // If extraction fails, use the full description
        return description;
    }
}