package org.demo.crm.sales.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class SalesPipelineRequest {

    private Long leadId;  // Lead ID that will be used to fetch the Lead entity
    private String stage;
    private double probability;
    private LocalDate expectedCloseDate;
}
