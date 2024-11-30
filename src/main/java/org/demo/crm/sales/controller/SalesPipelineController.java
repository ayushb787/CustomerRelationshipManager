package org.demo.crm.sales.controller;

import org.demo.crm.auth.model.ApiResponse;
import org.demo.crm.sales.dto.SalesPipelineRequest;
import org.demo.crm.sales.dto.SalesPipelineResponse;
import org.demo.crm.sales.service.SalesPipelineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sales-pipeline")
public class SalesPipelineController {

    @Autowired
    private SalesPipelineService salesPipelineService;

    @GetMapping("/{leadId}")
    public ApiResponse<List<SalesPipelineResponse>> getAllPipelineStages(@PathVariable Long leadId) {
        List<SalesPipelineResponse> response = salesPipelineService.getAllPipelineStages(leadId);
        return ApiResponse.success(response, "Sales pipeline stages retrieved successfully");
    }

    @PutMapping("/{pipelineId}")
    public ApiResponse<SalesPipelineResponse> updatePipelineStage(
            @PathVariable Long pipelineId, @RequestBody SalesPipelineRequest request) {
        SalesPipelineResponse response = salesPipelineService.updatePipelineStage(pipelineId, request);
        return ApiResponse.success(response, "Sales pipeline stage updated successfully");
    }

    @PostMapping
    public ApiResponse<SalesPipelineResponse> createSalesPipeline(@RequestBody SalesPipelineRequest request) {
        SalesPipelineResponse response = salesPipelineService.createSalesPipeline(request);
        return ApiResponse.success(response, "Sales pipeline created successfully");
    }
}
