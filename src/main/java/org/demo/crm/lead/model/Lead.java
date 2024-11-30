package org.demo.crm.lead.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Data
public class Lead {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long leadId;

    @Column(nullable = false)
    private Long customerId;

    @Column(nullable = false)
    private Long assignedTo; // User ID of salesperson

    @Column(nullable = false)
    private String status; // Example: Active, Inactive

    @Column(nullable = false)
    private String pipelineStage; // Example: Contacted, Negotiation, Closed

    @Column(nullable = false, updatable = false)
    private LocalDateTime dateCreated;

    private LocalDateTime lastUpdated;
}
