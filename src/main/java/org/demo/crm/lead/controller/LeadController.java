package org.demo.crm.lead.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.demo.crm.auth.model.ApiResponse;
import org.demo.crm.auth.model.User;
import org.demo.crm.lead.dto.LeadRequest;
import org.demo.crm.lead.dto.LeadResponse;
import org.demo.crm.lead.service.LeadService;
import org.demo.crm.security.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/leads")
public class LeadController {

    @Autowired
    private LeadService leadService;

    @Autowired
    private JwtUtils jwtUtil;

    @GetMapping
    public ApiResponse<List<LeadResponse>> getAllLeads(HttpServletRequest request) {
        validateRequest(request);
        List<LeadResponse> leads = leadService.getAllLeads();
        return ApiResponse.success(leads, "Leads retrieved successfully");
    }

    @GetMapping("/{leadId}")
    public ApiResponse<LeadResponse> getLeadById(@PathVariable Long leadId, HttpServletRequest request) {
        validateRequest(request);
        LeadResponse lead = leadService.getLeadById(leadId);
        return ApiResponse.success(lead, "Lead retrieved successfully");
    }

    @PostMapping
    public ApiResponse<LeadResponse> createLead(@Valid @RequestBody LeadRequest leadRequest, HttpServletRequest request) {
        validateRequestForAdmin(request);
        LeadResponse createdLead = leadService.createLead(leadRequest);
        return ApiResponse.success(createdLead, "Lead created successfully");
    }

    @PutMapping("/{leadId}")
    public ApiResponse<LeadResponse> updateLead(
            @PathVariable Long leadId,
            @Valid @RequestBody LeadRequest leadRequest,
            HttpServletRequest request) {
        validateRequestForAdmin(request);
        LeadResponse updatedLead = leadService.updateLead(leadId, leadRequest);
        return ApiResponse.success(updatedLead, "Lead updated successfully");
    }

    @DeleteMapping("/{leadId}")
    public ApiResponse<String> deleteLead(@PathVariable Long leadId, HttpServletRequest request) {
        validateRequest(request);
        leadService.deleteLead(leadId);
        return ApiResponse.success("Lead deleted successfully", "Lead deleted successfully");
    }

    private void validateRequest(HttpServletRequest request) {
        String token = jwtUtil.extractToken(request);
        if (token == null || !jwtUtil.validateToken(token)) {
            throw new IllegalArgumentException("Invalid or expired token");
        }
    }

    private void validateRequestForAdmin(HttpServletRequest request) {
        String token = jwtUtil.extractToken(request);
        if (token == null || !jwtUtil.validateToken(token)) {
            throw new IllegalArgumentException("Invalid or expired token");
        }
        String role = jwtUtil.extractClaims(token).get("role", String.class);
        if (!"Admin".equals(role)) {
            throw new SecurityException("Access denied: Admin role required");
        }
    }

    @GetMapping("/users")
    public ResponseEntity<ApiResponse<List<User>>> getUsersByRole(@RequestParam String role, HttpServletRequest request) {
        try {
            validateRequestForAdmin(request);
            List<User> users = leadService.getUsersByRole(role);
            if (users.isEmpty()) {
                return ResponseEntity.ok(ApiResponse.success(users, "No users found with the role " + role));
            }
            return ResponseEntity.ok(ApiResponse.success(users, "Users fetched successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(ApiResponse.error("An error occurred: " + e.getMessage(), 500));
        }
    }
}
