package org.demo.crm.task.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.demo.crm.security.JwtUtils;
import org.demo.crm.task.dto.TaskRequestDTO;
import org.demo.crm.task.dto.TaskResponseDTO;
import org.demo.crm.task.service.TaskService;
import org.demo.crm.auth.model.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<TaskResponseDTO>> createTask(@Valid @RequestBody TaskRequestDTO taskRequestDTO) {
        TaskResponseDTO createdTask = taskService.createTask(taskRequestDTO);
        return ResponseEntity.ok(ApiResponse.success(createdTask, "Task created successfully"));
    }

    @GetMapping("/salesperson/{id}")
    public ResponseEntity<ApiResponse<Page<TaskResponseDTO>>> getTasksBySalesperson(@PathVariable Long id,  @RequestParam(defaultValue = "0") int page,
                                                                                    @RequestParam(defaultValue = "10") int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<TaskResponseDTO> tasks = taskService.getTasksBySalesperson(id, pageable);
        return ResponseEntity.ok(ApiResponse.success(tasks, "Tasks retrieved successfully"));
    }

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

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<Page<TaskResponseDTO>>> getAllTasks(HttpServletRequest request, @RequestParam(defaultValue = "0") int page,
                                                                          @RequestParam(defaultValue = "10") int size) {

        validateRequestForAdmin(request);
        Pageable pageable = PageRequest.of(page, size);
        Page<TaskResponseDTO> tasks = taskService.getAllTasks(pageable);
        return ResponseEntity.ok(ApiResponse.success(tasks, "All tasks retrieved successfully"));
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<ApiResponse<TaskResponseDTO>> getTaskById(@PathVariable Long taskId) {
        TaskResponseDTO task = taskService.getTaskById(taskId);
        return ResponseEntity.ok(ApiResponse.success(task, "Task retrieved successfully"));
    }

    @DeleteMapping("/{taskId}/delete")
    public ResponseEntity<ApiResponse<String>> deleteTask(@PathVariable Long taskId) {
        taskService.deleteTask(taskId);
        return ResponseEntity.ok(ApiResponse.success(null, "Task deleted successfully"));
    }

}
