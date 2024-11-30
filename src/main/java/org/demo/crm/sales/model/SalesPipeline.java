package org.demo.crm.sales.model;

import jakarta.persistence.*;
import lombok.Data;
import org.demo.crm.lead.model.Lead;

import java.time.LocalDate;

@Entity
@Data
public class SalesPipeline {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long pipelineId;

    @ManyToOne
    @JoinColumn(name = "lead_id", referencedColumnName = "leadId", nullable = false)
    private Lead lead;  // This is a reference to the Lead entity

    private String stage;  // Stage in the pipeline (e.g., "Lead", "Negotiation", "Closed")
    private double probability;  // Probability of closing the deal
    private LocalDate expectedCloseDate;
}
