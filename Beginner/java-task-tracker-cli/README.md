# Task Tracker CLI

A command-line interface application for tracking and managing your tasks. Built with Java, this application allows you to create, update, and manage tasks while storing them persistently in a JSON file.

## Features

- Add new tasks with descriptions
- Update existing tasks
- Delete tasks
- Mark tasks as in-progress or done
- List all tasks
- Filter tasks by status (todo, in-progress, done)
- Persistent storage using JSON
- Automatic task ID generation
- Timestamp tracking for task creation and updates

## Installation

1. Ensure you have Java Development Kit (JDK) installed on your system
2. Clone this repository:
```bash
git clone <repository-url>
cd task-tracker-cli
```
3. Compile the Java files:
```bash
javac TaskCLIApp.java Task.java TaskController.java
```

## Usage

The application supports the following commands:

### Add a Task
```bash
java TaskCLIApp add "Buy groceries"
```

### Update a Task
```bash
java TaskCLIApp update 1 "Buy groceries and cook dinner"
```

### Delete a Task
```bash
java TaskCLIApp delete 1
```

### Mark Task Status
```bash
java TaskCLIApp mark-in-progress 1
java TaskCLIApp mark-done 1
```

### List Tasks
List all tasks:
```bash
java TaskCLIApp list
```

List tasks by status:
```bash
java TaskCLIApp list todo
java TaskCLIApp list in-progress
java TaskCLIApp list done
```

## Task Properties

Each task contains the following properties:

- `id`: Unique identifier (auto-generated)
- `description`: Task description
- `status`: Current status (TODO, IN_PROGRESS, DONE)
- `createdAt`: Timestamp of task creation
- `updatedAt`: Timestamp of last update

## Data Storage

Tasks are stored in a `tasks.json` file in the application directory. The file is automatically created if it doesn't exist. Each task is stored in JSON format with all its properties.

Example task JSON structure:
```json
{
  "id": 1,
  "description": "Buy groceries",
  "status": "TODO",
  "createdAt": "2024-10-16T10:30:00",
  "updatedAt": "2024-10-16T10:30:00"
}
```

## Error Handling

The application includes error handling for common scenarios:

- Invalid task IDs
- Non-existent tasks
- Invalid status values
- Missing command arguments
- File I/O errors

## Technical Details

- Built with pure Java (no external dependencies)
- Uses native Java file I/O operations
- Implements custom JSON parsing and generation
- Thread-safe task ID generation
- ISO-8601 timestamp format

## Project Structure

- `TaskCLIApp.java`: Main application entry point and command handling
- `Task.java`: Task model and JSON conversion logic
- `TaskController.java`: Business logic and file operations

## Contributing

Feel free to submit issues and enhancement requests!

## License

[Add your chosen license here]