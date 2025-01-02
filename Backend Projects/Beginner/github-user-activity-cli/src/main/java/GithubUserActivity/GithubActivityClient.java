package GithubUserActivity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class GithubActivityClient {

    private final String BASE_URL = "https://api.github.com/users/%s/events";
    private final HttpClient client;
    private final ObjectMapper objectMapper;
    private static final Logger logger = Logger.getLogger(GithubActivityClient.class.getName());

    public GithubActivityClient() {
        client = HttpClient.newHttpClient();
        objectMapper = new ObjectMapper();
        logger.info("GithubActivityClient initialized successfully");
    }


    public List<GithubEvent> fetchUserEvents(String username) throws IOException, InterruptedException {
        logger.info("Fetching events for user: " + username);
        String url = String.format(BASE_URL, username);

        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(url))
                    .header("Accept", "application/vnd.github.v3+json")
                    .GET()
                    .build();
            logger.fine("Sending request to " + url);
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            return switch (response.statusCode()) {
                case 200 -> {
                    if (response.body() == null) {
                        logger.severe("Response body is null");
                        throw new RuntimeException("Response body is null");
                    }

                    if (response.body().isEmpty()) {
                        logger.severe("Response body is empty");
                        throw new RuntimeException("Response body is empty");
                    }

                    logger.info("Successfully fetched user events for user: " + username);
                    yield objectMapper.readValue(response.body(), new TypeReference<>() {
                    });
                }
                case 404 -> {
                    logger.warning("User not found: " + username);
                    throw new RuntimeException("User not found");
                }
                case 403 -> {
                    logger.warning("Rate limit exceeded or access denied for user: " + username);
                    throw new RuntimeException("Rate limit exceeded or access denied");
                }
                default -> {
                    logger.severe("Unhandled error: " + response.statusCode());
                    throw new RuntimeException("Unexpected status code " + response.statusCode());
                }
            };

        } catch (IOException | InterruptedException e) {
            logger.severe("Failed to fetch user events for " + username + ": " + e);
            throw new RuntimeException("Failed to fetch user events", e);
        }
    }

    public List<String> displayUserEvents(List<GithubEvent> events) throws IOException, InterruptedException {
        logger.info("Formatting " + events.size() + " events");
        List<String> formatted_events = new ArrayList<>();

        for (GithubEvent event : events) {

            try {
                String type = event.type();
                String repo = event.repo().name();

                logger.fine("Processing event type: " + type + " for repo: " + repo);

                switch (type) {
                    case "PushEvent" -> {
                        int commits = event.payload().get("commits").size();
                        formatted_events.add(String.format("Pushed %d commits to %s", commits, repo));
                    }
                    case "CreateEvent" -> {
                        String ref = event.payload().get("ref").asText();
                        String ref_type = event.payload().get("ref_type").asText();
                        String master_branch = event.payload().get("master_branch").asText();
                        formatted_events.add(String.format("Created %s named, %s, from master branch: %s in the repository: %s", ref_type, ref, master_branch, repo));
                    }
                    case "DeleteEvent" -> {
                        String ref = event.payload().get("ref").asText();
                        String ref_type = event.payload().get("ref_type").asText();
                        formatted_events.add(String.format("Deleted %s named, %s, in the repository: %s", ref_type, ref, repo));
                    }
                    case "PullRequestEvent" -> {
                        String action = event.payload().get("action").asText();
                        int number = event.payload().get("number").asInt();
                        String pullRequestName = event.payload().get("pull_request").get("title").asText();
                        formatted_events.add(String.format("%s pull request %d, %s in %s", action, number, pullRequestName, repo));
                    }
                    case "WatchEvent" -> {
                        String action = event.payload().get("action").asText();
                        formatted_events.add("Starred the repository: " + repo);
                    }
                    case "PublicEvent" -> {
                        formatted_events.add("A private repository was made public: " + repo);
                    }

                    default -> {
                        logger.warning("Unsupported event type: " + type);
                        formatted_events.add(String.format("Unsupported event type: %s", type));
                    }
                }
            } catch (Exception e) {
                logger.warning("Error processing event: " + e.getMessage());
                formatted_events.add("Error processing event");
            }
        }

        logger.info("Successfully formatted " + formatted_events.size() + " events");
        return formatted_events;

    }
    
}
