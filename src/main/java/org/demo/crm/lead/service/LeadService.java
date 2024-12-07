package org.demo.crm.lead.service;

import org.demo.crm.auth.model.User;
import org.demo.crm.auth.repository.UserRepository;
import org.demo.crm.customer.repository.CustomerRepository;
import org.demo.crm.lead.dto.LeadRequest;
import org.demo.crm.lead.dto.LeadResponse;
import org.demo.crm.lead.model.Lead;
import org.demo.crm.lead.repository.LeadRepository;
import org.demo.crm.notification.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LeadService {

    @Autowired
    private LeadRepository leadRepository;

    @Autowired
    private NotificationService notificationService;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserRepository userRepository;

    public List<LeadResponse> getAllLeads() {
        return leadRepository.findAll().stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    public LeadResponse getLeadById(Long leadId) {
        Lead lead = leadRepository.findById(leadId)
                .orElseThrow(() -> new IllegalArgumentException("Lead not found"));
        return convertToResponseDTO(lead);
    }

    public LeadResponse createLead(LeadRequest leadRequest) {

        if (!customerRepository.existsById(leadRequest.getCustomerId())) {
            throw new IllegalArgumentException("Customer ID " + leadRequest.getCustomerId() + " does not exist");
        }

        if (!userRepository.existsById(leadRequest.getAssignedTo())) {
            throw new IllegalArgumentException("Assigned user ID " + leadRequest.getAssignedTo() + " does not exist");
        }

        Lead lead = convertToEntity(leadRequest);
        lead.setDateCreated(LocalDateTime.now());
        lead.setLastUpdated(LocalDateTime.now());
        Lead savedLead = leadRepository.save(lead);

        String message = "A new lead has been assigned to you: " + lead.getCustomerId();
        notificationService.createNotification(lead.getAssignedTo(), message, "NEW_LEAD");

        return convertToResponseDTO(savedLead);
    }

    public LeadResponse updateLead(Long leadId, LeadRequest leadRequest) {
        Lead lead = leadRepository.findById(leadId)
                .orElseThrow(() -> new IllegalArgumentException("Lead not found"));

        lead.setCustomerId(leadRequest.getCustomerId());
        lead.setAssignedTo(leadRequest.getAssignedTo());
        lead.setStatus(leadRequest.getStatus());
        lead.setPipelineStage(leadRequest.getPipelineStage());
        lead.setLastUpdated(LocalDateTime.now());

        Lead updatedLead = leadRepository.save(lead);
        return convertToResponseDTO(updatedLead);
    }

    public void deleteLead(Long leadId) {
        Lead lead = leadRepository.findById(leadId)
                .orElseThrow(() -> new IllegalArgumentException("Lead not found"));
        leadRepository.delete(lead);
    }

    private LeadResponse convertToResponseDTO(Lead lead) {
        LeadResponse response = new LeadResponse();
        response.setLeadId(lead.getLeadId());
        response.setCustomerId(lead.getCustomerId());
        response.setAssignedTo(lead.getAssignedTo());
        response.setStatus(lead.getStatus());
        response.setPipelineStage(lead.getPipelineStage());
        response.setDateCreated(lead.getDateCreated());
        response.setLastUpdated(lead.getLastUpdated());
        return response;
    }

    private Lead convertToEntity(LeadRequest leadRequest) {
        Lead lead = new Lead();
        lead.setCustomerId(leadRequest.getCustomerId());
        lead.setAssignedTo(leadRequest.getAssignedTo());
        lead.setStatus(leadRequest.getStatus());
        lead.setPipelineStage(leadRequest.getPipelineStage());
        return lead;
    }

    public List<User> getUsersByRole(String role) {
        return userRepository.findByRole(role);
    }
}
