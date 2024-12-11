package org.demo.crm.task.entity;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDate;

@Entity
@Data
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    @Column(nullable = false)
    private Long assignedTo;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private LocalDate dueDate;
//    Pending, In Progress, or Completed
    @Column(nullable = false)
    private String status;
//    Low, Medium or High
    @Column(nullable = false)
    private String priority;
}
