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
    private Lead lead;

    private String stage;
    private double probability;
    private LocalDate expectedCloseDate;
}
