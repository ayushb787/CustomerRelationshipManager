package org.demo.crm.communication.controller;

import org.demo.crm.auth.exception.UserNotFoundException;
import org.demo.crm.communication.entity.CommunicationLog;
import org.demo.crm.communication.service.CommunicationLogService;
import org.demo.crm.auth.model.ApiResponse;
import org.demo.crm.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/communication")
public class CommunicationLogController {


    @Autowired
    private CommunicationLogService communicationLogService;

    @PostMapping("/log")
    public ResponseEntity<ApiResponse<CommunicationLog>> logCommunication(
            @RequestParam Long customerId,
            @RequestParam String channel,
            @RequestParam String message,
            @RequestParam String status) {

        // Validate customer existence
        if (!communicationLogService.existsById(customerId)) {
            throw new UserNotFoundException("Customer not found with ID: " + customerId);
        }

        CommunicationLog log = communicationLogService.logCommunication(customerId, channel, message, status);
        return ResponseEntity.ok(ApiResponse.success(log, "Communication logged successfully"));
    }


    @PostMapping("/notify-customer/{id}")
    public ResponseEntity<ApiResponse<String>> notifyCustomer(
            @PathVariable Long id,
            @RequestParam String subject,
            @RequestParam String message) {
        try {
            communicationLogService.notifyCustomer(id, subject, message);
            return ResponseEntity.ok(ApiResponse.success("Email sent successfully!", "Notification sent to customer with ID: " + id));
        } catch (UserNotFoundException e) {
            return ResponseEntity.status(404).body(ApiResponse.error(e.getMessage(), 404));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(ApiResponse.error("Failed to send email: " + e.getMessage(), 500));
        }
    }


}
