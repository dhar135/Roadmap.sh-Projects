# GitHub User Activity CLI

This project is my implementation of the [GitHub User Activity CLI project](https://roadmap.sh/projects/github-user-activity) from Roadmap.sh. It's a command-line interface application that fetches and displays a GitHub user's recent activity using the GitHub API.

## Project Overview

This application allows users to view anyone's recent GitHub activity directly from their terminal. It provides a straightforward way to track actions like push events, pull requests, repository creation, and more, making it easier to monitor GitHub activity without visiting the website.

## Features

- Command-line interface accepting GitHub username as an argument
- Fetches user's recent GitHub activities using the GitHub API
- Displays formatted activity information including:
    - Push events with commit counts
    - Repository creation events
    - Pull request activities
    - Repository starring
    - Branch creation and deletion events
- Comprehensive error handling for various scenarios
- Detailed logging system for debugging and monitoring
- Extensive test coverage

## Technical Details

The project is built using:
- Java 23
- Maven for build management
- JUnit 5 for testing
- Built-in Java HTTP Client for API requests
- Jackson for JSON processing

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.6 or higher

### Installation

1. Clone the repository:
  ```bash
  git clone https://github.com/yourusername/github-activity-cli.git
  cd github-activity-cli
  ```

2. Build the project:
  ```bash
  mvn clean install
  ```

### Usage

Run the application using:
```bash
java -jar target/github-activity-cli.jar <username>
```

For example:
```bash
java -jar target/github-activity-cli.jar octocat
```

The output will show recent GitHub activities:
```
Github user activity fetched!
There are a total of 5 Github events:
Formatted Events:
- Pushed 3 commits to octocat/Hello-World
- Created branch named feature-branch from master branch: main in repository: octocat/Hello-World
- Starred octocat/Spoon-Knife
```

## Testing

The project includes comprehensive tests covering:
- Success scenarios
- Error handling
- Different event type formatting
- Network timeouts
- Invalid usernames

Run tests using:
```bash
mvn test
```

## Logging

The logging system captures:
- API requests and responses
- Error conditions
- Event processing details
- Rate limit tracking

## Error Handling

The application handles various error scenarios gracefully:
- Invalid usernames
- Network timeouts
- API rate limiting
- Malformed responses
- Missing data fields

## Future Improvements

Potential enhancements for the project:
- Activity filtering by event type
- Caching for improved performance
- Support for GitHub authentication
- Interactive mode with real-time updates
- Custom output formatting options

## Contributing

Feel free to fork the project and submit pull requests. For major changes, please open an issue first to discuss the proposed changes.

## License

This project is licensed under the MIT License - see the LICENSE file for details.

## Acknowledgments

- [Roadmap.sh](https://roadmap.sh) for the project idea and requirements
- GitHub API documentation and team
- https://roadmap.sh/projects/github-user-activity
  
