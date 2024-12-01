package org.demo.crm.feedback.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class FeedbackRequestDTO {

    @NotNull(message = "Customer ID cannot be null")
    private Long customerId;

    @NotNull(message = "Salesperson ID cannot be null")
    private Long salespersonId;

    @Min(value = 1, message = "Rating must be at least 1")
    @Max(value = 5, message = "Rating must be at most 5")
    private Integer rating; // 1 to 5

    @Size(max = 1000, message = "Comments must not exceed 1000 characters")
    private String comments;

    @NotNull(message = "Date cannot be null")
    private LocalDate date;
}
