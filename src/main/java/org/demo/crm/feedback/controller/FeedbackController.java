package org.demo.crm.feedback.controller;

import jakarta.validation.Valid;
import org.demo.crm.auth.model.ApiResponse;
import org.demo.crm.feedback.dto.FeedbackRequestDTO;
import org.demo.crm.feedback.dto.FeedbackResponseDTO;
import org.demo.crm.feedback.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

@RestController
@RequestMapping("/api/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    // Add Feedback
    @PostMapping("/add")
    public ResponseEntity<ApiResponse<FeedbackResponseDTO>> addFeedback(
            @Valid @RequestBody FeedbackRequestDTO feedbackRequestDTO) {
        FeedbackResponseDTO savedFeedback = feedbackService.addFeedback(feedbackRequestDTO);
        return ResponseEntity.ok(ApiResponse.success(savedFeedback, "Feedback added successfully"));
    }

    // Get Feedback by Customer
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<ApiResponse<List<FeedbackResponseDTO>>> getFeedbackByCustomer(
            @PathVariable Long customerId) {
        List<FeedbackResponseDTO> feedbackList = feedbackService.getFeedbackByCustomer(customerId);
        return ResponseEntity.ok(ApiResponse.success(feedbackList, "Feedback retrieved successfully for customer"));
    }

    // Get Feedback by Salesperson
    @GetMapping("/salesperson/{salespersonId}")
    public ResponseEntity<ApiResponse<List<FeedbackResponseDTO>>> getFeedbackBySalesperson(
            @PathVariable Long salespersonId) {
        List<FeedbackResponseDTO> feedbackList = feedbackService.getFeedbackBySalesperson(salespersonId);
        return ResponseEntity.ok(ApiResponse.success(feedbackList, "Feedback retrieved successfully for salesperson"));
    }
}
