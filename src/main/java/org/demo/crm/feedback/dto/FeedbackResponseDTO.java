package org.demo.crm.feedback.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FeedbackResponseDTO {

    private Long feedbackId;
    private Long customerId;
    private Long salespersonId;
    private Integer rating;
    private String comments;
    private LocalDate date;
}
