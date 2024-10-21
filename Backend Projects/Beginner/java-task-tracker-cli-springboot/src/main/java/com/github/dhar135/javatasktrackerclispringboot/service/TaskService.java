package com.github.dhar135.javatasktrackerclispringboot.service;

import com.github.dhar135.javatasktrackerclispringboot.model.Task;
import com.github.dhar135.javatasktrackerclispringboot.model.TaskStatus;
import com.github.dhar135.javatasktrackerclispringboot.repository.TaskRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    private final TaskRepository taskRepository;

    public TaskService(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public Task addTask(String description) {
        Task task = new Task();
        task.setDescription(description);
        task.setStatus(TaskStatus.TODO);
        task.setCreatedAt(LocalDateTime.now());
        task.setUpdatedAt(LocalDateTime.now());
        return taskRepository.save(task);
    }

    public Task updateTask(Long id, String description) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid task id: " + id));
        task.setDescription(description);
        task.setUpdatedAt(LocalDateTime.now());
        return taskRepository.save(task);
    }

    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    public Task updateTaskStatus(Long id, TaskStatus status) {
        Task task = taskRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid task id: " + id));
        task.setStatus(status);
        task.setUpdatedAt(LocalDateTime.now());
        return taskRepository.save(task);
    }

    public Task markTaskAsInProgress(Long id) {
        return updateTaskStatus(id, TaskStatus.IN_PROGRESS);
    }

    public Task markTaskAsDone(Long id) {
        return updateTaskStatus(id, TaskStatus.DONE);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public List<Task> getTasksByStatus(TaskStatus status) {
        return taskRepository.findByStatus(status);
    }


}
