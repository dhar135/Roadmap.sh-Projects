package GithubUserActivity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GithubActivityClientTest {

    private GithubActivityClient client;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        client = new GithubActivityClient();
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Should correctly format all types of events")
    void shouldCorrectlyFormatAllEventTypes() throws IOException, InterruptedException {
        // Here we create a comprehensive test that covers all event types
        List<GithubEvent> events = new ArrayList<>();

        // First, let's create a PushEvent with complete data
        ObjectNode pushPayload = objectMapper.createObjectNode();
        pushPayload.putArray("commits")
                .add(objectMapper.createObjectNode())
                .add(objectMapper.createObjectNode())
                .add(objectMapper.createObjectNode()); // 3 commits

        events.add(new GithubEvent(
                "push-123",                // id
                "PushEvent",              // type
                new Actor(                // actor
                        1,
                        "testUser",
                        "Test User",
                        "gravatar123",
                        "https://api.github.com/users/testUser",
                        "https://avatars.githubusercontent.com/testUser"
                ),
                new Repository(           // repository
                        1L,
                        "testUser/test-repo",
                        "https://api.github.com/repos/testUser/test-repo"
                ),
                pushPayload,             // payload
                true,                    // isPublic
                "2024-01-02T12:00:00Z"  // createdAt
        ));

        // Let's add a CreateEvent for a new branch
        ObjectNode createPayload = objectMapper.createObjectNode();
        createPayload.put("ref", "feature/new-feature");
        createPayload.put("ref_type", "branch");
        createPayload.put("master_branch", "main");

        events.add(new GithubEvent(
                "create-124",
                "CreateEvent",
                new Actor(1, "testUser", "Test User", "gravatar123",
                        "https://api.github.com/users/testUser",
                        "https://avatars.githubusercontent.com/testUser"),
                new Repository(1L, "testUser/test-repo",
                        "https://api.github.com/repos/testUser/test-repo"),
                createPayload,
                true,
                "2024-01-02T12:01:00Z"
        ));

        // Add a PullRequestEvent
        ObjectNode prPayload = objectMapper.createObjectNode();
        prPayload.put("action", "opened");
        prPayload.put("number", 42);
        ObjectNode prNode = objectMapper.createObjectNode();
        prNode.put("title", "Add new feature");
        prPayload.set("pull_request", prNode);

        events.add(new GithubEvent(
                "pr-125",
                "PullRequestEvent",
                new Actor(1, "testUser", "Test User", "gravatar123",
                        "https://api.github.com/users/testUser",
                        "https://avatars.githubusercontent.com/testUser"),
                new Repository(1L, "testUser/test-repo",
                        "https://api.github.com/repos/testUser/test-repo"),
                prPayload,
                true,
                "2024-01-02T12:02:00Z"
        ));

        // When we format these events
        List<String> formattedEvents = client.displayUserEvents(events);

        // Then verify each event is formatted correctly
        assertEquals(3, formattedEvents.size(),
                "Should have formatted all three events");

        // Verify PushEvent formatting
        assertTrue(formattedEvents.get(0).contains("Pushed 3 commits to testUser/test-repo"),
                "Push event should show correct number of commits and repository");

        // Verify CreateEvent formatting
        assertTrue(formattedEvents.get(1).contains("Created branch named, feature/new-feature"),
                "Create event should show correct branch name");
        assertTrue(formattedEvents.get(1).contains("from master branch: main"),
                "Create event should show correct master branch");

        // Verify PullRequestEvent formatting
        assertTrue(formattedEvents.get(2).contains("opened pull request 42"),
                "Pull request event should show correct action and number");
        assertTrue(formattedEvents.get(2).contains("Add new feature"),
                "Pull request event should show correct title");
    }

    @Test
    @DisplayName("Should handle empty event list")
    void shouldHandleEmptyEventList() throws IOException, InterruptedException {
        // Given an empty list of events
        List<GithubEvent> events = new ArrayList<>();

        // When we format the events
        List<String> formattedEvents = client.displayUserEvents(events);

        // Then we should get an empty list back
        assertTrue(formattedEvents.isEmpty(),
                "Should return empty list when no events are provided");
    }

    @Test
    @DisplayName("Should handle malformed event data gracefully")
    void shouldHandleMalformedEventData() throws IOException, InterruptedException {
        // Create an event with missing required payload data
        ObjectNode incompletePayload = objectMapper.createObjectNode();
        // Note: We're creating a PushEvent without the required 'commits' array

        GithubEvent malformedEvent = new GithubEvent(
                "malformed-126",
                "PushEvent",
                new Actor(1, "testUser", "Test User", "gravatar123",
                        "https://api.github.com/users/testUser",
                        "https://avatars.githubusercontent.com/testUser"),
                new Repository(1L, "testUser/test-repo",
                        "https://api.github.com/repos/testUser/test-repo"),
                incompletePayload,
                true,
                "2024-01-02T12:03:00Z"
        );

        List<GithubEvent> events = List.of(malformedEvent);

        // When we try to format this malformed event
        List<String> formattedEvents = client.displayUserEvents(events);

        // Then we should get an error message instead of throwing an exception
        assertEquals(1, formattedEvents.size(),
                "Should have one formatted response");
        assertEquals("Error processing event", formattedEvents.getFirst(),
                "Should return error message for malformed event");
    }
}