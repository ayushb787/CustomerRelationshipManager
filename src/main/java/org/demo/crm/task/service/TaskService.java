package org.demo.crm.task.service;

import org.demo.crm.auth.repository.UserRepository;
import org.demo.crm.notification.service.NotificationService;
import org.demo.crm.task.dto.TaskRequestDTO;
import org.demo.crm.task.dto.TaskResponseDTO;
import org.demo.crm.task.entity.Task;
import org.demo.crm.task.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationService notificationService;

    public TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO) {
        if (!userRepository.existsByUserIdAndRole(taskRequestDTO.getAssignedTo(), "Salesperson")) {
            throw new IllegalArgumentException(
                    "User with ID " + taskRequestDTO.getAssignedTo() + " does not exist or is not a salesperson."
            );
        }

        Task task = new Task();
        task.setAssignedTo(taskRequestDTO.getAssignedTo());
        task.setDescription(taskRequestDTO.getDescription());
        task.setDueDate(taskRequestDTO.getDueDate());
        task.setStatus(taskRequestDTO.getStatus());
        task.setPriority(taskRequestDTO.getPriority());

        Task savedTask = taskRepository.save(task);
        String message = "A new task has been assigned to you: " + task.getTaskId();
        notificationService.createNotification(task.getAssignedTo(), message, "NEW_TASK");
        return toResponseDTO(savedTask);
    }

    public Page<TaskResponseDTO> getTasksBySalesperson(Long salespersonId, Pageable pageable) {
        if (!userRepository.existsByUserIdAndRole(salespersonId, "Salesperson")) {
            throw new IllegalArgumentException(
                    "User with ID " + salespersonId + " does not exist or is not a salesperson."
            );
        }

        Page<Task> tasks = taskRepository.findByAssignedTo(salespersonId, pageable);
        return tasks.map(this::toResponseDTO);
    }

    public TaskResponseDTO updateTask(Long taskId, TaskRequestDTO taskRequestDTO) {
        Task task = taskRepository.findById(taskId).orElseThrow(
                () -> new IllegalArgumentException("Task ID " + taskId + " not found."));

        if (!userRepository.existsByUserIdAndRole(taskRequestDTO.getAssignedTo(), "Salesperson")) {
            throw new IllegalArgumentException(
                    "User with ID " + taskRequestDTO.getAssignedTo() + " does not exist or is not a salesperson.");
        }

        if (!taskRequestDTO.getStatus().matches("Pending|In Progress|Completed")) {
            throw new IllegalArgumentException("Status must be one of: Pending, In Progress, Completed.");
        }

        task.setAssignedTo(taskRequestDTO.getAssignedTo());
        task.setDescription(taskRequestDTO.getDescription());
        task.setDueDate(taskRequestDTO.getDueDate());
        task.setStatus(taskRequestDTO.getStatus());
        task.setPriority(taskRequestDTO.getPriority());

        Task updatedTask = taskRepository.save(task);
        String message = "A task has been updated. Please check: " + task.getTaskId();
        notificationService.createNotification(task.getAssignedTo(), message, "UPDATE_TASK");
        return toResponseDTO(updatedTask);
    }


    public Page<TaskResponseDTO> getAllTasks(Pageable pageable) {
        Page<Task> tasks = taskRepository.findAll(pageable);
        return tasks.map(this::toResponseDTO);
    }

    public TaskResponseDTO getTaskById(Long taskId) {
        Task task = taskRepository.findById(taskId).orElseThrow(
                () -> new IllegalArgumentException("Task ID " + taskId + " not found."));
        return toResponseDTO(task);
    }

    public void deleteTask(Long taskId) { 
        Task task = taskRepository.findById(taskId).orElseThrow(
                () -> new IllegalArgumentException("Task ID " + taskId + " not found.")
        );

        taskRepository.delete(task);
    }

    private TaskResponseDTO toResponseDTO(Task task) {
        TaskResponseDTO response = new TaskResponseDTO();
        response.setTaskId(task.getTaskId());
        response.setAssignedTo(task.getAssignedTo());
        response.setDescription(task.getDescription());
        response.setDueDate(task.getDueDate());
        response.setStatus(task.getStatus());
        response.setPriority(task.getPriority());
        return response;
    }
}
