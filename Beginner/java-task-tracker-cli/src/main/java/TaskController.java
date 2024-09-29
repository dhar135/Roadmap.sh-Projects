import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class TaskController {
    private List<Task> tasks;
    private final Path FILE_PATH = Path.of("tasks.json");

    public TaskController() {
        this.tasks = loadTasks();
    }

    private List<Task> loadTasks(){
        List<Task> stored_tasks = new ArrayList<>();

        if (!Files.exists(FILE_PATH)){
            return new ArrayList<>();
        }

        try {
            String jsonContent = Files.readString(FILE_PATH);
            String[] taskList = jsonContent.replace("[", "")
                                            .replace("]", "")
                                            .split("},");
            for (String taskJson : taskList){
                if (!taskJson.endsWith("}")){
                    taskJson = taskJson + "}";
                    stored_tasks.add(Task.fromJson(taskJson));
                } else {
                    stored_tasks.add(Task.fromJson(taskJson));
                }
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        return stored_tasks;
    }

    public void saveTasks(){
        StringBuilder sb = new StringBuilder();
        sb.append("[\n");
        for (int i = 0; i < tasks.size(); i++){
            sb.append(tasks.get(i).toJSON());
            if (i < tasks.size() - 1){
                sb.append(",\n");
            }
        }
        sb.append("\n]");

        String jsonContent = sb.toString();
        try {
            Files.writeString(FILE_PATH, jsonContent);
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public void addTask(String description){
        Task newTask = new Task(description);
        tasks.add(newTask);
        System.out.println("Task added successfully (ID: " + newTask.getId() + ")");
    }

    public void updateTask(String id, String description){
        Task task = findTask(id).orElseThrow(() -> new IllegalArgumentException("Task with id " + id + " not found"));
        task.setDescription(description);
    }

    public void deleteTask(String id){
        Task task = findTask(id).orElseThrow(() -> new IllegalArgumentException("Task with id " + id + " not found"));
        tasks.remove(task);
    }

    public void markTaskAsInProgress(String id){
        Task task = findTask(id).orElseThrow(() -> new IllegalArgumentException("Task with id " + id + " not found"));
        task.markInProgress();
    }

    public void markTaskAsDone(String id){
        Task task = findTask(id).orElseThrow(() -> new IllegalArgumentException("Task with id " + id + " not found"));
        task.markDone();
    }

    public void listTasks(String type){
        for (Task task : tasks){
            String status = task.getStatus().toString();
            if (type.equals("all") || type.equals(status)){
                System.out.println(task.toJSON());
            }
        }
    }

    
    public Optional<Task> findTask(String id) {
        return tasks.stream().filter((task) -> task.getId() == Integer.parseInt(id)).findFirst();
    }
    
}
