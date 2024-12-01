package org.demo.crm.task.controller;

import org.demo.crm.task.dto.TaskRequestDTO;
import org.demo.crm.task.dto.TaskResponseDTO;
import org.demo.crm.task.service.TaskService;
import org.demo.crm.auth.model.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@Validated
public class TaskController {

    @Autowired
    private TaskService taskService;

    // Create a new task
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<TaskResponseDTO>> createTask(@Valid @RequestBody TaskRequestDTO taskRequestDTO) {
        TaskResponseDTO createdTask = taskService.createTask(taskRequestDTO);
        return ResponseEntity.ok(ApiResponse.success(createdTask, "Task created successfully"));
    }

    // Get tasks for a salesperson
    @GetMapping("/salesperson/{id}")
    public ResponseEntity<ApiResponse<List<TaskResponseDTO>>> getTasksBySalesperson(@PathVariable Long id) {
        List<TaskResponseDTO> tasks = taskService.getTasksBySalesperson(id);
        return ResponseEntity.ok(ApiResponse.success(tasks, "Tasks retrieved successfully"));
    }

    // Update task status
    @PutMapping("/{taskId}/status")
    public ResponseEntity<ApiResponse<TaskResponseDTO>> updateTaskStatus(
            @PathVariable Long taskId,
            @RequestParam String status) {
        TaskResponseDTO updatedTask = taskService.updateTaskStatus(taskId, status);
        return ResponseEntity.ok(ApiResponse.success(updatedTask, "Task status updated successfully"));
    }
}
