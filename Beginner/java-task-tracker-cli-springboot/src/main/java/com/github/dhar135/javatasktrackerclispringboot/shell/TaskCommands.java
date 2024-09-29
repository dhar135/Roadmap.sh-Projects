package com.github.dhar135.javatasktrackerclispringboot.shell;

import com.github.dhar135.javatasktrackerclispringboot.model.Task;
import com.github.dhar135.javatasktrackerclispringboot.model.TaskStatus;
import com.github.dhar135.javatasktrackerclispringboot.service.TaskService;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;

import java.util.List;

@ShellComponent
public class TaskCommands {

    private final TaskService taskService;

    public TaskCommands(TaskService taskService) {
        this.taskService = taskService;
    }

    @ShellMethod("Add a new Task")
    public String add(@ShellOption String description) {
        Task task = taskService.addTask(description);
        return "Task added successfully (Id = " + task.getId() + ")";
    }

    @ShellMethod("Update a task")
    public String update(@ShellOption Long id, @ShellOption String description) {
        Task task = taskService.updateTask(id, description);
        return "Task updated successfully (Id = " + task.getId() + ")";
    }

    @ShellMethod("Delete a task")
    public String delete(@ShellOption Long id) {
        taskService.deleteTask(id);
        return "Task deleted successfully (Id = " + id + ")";
    }

    @ShellMethod("Mark a Task in progress")
    public String markInProgress(@ShellOption Long id) {
        Task task = taskService.markTaskAsInProgress(id);
        return "Task marked as in progress (Id = " + task.getId() + ")";
    }

    @ShellMethod("Mark a Task as Done")
    public String markDone(@ShellOption Long id) {
        Task task = taskService.markTaskAsDone(id);
        return "Task marked as done (Id = " + task.getId() + ")";
    }

    @ShellMethod("List all tasks")
    public String list(){
        return formatTaskList(taskService.getAllTasks());
    }

    @ShellMethod("List tasks by Status")
    public String listByStatus(@ShellOption TaskStatus status){
        return formatTaskList(taskService.getTasksByStatus(status));
    }

    public String formatTaskList(List<Task> taskList) {
        if (taskList.isEmpty()) {
            return "No tasks found";
        }
        StringBuilder builder = new StringBuilder();
        for (Task task : taskList) {
            builder.append(task.toString()).append("\n");
        }
        return builder.toString();
    }

}
