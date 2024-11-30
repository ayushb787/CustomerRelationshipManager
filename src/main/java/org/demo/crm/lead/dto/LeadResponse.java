package org.demo.crm.lead.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class LeadResponse {
    private Long leadId;
    private Long customerId;
    private Long assignedTo;
    private String status;
    private String pipelineStage;
    private LocalDateTime dateCreated;
    private LocalDateTime lastUpdated;
}
