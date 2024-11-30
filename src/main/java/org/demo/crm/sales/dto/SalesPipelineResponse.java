package org.demo.crm.sales.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SalesPipelineResponse {

    private Long pipelineId;
    private Long leadId;
    private String stage;
    private double probability;
    private LocalDate expectedCloseDate;
}
