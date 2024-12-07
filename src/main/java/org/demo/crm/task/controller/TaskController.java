package org.demo.crm.task.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.demo.crm.security.JwtUtils;
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

    @Autowired
    private JwtUtils jwtUtil;
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
    @PutMapping("/{taskId}/update")
    public ResponseEntity<ApiResponse<TaskResponseDTO>> updateTaskStatus(
            @PathVariable Long taskId,
            @Valid @RequestBody TaskRequestDTO taskRequestDTO) {
        TaskResponseDTO updatedTask = taskService.updateTask(taskId, taskRequestDTO);
        return ResponseEntity.ok(ApiResponse.success(updatedTask, "Task status updated successfully"));
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

    // Get all tasks (Admin only)
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<TaskResponseDTO>>> getAllTasks(HttpServletRequest request) {
        // Check if the current user is an Admin
        validateRequestForAdmin(request);

        List<TaskResponseDTO> tasks = taskService.getAllTasks();
        return ResponseEntity.ok(ApiResponse.success(tasks, "All tasks retrieved successfully"));
    }

    // Get task by ID
    @GetMapping("/{taskId}")
    public ResponseEntity<ApiResponse<TaskResponseDTO>> getTaskById(@PathVariable Long taskId) {
        TaskResponseDTO task = taskService.getTaskById(taskId);
        return ResponseEntity.ok(ApiResponse.success(task, "Task retrieved successfully"));
    }

    /* Delete a task by ID */@DeleteMapping("/{taskId}/delete")
    public ResponseEntity<ApiResponse<String>> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.ok(ApiResponse.success(null, "Task deleted successfully"));
    }

}
