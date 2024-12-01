package org.demo.crm.task.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskRequestDTO {

    @NotNull(message = "Salesperson ID is required.")
    private Long assignedTo; // Salesperson ID

    @NotBlank(message = "Task description cannot be blank.")
    private String description;

    @NotNull(message = "Due date is required.")
    @Future(message = "Due date must be a future date.")
    private LocalDate dueDate;

    @NotBlank(message = "Status is required.")
    @Pattern(
            regexp = "Pending|In Progress|Completed",
            message = "Status must be one of: Pending, In Progress, Completed."
    )
    private String status;

    @NotBlank(message = "Priority is required.")
    @Pattern(
            regexp = "Low|Medium|High",
            message = "Priority must be one of: Low, Medium, High."
    )
    private String priority;
}
