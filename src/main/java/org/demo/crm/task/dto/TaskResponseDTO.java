package org.demo.crm.task.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class TaskResponseDTO {
    private Long taskId;
    private Long assignedTo;
    private String description;
    private LocalDate dueDate;
    private String status;
    private String priority;
}
