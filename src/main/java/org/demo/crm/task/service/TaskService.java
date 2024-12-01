package org.demo.crm.task.service;

import org.demo.crm.auth.repository.UserRepository;
import org.demo.crm.task.dto.TaskRequestDTO;
import org.demo.crm.task.dto.TaskResponseDTO;
import org.demo.crm.task.entity.Task;
import org.demo.crm.task.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository; // To validate the assignedTo field

    public TaskResponseDTO createTask(TaskRequestDTO taskRequestDTO) {
        // Validate if the assignedTo user exists and has the role "Salesperson"
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
        return toResponseDTO(savedTask);
    }

    public List<TaskResponseDTO> getTasksBySalesperson(Long salespersonId) {
        // Ensure that the salesperson exists and has the role "Salesperson"
        if (!userRepository.existsByUserIdAndRole(salespersonId, "Salesperson")) {
            throw new IllegalArgumentException(
                    "User with ID " + salespersonId + " does not exist or is not a salesperson."
            );
        }

        List<Task> tasks = taskRepository.findByAssignedTo(salespersonId);
        return tasks.stream().map(this::toResponseDTO).collect(Collectors.toList());
    }

    public TaskResponseDTO updateTaskStatus(Long taskId, String status) {
        Task task = taskRepository.findById(taskId).orElseThrow(
                () -> new IllegalArgumentException("Task ID " + taskId + " not found."));

        // Validate status value
        if (!status.matches("Pending|In Progress|Completed")) {
            throw new IllegalArgumentException("Status must be one of: Pending, In Progress, Completed.");
        }

        task.setStatus(status);
        Task updatedTask = taskRepository.save(task);
        return toResponseDTO(updatedTask);
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
