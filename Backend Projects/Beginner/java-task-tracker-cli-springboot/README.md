# Task Tracker

Task Tracker is a command-line interface (CLI) application built with Spring Boot that helps you manage your to-do list. It allows you to add, update, delete, and list tasks, as well as mark them as in progress or done.

## Features

- Add new tasks
- Update existing tasks
- Delete tasks
- Mark tasks as in progress or done
- List all tasks
- List tasks by status (todo, in progress, done)

## Technologies Used

- Java 11
- Spring Boot 2.6.3
- Spring Shell
- Spring Data JPA
- H2 Database

## Prerequisites

Before you begin, ensure you have met the following requirements:

- You have installed Java 11 or later
- You have installed Maven

## Installation

To install Task Tracker, follow these steps:

1. Clone the repository:
   ```
   git clone https://github.com/yourusername/task-tracker.git
   ```
2. Navigate to the project directory:
   ```
   cd task-tracker
   ```
3. Build the project:
   ```
   mvn clean install
   ```

## Usage

To use Task Tracker, follow these steps:

1. Run the application:
   ```
   mvn spring-boot:run
   ```
2. Once the application starts, you'll see a prompt:
   ```
   task-tracker:>
   ```
3. You can now enter commands to manage your tasks.

### Commands

- Add a new task:
  ```
  task-tracker:>add "Buy groceries"
  ```

- Update a task:
  ```
  task-tracker:>update 1 "Buy groceries and cook dinner"
  ```

- Delete a task:
  ```
  task-tracker:>delete 1
  ```

- Mark a task as in progress:
  ```
  task-tracker:>mark-in-progress 1
  ```

- Mark a task as done:
  ```
  task-tracker:>mark-done 1
  ```

- List all tasks:
  ```
  task-tracker:>list
  ```

- List tasks by status:
  ```
  task-tracker:>list-by-status TODO
  ```

## Configuration

The application uses an H2 database by default. The database file is stored in the current directory as `taskdb`. You can modify the database configuration in the `src/main/resources/application.properties` file.

## Contributing to Task Tracker

To contribute to Task Tracker, follow these steps:

1. Fork this repository.
2. Create a branch: `git checkout -b <branch_name>`.
3. Make your changes and commit them: `git commit -m '<commit_message>'`
4. Push to the original branch: `git push origin <project_name>/<location>`
5. Create the pull request.

Alternatively, see the GitHub documentation on [creating a pull request](https://help.github.com/articles/creating-a-pull-request/).

## Contact

If you want to contact me, you can reach me at `<your_email@example.com>`.

## License

This project uses the following license: [<license_name>](<link_to_license>).
