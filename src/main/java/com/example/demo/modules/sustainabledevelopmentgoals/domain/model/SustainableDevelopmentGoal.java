package com.example.demo.modules.sustainabledevelopmentgoals.domain.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "sustainable_development_goals")
@Data
public class SustainableDevelopmentGoal {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "smo_code", unique = true)
    private Long smoCode;
    
    @Column(name = "short_name", length = 100)
    private String shortName;
    
    @Column(name = "full_name", length = 400)
    private String fullName;
    
    @Column(name = "icon", length = 200)
    private String icon;
    
    @Column(name = "description", length = 800)
    private String description;
}