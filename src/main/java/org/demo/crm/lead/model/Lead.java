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
    private Long assignedTo;

//    In Progress, Qualified, or Lost
    @Column(nullable = false)
    private String status;

//    Prospect, Negotiation, or Closing
    @Column(nullable = false)
    private String pipelineStage;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dateCreated;

    private LocalDateTime lastUpdated;
}
