package com.taskmanagement.taskmanagement.service;

import com.taskmanagement.taskmanagement.entity.Task;
import com.taskmanagement.taskmanagement.repository.TaskRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    @Autowired
    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }
    public Optional<Task> getTaskById(Long id) {
        return taskRepository.findById(id);
    }
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }
    public Task updateTask(Task task, Long id) {
        Optional<Task> taskOptional = taskRepository.findById(id);

        if (taskOptional.isPresent()) {
            Task existingTask = taskOptional.get();

            // Update the fields of existingTask with new values from task
            existingTask.setTitle(task.getTitle());
            existingTask.setDescription(task.getDescription());
            existingTask.setStatus(task.getStatus());
            // Add other necessary field updates here

            return taskRepository.save(existingTask);
        } else {
            throw new EntityNotFoundException("Task with id " + id + " not found");
        }
    }
    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new EntityNotFoundException("Task with ID " + id + " not found.");
        }
        taskRepository.deleteById(id);
    }
}
