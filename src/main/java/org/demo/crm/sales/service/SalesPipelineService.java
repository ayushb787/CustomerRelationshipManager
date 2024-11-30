package org.demo.crm.sales.service;

import org.demo.crm.lead.model.Lead;
import org.demo.crm.sales.dto.SalesPipelineRequest;
import org.demo.crm.sales.dto.SalesPipelineResponse;
import org.demo.crm.sales.model.SalesPipeline;
import org.demo.crm.sales.repository.SalesPipelineRepository;
import org.demo.crm.lead.repository.LeadRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class SalesPipelineService {

    @Autowired
    private SalesPipelineRepository salesPipelineRepository;

    @Autowired
    private LeadRepository leadRepository;

    public List<SalesPipelineResponse> getAllPipelineStages(Long leadId) {
        List<SalesPipeline> pipelineStages = salesPipelineRepository.findByLeadLeadId(leadId);
        return pipelineStages.stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public SalesPipelineResponse updatePipelineStage(Long pipelineId, SalesPipelineRequest request) {
        SalesPipeline salesPipeline = salesPipelineRepository.findById(pipelineId)
                .orElseThrow(() -> new RuntimeException("Sales Pipeline not found"));


        Optional<Lead> leadOptional = leadRepository.findById(request.getLeadId());
        if (!leadOptional.isPresent()) {
            throw new RuntimeException("Lead not found with id " + request.getLeadId());
        }


        salesPipeline.setLead(leadOptional.get());
        salesPipeline.setStage(request.getStage());
        salesPipeline.setProbability(request.getProbability());
        salesPipeline.setExpectedCloseDate(request.getExpectedCloseDate());

        salesPipelineRepository.save(salesPipeline);
        return mapToResponse(salesPipeline);
    }

    public SalesPipelineResponse createSalesPipeline(SalesPipelineRequest request) {
        SalesPipeline salesPipeline = new SalesPipeline();

        Optional<Lead> leadOptional = leadRepository.findById(request.getLeadId());
        if (!leadOptional.isPresent()) {
            throw new RuntimeException("Lead not found with id " + request.getLeadId());
        }

        salesPipeline.setLead(leadOptional.get());
        salesPipeline.setStage(request.getStage());
        salesPipeline.setProbability(request.getProbability());
        salesPipeline.setExpectedCloseDate(request.getExpectedCloseDate());

        salesPipelineRepository.save(salesPipeline);
        return mapToResponse(salesPipeline);
    }

    private SalesPipelineResponse mapToResponse(SalesPipeline salesPipeline) {
        SalesPipelineResponse response = new SalesPipelineResponse();
        response.setPipelineId(salesPipeline.getPipelineId());
        response.setLeadId(salesPipeline.getLead().getLeadId());
        response.setStage(salesPipeline.getStage());
        response.setProbability(salesPipeline.getProbability());
        response.setExpectedCloseDate(salesPipeline.getExpectedCloseDate());
        return response;
    }
}
