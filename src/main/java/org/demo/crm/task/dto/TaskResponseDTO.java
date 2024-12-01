package org.demo.crm.task.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskResponseDTO {
    private Long taskId; // Auto-generated
    private Long assignedTo; // Salesperson ID
    private String description;
    private LocalDate dueDate;
    private String status; // Pending, In Progress, Completed
    private String priority; // Low, Medium, High
}
