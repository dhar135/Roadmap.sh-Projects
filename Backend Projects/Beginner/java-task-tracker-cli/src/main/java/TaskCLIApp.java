public class TaskCLIApp {
    public static void main(String[] args) {
        TaskController taskController = new TaskController();

        if (args.length < 1) {
            System.out.println("Usage: java TaskCLIApp <command> [args]");
            return;
        }

        String command = args[0];

        try {
            switch (command) {
                case "add":
                    if (args.length < 2) {
                        System.out.println("Usage: java TaskCLIApp add <task_description>");
                        return;
                    }
                    taskController.addTask(args[1]);
                    break;
                case "update":
                    if (args.length < 3) {
                        System.out.println("Usage: java TaskCLIApp update <task_id> <new_description>");
                        return;
                    }
                    taskController.updateTask(args[1], args[2]);
                    System.out.println("Task updated successfully (ID: " + args[1] + ")");
                    break;
                case "delete":
                    if (args.length < 2) {
                        System.out.println("Usage: java TaskCLIApp delete <task_id>");
                        return;
                    }
                    taskController.deleteTask(args[1]);
                    System.out.println("Task deleted successfully (ID: " + args[1] + ")");
                    break;
                case "mark-in-progress":
                    if (args.length < 2) {
                        System.out.println("Usage: java TaskCLIApp mark-in-progress <task_id>");
                        return;
                    }
                    taskController.markTaskAsInProgress(args[1]);
                    System.out.println("Task marked as in progress (ID: " + args[1] + ")");
                    break;
                case "mark-done":
                    if (args.length < 2) {
                        System.out.println("Usage: java TaskCLIApp mark-done <task_id>");
                        return;
                    }
                    taskController.markTaskAsDone(args[1]);
                    System.out.println("Task marked as done (ID: " + args[1] + ")");
                    break;
                case "list":
                    if (args.length < 2) {
                        taskController.listTasks("all");
                    } else {
                        Task.TaskStatus filterStatus;
                        try {
                            filterStatus = Task.TaskStatus.valueOf(args[1].toUpperCase().replace("-", "_"));
                        } catch (IllegalArgumentException e) {
                            System.out.println("Invalid status: " + args[1]);
                            return;
                        }
                        taskController.listTasks(filterStatus.toString());
                    }
                    break;
                default:
                    System.out.println("Invalid command");
                    break;
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid task ID. Please provide a valid number.");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid status. Use 'todo', 'in-progress', or 'done'.");
        }
        taskController.saveTasks();
    }
}