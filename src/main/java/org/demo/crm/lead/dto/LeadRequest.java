package org.demo.crm.lead.dto;

import lombok.Data;

@Data
public class LeadRequest {
    private Long customerId;
    private Long assignedTo;
    private String status;
    private String pipelineStage;
}
