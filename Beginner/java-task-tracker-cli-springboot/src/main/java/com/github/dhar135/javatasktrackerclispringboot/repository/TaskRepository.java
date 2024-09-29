package com.github.dhar135.javatasktrackerclispringboot.repository;

import com.github.dhar135.javatasktrackerclispringboot.model.Task;
import com.github.dhar135.javatasktrackerclispringboot.model.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Long> {
    List<Task> findByStatus(TaskStatus status);
}
